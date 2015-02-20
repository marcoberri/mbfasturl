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
package it.marcoberri.mbfasturl.cron.action.system;

import static it.marcoberri.mbfasturl.action.Commons.writeEventLog;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.system.AppCache;
import it.marcoberri.mbfasturl.utils.Log4j;

import org.mongodb.morphia.Datastore;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class QuartzDeleteAppCache implements Job {

    /**
     *
     */
    protected static final org.apache.log4j.Logger log = Log4j.getLogger(QuartzDeleteAppCache.class.getSimpleName(), ConfigurationHelper.getProp().getProperty("log.path"), Log4j.ROTATE_DAILY);

    /**
     * 
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
	final long ts = System.currentTimeMillis();
	final Datastore ds = MongoConnectionHelper.ds;
	ds.delete(AppCache.class);
	writeEventLog("deleteAppCache", true, "Complete", "System", (System.currentTimeMillis() - ts));

    }
}
