/**
 * Copyright 2011 Marco Berri - marcoberri@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package it.marcoberri.mbfasturl.cron;

import it.marcoberri.mbfasturl.action.Commons;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.utils.Default;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.google.gson.Gson;

/**
 * 
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class QuartzInitServlet extends HttpServlet {

    /**
     *
     */
    public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";
    private boolean performShutdown = true;
    private Scheduler scheduler = null;
    /**
     *
     */
    protected ServletContext application;

    /**
     *
     */
    @Override
    public void destroy() {

	if (!performShutdown) {
	    return;
	}

	if (scheduler != null) {
	    try {
		scheduler.shutdown();
	    } catch (SchedulerException ex) {

		Commons.log.error(ex);
	    }
	}
    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    /**
     * Returns a short description of the servlet.
     * 
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Init Quartz on Start UP";
    }// </editor-fold>

    /**
     * 
     * @param cfg
     * @throws javax.servlet.ServletException
     */
    @Override
    public void init(ServletConfig cfg) throws javax.servlet.ServletException {

	super.init(cfg);
	this.application = cfg.getServletContext();

	StdSchedulerFactory schedulerFactory = null;
	final String shutdownPref = cfg.getInitParameter("shutdown-on-unload");

	if (shutdownPref != null) {
	    performShutdown = Boolean.valueOf(shutdownPref).booleanValue();
	}

	Gson cron = new Gson();

	final Properties prop = new Properties();
	prop.setProperty("org.quartz.scheduler.instanceName", "OsQuarz_mbfasturl2");
	prop.setProperty("org.quartz.scheduler.instanceId", "" + System.nanoTime());
	prop.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
	prop.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
	prop.setProperty("org.quartz.threadPool.threadCount", "1");

	Commons.log.debug("Init Schduler");

	try {

	    schedulerFactory = new StdSchedulerFactory(prop);
	    scheduler = schedulerFactory.getScheduler();

	    // StartUp
	    Commons.log.debug("configure : StartUp");

	    final Iterator<JSONObject> iterator = ConfigurationHelper.getCron().iterator();
	    while (iterator.hasNext()) {
		final JSONObject c = iterator.next();
		final String name = Default.toString(c.get("name"));
		Commons.log.debug("name:" + name);

		final String clazz = Default.toString(c.get("class"));
		Commons.log.debug("clazz:" + clazz);

		final Boolean enable = Default.toBoolean(c.get("enable"));
		Commons.log.debug("enable:" + enable);

		final String cronTab = Default.toString(c.get("cron"));
		Commons.log.debug("cronTab:" + cronTab);

		if (!enable) {
		    Commons.log.info(name + " enbale:" + enable);
		    continue;
		}

		try {
		    final Class classForCron = Class.forName(clazz);
		    Commons.log.debug("start configure:" + name);
		    final JobDetail job = JobBuilder.newJob(classForCron).withIdentity("job" + name, "group" + name).build();

		    final Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + name, "group" + name).withSchedule(CronScheduleBuilder.cronSchedule(cronTab)).build();

		    scheduler.scheduleJob(job, trigger);

		} catch (ClassNotFoundException ex) {

		    Commons.log.fatal(ex);
		}

	    }

	    scheduler.start();
	    application.setAttribute(QUARTZ_FACTORY_KEY, schedulerFactory);
	    Commons.log.debug("cron started :" + QUARTZ_FACTORY_KEY);
	} catch (SchedulerException ex) {
	    Commons.log.fatal("Fatal conf Job", ex);
	}

    }
}
