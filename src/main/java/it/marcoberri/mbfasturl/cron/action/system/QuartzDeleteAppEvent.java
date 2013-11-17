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

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import static it.marcoberri.mbfasturl.action.Commons.writeEventLog;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.system.AppEvent;
import it.marcoberri.mbfasturl.utils.Log4j;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class QuartzDeleteAppEvent implements Job {

    /**
     *
     */
    protected static final org.apache.log4j.Logger log = Log4j.getLogger(QuartzDeleteAppEvent.class.getSimpleName(), ConfigurationHelper.getProp().getProperty("log.path"), Log4j.ROTATE_DAILY);
    private static final long LAST_DAY_IN_MS = 1000 * 60 * 60 * 24;

    /**
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        final long ts = System.currentTimeMillis();
        final Datastore ds = MongoConnectionHelper.ds;
        final Date d = new Date(System.currentTimeMillis() - (LAST_DAY_IN_MS * 10));
        final Query q = ds.createQuery(AppEvent.class).field("date").lessThan(d);
        long count = q.countAll();
        ds.delete(q);
        writeEventLog("deleteAppCache", true, "tot elements deleted:  " + count, "System", (System.currentTimeMillis() - ts));

    }
}
