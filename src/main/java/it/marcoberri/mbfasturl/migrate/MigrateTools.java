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
package it.marcoberri.mbfasturl.migrate;

import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.utils.Log4j;

import java.io.File;
import java.util.ArrayList;

import org.mongodb.morphia.Datastore;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class MigrateTools {

	/**
     *
     */
	protected final static org.apache.log4j.Logger log = Log4j.getLogger("migratetools", ConfigurationHelper.getProp().getProperty("log.path"), Log4j.ROTATE_DAILY);
	/**
     *
     */
	protected final Datastore ds = MongoConnectionHelper.ds;
	/**
     *
     */
	protected final String dbUrl = ConfigurationHelper.getProp().getProperty("migration.db");
	/**
     *
     */
	protected final String dbStats = ConfigurationHelper.getProp().getProperty("migration.stats.db");
	/**
     *
     */
	protected final String urlFile = ConfigurationHelper.getProp().getProperty("migration.db.file");
	/**
     *
     */
	protected final String statsFile = ConfigurationHelper.getProp().getProperty("migration.stats.db.file");
	/**
     *
     */
	protected ArrayList<File> fileStatsList = new ArrayList<File>();
	/**
     *
     */
	protected ArrayList<File> fileUrlList = new ArrayList<File>();
	/**
     *
     */
	protected String proxy = ConfigurationHelper.getProp().getProperty("url.proxy.domain", "http://mbfasturl2.marcoberri.it/");

	/**
     *
     */
	public void dropDB() {
		ds.getDB().dropDatabase();
	}
	/*
	 * public void exportJson() {
	 * 
	 * try { Class.forName("org.sqlite.JDBC");
	 * 
	 * Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
	 * Statement statement = conn.createStatement();
	 * 
	 * final Gson gson = new Gson(); final ResultSet rs =
	 * statement.executeQuery("select * from url");
	 * log.debug("select * from url"); List list = new ArrayList(); int c = 0;
	 * int fileC = 0; while (rs.next()) {
	 * 
	 * if (StringUtil.isNullOrEmpty(rs.getString("url")) ||
	 * StringUtil.isNullOrEmpty(rs.getString("id")) ||
	 * StringUtil.isNullOrEmpty(rs.getString("t")) ||
	 * StringUtil.isNullOrEmpty(rs.getString("t_end"))) { continue; } final
	 * String fast = rs.getString("id"); final String url = rs.getString("url");
	 * final String created = rs.getString("t"); final String ending =
	 * rs.getString("t_end");
	 * 
	 * final Map<String, String> o = new HashMap<String, String>();
	 * o.put("fast", fast); o.put("created", created); o.put("ending", ending);
	 * o.put("url", url);
	 * 
	 * list.add(o); c++;
	 * 
	 * if (c % 1000 == 0) { fileC++; fileUrlList.add(new File(urlFile + fileC));
	 * org.apache.commons.io.FileUtils.writeStringToFile(new File(urlFile +
	 * fileC), gson.toJson(list).toString(), "utf8"); list = new ArrayList();
	 * 
	 * } }
	 * 
	 * fileC++; org.apache.commons.io.FileUtils.writeStringToFile(new
	 * File(urlFile + fileC), gson.toJson(list).toString(), "utf8");
	 * fileUrlList.add(new File(urlFile + fileC));
	 * 
	 * log.debug("total:" + c); log.debug("to:" + dbUrl); rs.close();
	 * 
	 * conn = DriverManager.getConnection("jdbc:sqlite:" + dbStats);
	 * 
	 * statement = conn.createStatement();
	 * 
	 * String[] tables = {"view", "save"}; List listView = new ArrayList();
	 * 
	 * for (String t : tables) { log.debug("select * from " + t);
	 * 
	 * ResultSet rs2 = statement.executeQuery("select * from " + t);
	 * 
	 * fileC = 0; c = 0; while (rs2.next()) {
	 * 
	 * final String fast = rs2.getString("fk_url_id"); final String header =
	 * rs2.getString("header"); final String ts = rs2.getString("ts"); final
	 * String ip = rs2.getString("ip");
	 * 
	 * final HashMap headers = new HashMap();
	 * 
	 * if (header != null && !"".equals(header)) { String h[] =
	 * header.split("\\|\\|"); for (String s : h) { String[] v = s.split("\\|",
	 * 2); if (v.length < 2) { continue; }
	 * 
	 * headers.put(v[0].replaceAll("\\.", "_"), v[1]); } }
	 * 
	 * final Map o = new HashMap(8); o.put("fast", fast); o.put("header",
	 * headers); o.put("ts", ts); o.put("ip", ip); o.put("type", t);
	 * listView.add(o);
	 * 
	 * if (c % 10000 == 0) { fileC++; log.debug("act:" + c);
	 * fileStatsList.add(new File(statsFile + fileC + t));
	 * org.apache.commons.io.FileUtils.writeStringToFile(new File(statsFile +
	 * fileC + t), gson.toJson(listView).toString(), "utf8"); listView = new
	 * ArrayList();
	 * 
	 * }
	 * 
	 * c++; } log.debug("total:" + c + " for table:" + t);
	 * 
	 * rs.close(); fileC++; fileStatsList.add(new File(statsFile + fileC + t));
	 * org.apache.commons.io.FileUtils.writeStringToFile(new File(statsFile +
	 * fileC + t), gson.toJson(listView).toString(), "utf8");
	 * 
	 * }
	 * 
	 * } catch (IOException ex) { log.error(ex.getMessage(), ex); } catch
	 * (ClassNotFoundException ex) { log.error(ex.getMessage(), ex); } catch
	 * (SQLException ex) { log.error(ex.getMessage(), ex); }
	 * 
	 * }
	 * 
	 * public void importJson() {
	 * 
	 * try {
	 * 
	 * Gson gson = new Gson();
	 * 
	 * for (File f : fileUrlList) {
	 * 
	 * final String s = org.apache.commons.io.FileUtils.readFileToString(f,
	 * "utf8");
	 * 
	 * final ArrayList<StringMap> list = gson.fromJson(s, ArrayList.class);
	 * //gson. int c = 0; for (StringMap m : list) {
	 * 
	 * if (StringUtil.isNullOrEmpty(Default.toString(m.get("fast"), null)) ||
	 * StringUtil.isNullOrEmpty(Default.toString(m.get("created"), null)) ||
	 * StringUtil.isNullOrEmpty(Default.toString(m.get("ending"), null)) ||
	 * StringUtil.isNullOrEmpty(Default.toString(m.get("url"), null))) {
	 * continue; }
	 * 
	 * //write in mongo final Url urlModel = new Url();
	 * urlModel.setFast(m.get("fast").toString());
	 * urlModel.setCreated(DateTimeUtil.getDate("yyyy-MM-dd HH:mm:ss",
	 * m.get("created").toString()));
	 * urlModel.setEnding(DateTimeUtil.getDate("yyyy-MM-dd HH:mm:ss",
	 * m.get("ending").toString())); urlModel.setUrl(m.get("url").toString());
	 * urlModel.setPort(80); urlModel.setProtocol("http");
	 * 
	 * try { ds.save(urlModel); Url qrcode = Commons.generateQrcodes(urlModel);
	 * ds.save(qrcode); } catch (WriterException e) { log.error(e.getMessage(),
	 * e); continue; } catch (IOException e) { log.error(e.getMessage(), e);
	 * continue; }
	 * 
	 * if (c % 500 == 0) { log.debug("count: " + c); } c++;
	 * 
	 * } log.debug("file: " + f); log.debug("tot url saved in mongo: " + c); } }
	 * catch (IOException ex) { log.error(ex.getMessage(), ex); }
	 * 
	 * try { int superC = 0; for (File f : fileStatsList) {
	 * 
	 * String s = org.apache.commons.io.FileUtils.readFileToString(f, "utf8");
	 * 
	 * Gson gson = new Gson(); ArrayList<StringMap> list = gson.fromJson(s,
	 * ArrayList.class);
	 * 
	 * //gson. int c = 0; for (StringMap m : list) {
	 * 
	 * //write in mongo final String fast = Default.toString(m.get("fast"));
	 * final String type = Default.toString(m.get("type"));
	 * 
	 * if (StringUtil.isNullOrEmpty(Default.toString(m.get("fast"), null)) ||
	 * StringUtil.isNullOrEmpty(Default.toString(m.get("type"), null)) ) {
	 * continue; }
	 * 
	 * 
	 * final Url urlModel = (Url)
	 * ds.find(Url.class).field("fast").equal(fast).get();
	 * 
	 * if (urlModel == null) { log.debug("url is not found " + fast); continue;
	 * } Log logModel = null;
	 * 
	 * if (type.equals("view")) { logModel = new LogView(); }
	 * 
	 * if (type.equals("save")) { logModel = new LogSave(); }
	 * 
	 * if (logModel == null) { log.debug("type is not valid " + type); continue;
	 * }
	 * 
	 * final String ip = Default.toString(m.get("ip"));
	 * logModel.setCreated(DateTimeUtil.getDate("yyyy-MM-dd HH:mm:ss",
	 * m.get("ts").toString())); logModel.setUrlId(urlModel.getId());
	 * logModel.setIp(ip); logModel.setFast(fast);
	 * 
	 * StringMap header = (StringMap) m.get("header"); for (Object k :
	 * header.keySet()) { logModel.addHeader(Default.toString(k),
	 * header.get(k).toString()); } ds.save(logModel, WriteConcern.NORMAL);
	 * 
	 * if (c % 100 == 0) { log.debug("count: " + c); } c++; superC++;
	 * 
	 * } log.debug("file: " + f); log.debug("count tot file: " + superC);
	 * 
	 * }
	 * 
	 * final List<Url> urls = ds.find(Url.class).asList(); for (Url url : urls)
	 * { List<Log> views =
	 * ds.find(Log.class).field("fast").equal(url.getFast()).asList(); for (Log
	 * view : views) { view.setUrl(url.getUrl()); ds.save(view); }
	 * log.debug("Tot upd:" + views.size() + " for: " + url.getFast()); }
	 * 
	 * } catch (IOException ex) { log.error(ex.getMessage(), ex); }
	 * 
	 * }
	 */

}
