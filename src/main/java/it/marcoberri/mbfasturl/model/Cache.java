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
package it.marcoberri.mbfasturl.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.utils.IndexDirection;

import com.google.gson.Gson;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@Entity(value = "Cache", noClassnameStored = true)
public class Cache {

	@Id
	private ObjectId id;
	@Indexed(value = IndexDirection.ASC)
	private String cacheKey;
	@Indexed(value = IndexDirection.ASC)
	private String servletName;
	private Date create;
	private Date lastRead;
	private String gridId;

	/**
	 * @return the key
	 */
	public String getCacheKey() {
		return cacheKey;
	}

	/**
	 * @return the create
	 */
	public Date getCreate() {
		return create;
	}

	/**
	 * @return the gridId
	 */
	public String getGridId() {
		return gridId;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @return the lastRead
	 */
	public Date getLastRead() {
		return lastRead;
	}

	/**
	 *
	 * @return
	 */
	public String getServletName() {
		return servletName;
	}

	@PrePersist
	void prePersist() {
		setLastRead(new Date());
	}

	/**
	 * @param cacheKey
	 */
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	/**
	 * @param create
	 *            the create to set
	 */
	public void setCreate(Date create) {
		this.create = create;
	}

	/**
	 * @param gridId
	 *            the gridId to set
	 */
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @param lastRead
	 *            the lastRead to set
	 */
	public void setLastRead(Date lastRead) {
		this.lastRead = lastRead;
	}

	/**
	 *
	 * @param servletName
	 */
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	/**
	 *
	 * @return
	 */
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public String toString() {
		return "Cache [id=" + id + ", cacheKey=" + cacheKey + ", servletName=" + servletName + ", create=" + create + ", lastRead=" + lastRead + ", gridId=" + gridId + "]";
	}

}
