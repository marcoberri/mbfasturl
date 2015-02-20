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
package it.marcoberri.mbfasturl.cron.action;

import static it.marcoberri.mbfasturl.action.Commons.writeEventLog;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.Log;
import it.marcoberri.mbfasturl.model.UAgent;
import it.marcoberri.mbfasturl.utils.Default;
import it.marcoberri.mbfasturl.utils.Log4j;

import java.util.HashMap;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import org.mongodb.morphia.Datastore;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class QuartzUACalculation implements Job {

    /**
     *
     */
    protected static final org.apache.log4j.Logger log = Log4j.getLogger(QuartzUACalculation.class.getSimpleName(), ConfigurationHelper.getProp().getProperty("log.path"), Log4j.ROTATE_DAILY);

    /**
     * 
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
	final Datastore ds = MongoConnectionHelper.ds;
	final Datastore dsSave = MongoConnectionHelper.ds;
	final long ts = System.currentTimeMillis();

	final HashMap<String, ReadableUserAgent> cache = new HashMap<String, ReadableUserAgent>();
	try {
	    int c = 0;
	    int tot = 0;
	    for (Log l : ds.createQuery(Log.class).field("headers.user-agent").exists().field("agent").doesNotExist().asList()) {
		tot++;
		if (l.getAgent() != null) {
		    continue;
		}

		final String ua = Default.toString(l.getHeaders().get("user-agent"));

		ReadableUserAgent agent = null;

		if (cache.get(ua) != null) {
		    agent = cache.get(ua);
		} else {

		    try {
			final UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
			agent = parser.parse(ua);

			if (agent == null) {
			    continue;
			}

			cache.put(ua, agent);

		    } catch (final Exception e) {
			log.fatal(e.getMessage(), e);
		    }

		    if (agent == null) {
			continue;
		    }

		    final UAgent uagent = new UAgent();
		    uagent.setIcon(agent.getIcon());
		    uagent.setName(agent.getName());
		    uagent.setProducer(agent.getProducer());
		    uagent.setProducerUrl(agent.getProducerUrl());
		    uagent.setTypeName(agent.getTypeName());
		    uagent.setUrl(agent.getUrl());

		    uagent.getOperatingSystem().setFamilyName(agent.getOperatingSystem().getFamilyName());
		    uagent.getOperatingSystem().setIcon(agent.getOperatingSystem().getIcon());
		    uagent.getOperatingSystem().setProducer(agent.getOperatingSystem().getProducer());
		    uagent.getOperatingSystem().setProducerUrl(agent.getOperatingSystem().getProducerUrl());
		    uagent.getOperatingSystem().setUrl(agent.getOperatingSystem().getUrl());
		    uagent.getOperatingSystem().getVersionNumber().setExtension(agent.getOperatingSystem().getVersionNumber().getExtension());
		    uagent.getOperatingSystem().getVersionNumber().setGroups(agent.getOperatingSystem().getVersionNumber().getGroups());
		    uagent.getOperatingSystem().getVersionNumber().setMajor(agent.getOperatingSystem().getVersionNumber().getMajor());
		    uagent.getOperatingSystem().getVersionNumber().setMinor(agent.getOperatingSystem().getVersionNumber().getMinor());

		    l.setAgent(uagent);
		    dsSave.save(l);
		    c++;

		}
	    }

	    writeEventLog("UserAgentCalc", true, "tot elements: " + tot + " | tot save: " + c, "Manipulate", (System.currentTimeMillis() - ts));
	} catch (final Exception e) {
	    log.fatal(e.getMessage(), e);
	    writeEventLog("UserAgentCalc", false, e.getMessage(), "Manipulate", (System.currentTimeMillis() - ts));
	}

    }
}
