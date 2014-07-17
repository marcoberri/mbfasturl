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

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;

import it.marcoberri.mbfasturl.action.Commons;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public final class MongoConnectionHelper {

	/**
	 *
	 * @return
	 */
	public static DBCollection getGridCol() {
		return ds.getDB().getCollection(GRIDCOLLECTIONNAME);
	}
	/**
	 *
	 * @return
	 */
	public static GridFS getGridFS() {
		if (gridFS == null) {
			gridFS = new GridFS(ds.getDB(), GRIDCOLLECTIONNAME);
		}
		return gridFS;
	}
	/**
	 *
	 * @return
	 */
	public static MongoConnectionHelper instance() {
		return INSTANCE;
	}
	private static final MongoConnectionHelper INSTANCE = new MongoConnectionHelper();

	/**
     *
     */
	public static Datastore ds;

	private static GridFS gridFS;

	private static final String GRIDCOLLECTIONNAME = "Cache";

	private MongoConnectionHelper() {
		if (ds != null) {
			return;
		}
		try {
			final MongoClient mongoClient = new MongoClient(ConfigurationHelper.getProp().getProperty("mongo.host"));
			final Morphia morphia = new Morphia();
			morphia.mapPackage("it.marcoberri.mbfasturl.model", true).mapPackage("it.marcoberri.mbfasturl.model.mr", true).mapPackage("it.marcoberri.mbfasturl.model.system", true);
			ds = morphia.createDatastore(mongoClient, ConfigurationHelper.getProp().getProperty("mongo.dbname"));
		} catch (UnknownHostException e) {
			Commons.log.fatal(e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
