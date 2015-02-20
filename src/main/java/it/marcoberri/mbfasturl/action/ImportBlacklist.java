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
package it.marcoberri.mbfasturl.action;

/**
 * 
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class ImportBlacklist {

    /*
     * try {
     * 
     * 
     * //scarico il file String name = "nixspam-ip.dump.gz"; String f =
     * this.path_app_root + "/" + this.properties.get("dir") + "/";
     * 
     * try { org.apache.commons.io.FileUtils.forceMkdir(new File(f)); } catch
     * (IOException ex) { fatal("IOException", ex); } f += "/" + name; String
     * url = "http://www.dnsbl.manitu.net/download/" + name;
     * 
     * debug("(1) - start download: " + url);
     * com.utils.HttpUtil.downloadData(url, f); com.utils.IOUtil.unzip(f,
     * f.replace(".gz", ""));
     * 
     * File file_to_read = new File(f.replaceAll(".gz", ""));
     * 
     * BigFile lines = null;
     * 
     * try { lines = new BigFile(file_to_read.toString()); } catch (Exception e)
     * { fatal("Excpetion", e); return; }
     * 
     * try {
     * 
     * Statement stat = conn_url.createStatement();
     * stat.executeUpdate(properties.get("query_delete")); stat.close(); } catch
     * (SQLException e) { fatal("SQLException", e);
     * 
     * }
     * 
     * 
     * try { conn_url.setAutoCommit(false); } catch (SQLException e) {
     * fatal("SQLException", e); }
     * 
     * 
     * boolean ok = true; int i = 0; for (String line : lines) { if
     * (StringUtil.isEmpty(line) || line.indexOf(" ") == -1) { continue; }
     * //eseguo la insert try { line = line.substring(line.indexOf(" ")); line =
     * line.trim(); if (getIPException(line)) { continue; } Statement stat =
     * this.conn_url.createStatement();
     * stat.executeUpdate("insert into blacklist(url) values('" + line + "')");
     * stat.close(); i++; } catch (SQLException e) { fatal("SQLException", e);
     * try { conn_url.rollback(); } catch (SQLException ex) {
     * fatal("SQLException", ex); } ok = false; break; }
     * 
     * 
     * }
     * 
     * 
     * //elimino il file originale per risparmiare spazio su disco boolean del =
     * file_to_read.delete(); debug("File " + file_to_read + " del:" + del);
     * 
     * 
     * 
     * 
     * 
     * //altro file da scaricare...
     * 
     * 
     * 
     * //scarico il file name = "spam-ip.com_" +
     * DateTimeUtil.getNowWithFormat("MM-dd-yyyy") + ".csv"; f =
     * this.path_app_root + "/" + this.properties.get("dir") + "/";
     * org.apache.commons.io.FileUtils.forceMkdir(new File(f)); f += "/" + name;
     * url = "http://spam-ip.com/csv_dump/" + name;
     * 
     * debug("(2) - start download: " + url);
     * com.utils.HttpUtil.downloadData(url, f);
     * 
     * file_to_read = new File(f); try { lines = new
     * BigFile(file_to_read.toString()); } catch (Exception e) {
     * fatal("Exception", e); return; }
     * 
     * try { conn_url.setAutoCommit(false); } catch (SQLException e) {
     * fatal("SQLException", e); }
     * 
     * 
     * ok = true; for (String line : lines) {
     * 
     * if (StringUtil.isEmpty(line) || line.indexOf(" ") == -1) { continue; }
     * //eseguo la insert try {
     * 
     * line = line.split(",")[1]; line = line.trim(); if (getIPException(line))
     * { continue; } Statement stat = this.conn_url.createStatement();
     * stat.executeUpdate("insert into blacklist(url) values('" + line + "')");
     * stat.close(); i++; } catch (SQLException e) { fatal("SQLException", e);
     * try { conn_url.rollback(); } catch (SQLException ex) {
     * fatal("SQLException", ex); } ok = false; break; }
     * 
     * 
     * }
     * 
     * //elimino il file originale per risparmiare spazio su disco del =
     * file_to_read.delete(); debug("File " + file_to_read + " del:" + del);
     * 
     * 
     * if (ok) { debug("Import della BlackList Concluso tot righe: " + i);
     * 
     * try { conn_url.commit(); } catch (SQLException e) { fatal("SQLException",
     * e); } } else { fatal("Problemi con la Blacklist"); }
     * 
     * try {
     * 
     * conn_url.setAutoCommit(true); } catch (SQLException e) {
     * fatal("SQLException", e); }
     * 
     * try {
     * 
     * Statement stat = this.conn_url.createStatement();
     * stat.executeUpdate("VACUUM"); stat.close();
     * 
     * } catch (SQLException e) { fatal("SQLException", e); }
     * 
     * 
     * } catch (IOException ex) { fatal("IOException", ex);
     * 
     * }
     * 
     * debug("End execute job " + this.getClass().getName());
     */

    // da portare nelle conf oppure in un file a parte modificabile a mano.
    /**
     * 
     * @param ip
     * @return boolean
     */
    protected boolean getIPException(String ip) {

	if (ip.equals("127.0.0.1")) {
	    return true;
	}

	if (ip.equals("127.0.0.1")) {
	    return true;
	}

	if (ip.equals("0:0:0:0:0:0:0:1")) {
	    return true;
	}

	return false;
    }

}
