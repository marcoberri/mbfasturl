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
package it.marcoberri.mbfasturl.helper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public final class ConfigurationHelper {

	/**
	 * @return the cron
	 */
	public static JSONArray getCron() {
		return cron;
	}

	/**
	 * @return the prop
	 */
	public static Properties getProp() {
		return prop;
	}

	/**
	 *
	 * @return
	 */
	public static ConfigurationHelper instance() {
		return INSTANCE;
	}

	/**
	 * @param aCron
	 *            the cron to set
	 */
	public static void setCron(JSONArray aCron) {
		cron = aCron;
	}

	/**
	 * @param aProp
	 *            the prop to set
	 */
	public static void setProp(Properties aProp) {
		prop = aProp;
	}

	private static final ConfigurationHelper INSTANCE = new ConfigurationHelper();

	/**
     *
     */
	private static Properties prop;

	private static JSONArray cron;

	private ConfigurationHelper() {
		try {
			final URL main = getClass().getProtectionDomain().getCodeSource().getLocation();
			final String path = URLDecoder.decode(main.getPath(), "utf-8");
			final String webInfFolderPosition = new File(path).getPath();
			final String webInfFolder = webInfFolderPosition.substring(0, webInfFolderPosition.indexOf("classes"));
			prop = new Properties();
			prop.load(FileUtils.openInputStream(new File(webInfFolder + File.separator + "config.properties")));

			final JSONParser parser = new JSONParser();

			final Object obj = parser.parse(new FileReader(webInfFolder + File.separator + "cron.json"));
			final JSONObject jsonObject = (JSONObject) obj;
			ConfigurationHelper.cron = (JSONArray) jsonObject.get("cron");

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ParseException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return ConfigurationHelper.getProp().getProperty(key);
	}
}
