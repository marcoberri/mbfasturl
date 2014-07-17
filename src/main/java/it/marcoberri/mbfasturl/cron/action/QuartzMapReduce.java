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

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.MapreduceResults;
import org.mongodb.morphia.MapreduceType;
import it.marcoberri.mbfasturl.action.Commons;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.Log;
import it.marcoberri.mbfasturl.model.mr.StatsCountSingleBrowser;
import it.marcoberri.mbfasturl.model.mr.StatsCountSingleBrowserUrl;
import it.marcoberri.mbfasturl.model.mr.StatsCountSingleCountryUrl;
import it.marcoberri.mbfasturl.model.mr.StatsCountSingleIp;
import it.marcoberri.mbfasturl.model.mr.StatsCountSingleSite;
import it.marcoberri.mbfasturl.model.mr.StatsCountSingleUrl;
import it.marcoberri.mbfasturl.model.mr.StatsCountTimeDayUrl;
import it.marcoberri.mbfasturl.model.mr.StatsCountTimeMonthCountryUrl;
import it.marcoberri.mbfasturl.model.mr.StatsCountTimeMonthUrl;
import it.marcoberri.mbfasturl.model.mr.StatsCountTimeYearUrl;
import it.marcoberri.mbfasturl.utils.Log4j;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class QuartzMapReduce implements Job {

	/**
     *
     */
	protected static final org.apache.log4j.Logger log = Log4j.getLogger(QuartzMapReduce.class.getSimpleName(), ConfigurationHelper.getProp().getProperty("log.path"), Log4j.ROTATE_DAILY);

	/**
	 *
	 * @param jec
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {

		log.debug("Start " + QuartzMapReduce.class.getSimpleName());

		log.debug("Start mapReduceCountSingleBrowser");
		mapReduceCountSingleBrowser(log);

		log.debug("Start mapReduceCountSingleIp");
		mapReduceCountSingleIp(log);

		log.debug("Start mapReduceCountSingleSite");
		mapReduceCountSingleSite(log);

		log.debug("Start mapReduceCountSingleUrl");
		mapReduceCountSingleUrl(log);

		log.debug("Start mapReduceCountTimeDayUrl");

		mapReduceCountTimeDayUrl(log);

		log.debug("Start mapReduceCountTimeMonthUrl");
		mapReduceCountTimeMonthUrl(log);

		log.debug("Start mapReduceCountTimeYearUrl");
		mapReduceCountTimeYearUrl(log);

		log.debug("Start mapReduceCountTimeMonthCountryUrl");
		mapReduceCountTimeMonthCountryUrl(log);

		log.debug("Start mapReduceCountSingleCountryUrl");
		mapReduceCountSingleCountryUrl(log);

		log.debug("Start mapReduceCountSingleBrowserUrl");
		mapReduceCountSingleBrowserUrl(log);
		log.debug("End " + QuartzMapReduce.class.getSimpleName());

	}

	/**
	 *
	 * @param fileName
	 * @param log
	 * @return
	 */
	private String getJsFromFile(String fileName, Logger log) {
		final long ts = System.currentTimeMillis();
		try {
			final URL main = log.getClass().getProtectionDomain().getCodeSource().getLocation();
			final String path = URLDecoder.decode(main.getPath(), "utf-8");
			final String webInfFolderPosition = new File(path).getPath();
			final String webInfFolder = webInfFolderPosition.substring(0, webInfFolderPosition.indexOf("classes"));
			return FileUtils.readFileToString(new File(webInfFolder + File.separator + fileName));
		} catch (final IOException ex) {
			Commons.writeEventLog("getJsFromFile", false, ex.getMessage(), "MapReduce", (System.currentTimeMillis() - ts));
			log.error(ex);
		}
		return null;
	}

	/**
	 *
	 * @param log
	 */
	private void mapReduceCountSingleBrowser(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountSingleBrowser";
		final Datastore ds = MongoConnectionHelper.ds;
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		final MapreduceResults<StatsCountSingleBrowser> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountSingleBrowser.class);
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	private void mapReduceCountSingleBrowserUrl(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountSingleBrowserUrl";
		final Datastore ds = MongoConnectionHelper.ds;
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		final MapreduceResults<StatsCountSingleBrowserUrl> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountSingleBrowserUrl.class);
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	/**
	 *
	 * @param log
	 */
	private void mapReduceCountSingleCountryUrl(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountSingleCountryUrl";
		final Datastore ds = MongoConnectionHelper.ds;
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		log.debug("map:" + map);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		log.debug("reduce:" + reduce);
		final MapreduceResults<StatsCountSingleCountryUrl> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountSingleCountryUrl.class);
		log.debug("count:" + mrRes.getClass());
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	/**
	 * @param log
	 */
	private void mapReduceCountSingleIp(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountSingleIp";
		final Datastore ds = MongoConnectionHelper.ds;
		log.debug("dbnamne:" + ds.getDB().getName());
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		log.debug("map:" + map);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		log.debug("reduce:" + reduce);
		final MapreduceResults<StatsCountSingleIp> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountSingleIp.class);
		log.debug("count:" + mrRes.getClass());
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	/**
	 *
	 * @param log
	 */
	private void mapReduceCountSingleSite(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountSingleSite";
		final Datastore ds = MongoConnectionHelper.ds;
		log.debug("dbnamne:" + ds.getDB().getName());
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		log.debug("map:" + map);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		log.debug("reduce:" + reduce);
		final MapreduceResults<StatsCountSingleSite> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountSingleSite.class);
		log.debug("count:" + mrRes.getClass());
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	/**
	 *
	 * @param log
	 */
	private void mapReduceCountSingleUrl(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountSingleUrl";
		final Datastore ds = MongoConnectionHelper.ds;
		log.debug("dbnamne:" + ds.getDB().getName());
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		log.debug("map:" + map);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		log.debug("reduce:" + reduce);
		final MapreduceResults<StatsCountSingleUrl> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountSingleUrl.class);
		log.debug("count:" + mrRes.getClass());
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	/**
	 * @param log
	 */
	private void mapReduceCountTimeDayUrl(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountTimeDayUrl";
		final Datastore ds = MongoConnectionHelper.ds;
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		final MapreduceResults<StatsCountTimeDayUrl> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountTimeDayUrl.class);
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	/**
	 *
	 * @param log
	 */
	private void mapReduceCountTimeMonthCountryUrl(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountTimeMonthCountryUrl";
		final Datastore ds = MongoConnectionHelper.ds;
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		final MapreduceResults<StatsCountTimeMonthCountryUrl> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountTimeMonthCountryUrl.class);
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	/**
	 *
	 * @param log
	 */
	private void mapReduceCountTimeMonthUrl(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountTimeMonthUrl";
		final Datastore ds = MongoConnectionHelper.ds;
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		final MapreduceResults<StatsCountTimeMonthUrl> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountTimeMonthUrl.class);
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	/**
	 *
	 * @param log
	 */
	private void mapReduceCountTimeYearUrl(Logger log) {
		final long ts = System.currentTimeMillis();
		final String l = "CountTimeYearUrl";
		final Datastore ds = MongoConnectionHelper.ds;
		final String map = getJsFromFile("/mapreduce/stats/" + l + "/map.txt", log);
		final String reduce = getJsFromFile("/mapreduce/stats/" + l + "/reduce.txt", log);
		final MapreduceResults<StatsCountTimeYearUrl> mrRes = ds.mapReduce(MapreduceType.MERGE, ds.createQuery(Log.class), map, reduce, null, null, StatsCountTimeYearUrl.class);
		writeLogMR(l, mrRes, (System.currentTimeMillis() - ts));
	}

	private void writeLogMR(String action, MapreduceResults mr, long ts) {
		Commons.writeEventLog(action, true, "tot: " + mr.createQuery().countAll(), "MapReduce", ts);
	}
}
