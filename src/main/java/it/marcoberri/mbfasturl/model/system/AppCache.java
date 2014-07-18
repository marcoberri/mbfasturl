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
package it.marcoberri.mbfasturl.model.system;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.google.gson.Gson;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@Entity(value = "App.cache", noClassnameStored = true)
public class AppCache {

	@Id
	private ObjectId id;
	private Date time;
	private String key;
	private String data;

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the date
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @param time
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 *
	 * @return
	 */
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
