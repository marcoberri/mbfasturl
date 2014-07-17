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

import it.marcoberri.mbfasturl.action.Commons;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.utils.Log4j;
import java.io.File;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class QuartzGeoIpCalculation implements Job {

	/**
     *
     */
	protected static final org.apache.log4j.Logger log = Log4j.getLogger(QuartzGeoIpCalculation.class.getSimpleName(), ConfigurationHelper.getProp().getProperty("log.path"), Log4j.ROTATE_DAILY);
	/**
     *
     */
	// protected final String fileMMDB =
	// ConfigurationHelper.getProp().getProperty("cron.downloadGeoIp2.path") +
	// "/" +
	// ConfigurationHelper.getProp().getProperty("cron.downloadGeoIp2.filename");

	protected final String fileMMDB = "/home/marco/mbfasturl/geoip2/" + "GeoLite2-City.mmdb";

	/**
	 *
	 * @param jec
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {

		final File mmdb = new File(fileMMDB);
		if (!mmdb.exists()) {
			final String path = "/home/marco/mbfasturl/geoip2/";// ConfigurationHelper.getProp().getProperty("cron.downloadGeoIp2.path");
			final String url = "http://geolite.maxmind.com/download/geoip/database/GeoLite2-City.mmdb.gz";
			ConfigurationHelper.getProp().getProperty("cron.downloadGeoIp2.url");
			Commons.downloadGeoIp2(log, path, url);
		}

		// Commons.ipCalc(log, mmdb);

		Commons.ipToLog(log);

	}
}
