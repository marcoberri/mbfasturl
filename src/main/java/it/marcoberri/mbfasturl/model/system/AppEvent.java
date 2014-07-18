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
@Entity(value = "App.event", noClassnameStored = true)
public class AppEvent {

	@Id
	private ObjectId id;
	private Date date = new Date();
	private String action;
	private boolean result = true;
	private String note;
	private String category;
	private Long time;

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @return the result
	 */
	public boolean getResult() {
		return isResult();
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Long time) {
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
