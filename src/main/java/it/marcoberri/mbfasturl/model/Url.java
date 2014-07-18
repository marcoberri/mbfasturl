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
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.utils.IndexDirection;

import com.google.gson.Gson;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@Entity(value = "Url.url", noClassnameStored = true)
public class Url {

	@Id
	private ObjectId id;
	@Indexed(value = IndexDirection.ASC, name = "fast", unique = true, dropDups = true)
	private String fast;
	@Indexed(value = IndexDirection.ASC, name = "url", unique = true, dropDups = true)
	private String url;
	private Date created;
	private Date ending;
	private String protocol = "http";
	private int port = 80;
	private ObjectId qrcodeSmall;
	private ObjectId qrcodeMedium;
	private ObjectId qrcodeBig;
	@Transient
	private String urlComplete;

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @return the ending
	 */
	public Date getEnding() {
		return ending;
	}

	/**
	 * @return the fast
	 */
	public String getFast() {
		return fast;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @return the qrcodeBig
	 */
	public ObjectId getQrcodeBig() {
		return qrcodeBig;
	}

	/**
	 * @return the qrcodeMedium
	 */
	public ObjectId getQrcodeMedium() {
		return qrcodeMedium;
	}

	/**
	 * @return the qrcodeSmall
	 */
	public ObjectId getQrcodeSmall() {
		return qrcodeSmall;
	}

	/**
	 *
	 * @return
	 */
	public String getRedirectComplete() {
		return this.protocol + "://" + (this.url).replaceAll("//", "/");
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the urlComplete
	 */
	public String getUrlComplete() {
		return urlComplete;
	}

	@PrePersist
	void prePersist() {
		if (getCreated() == null) {
			setCreated(new Date());
		}
		if (getProtocol() == null) {
			setProtocol("http");
		}
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @param ending
	 *            the ending to set
	 */
	public void setEnding(Date ending) {
		this.ending = ending;
	}

	/**
	 * @param fast
	 *            the fast to set
	 */
	public void setFast(String fast) {
		this.fast = fast;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @param qrcodeBig
	 *            the qrcodeBig to set
	 */
	public void setQrcodeBig(ObjectId qrcodeBig) {
		this.qrcodeBig = qrcodeBig;
	}

	/**
	 * @param qrcodeMedium
	 *            the qrcodeMedium to set
	 */
	public void setQrcodeMedium(ObjectId qrcodeMedium) {
		this.qrcodeMedium = qrcodeMedium;
	}

	/**
	 * @param qrcodeSmall
	 *            the qrcodeSmall to set
	 */
	public void setQrcodeSmall(ObjectId qrcodeSmall) {
		this.qrcodeSmall = qrcodeSmall;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param urlComplete
	 *            the urlComplete to set
	 */
	public void setUrlComplete(String urlComplete) {
		this.urlComplete = urlComplete + "/" + fast;
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
		return "Url [id=" + id + ", fast=" + fast + ", url=" + url + ", created=" + created + ", ending=" + ending + ", protocol=" + protocol + ", port=" + port + ", qrcodeSmall=" + qrcodeSmall + ", qrcodeMedium=" + qrcodeMedium + ", qrcodeBig=" + qrcodeBig + ", urlComplete=" + urlComplete + "]";
	}

}
