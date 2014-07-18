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
package it.marcoberri.mbfasturl.cron.action.fix;

import static it.marcoberri.mbfasturl.action.Commons.writeEventLog;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.Log;
import it.marcoberri.mbfasturl.model.Url;
import it.marcoberri.mbfasturl.utils.Log4j;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class QuartzFixLogUrlFast implements Job {

	/**
     *
     */
	protected static final org.apache.log4j.Logger log = Log4j.getLogger(QuartzFixLogUrlFast.class.getSimpleName(), ConfigurationHelper.getProp().getProperty("log.path"), Log4j.ROTATE_DAILY);

	/**
	 *
	 * @param jec
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		final long ts = System.currentTimeMillis();
		final Datastore ds = MongoConnectionHelper.ds;
		final Datastore dsSave = MongoConnectionHelper.ds;
		final Datastore dsUrl = MongoConnectionHelper.ds;

		try {
			int c = 0;

			final Query q = ds.createQuery(Log.class).field("fast").doesNotExist().field("url").doesNotExist();

			final List<Log> entities = q.asList();

			for (Log l : entities) {

				if (l.getFast() != null && l.getUrl() != null) {
					continue;
				}
				final Url u = dsUrl.find(Url.class, "_id", l.getUrlId()).get();
				if (u == null) {
					continue;
				}
				l.setFast(u.getFast());
				l.setUrl(u.getUrl());
				dsSave.save(l);
				c++;
			}

			writeEventLog("FixLogUrlFast", true, "tot elements fast/url: " + entities.size() + " | tot fixed: " + c, "Fix", (System.currentTimeMillis() - ts));
		} catch (final Exception e) {
			log.fatal(e);
			writeEventLog("FixLogUrlFast", false, e.getMessage(), "Fix", (System.currentTimeMillis() - ts));
		}

	}
}
