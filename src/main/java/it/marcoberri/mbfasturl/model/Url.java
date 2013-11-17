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

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import com.github.jmkgreen.morphia.annotations.PrePersist;
import com.github.jmkgreen.morphia.annotations.Transient;
import com.github.jmkgreen.morphia.utils.IndexDirection;
import com.google.gson.Gson;
import java.util.Date;
import org.bson.types.ObjectId;

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
     * @return the id
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(ObjectId id) {
        this.id = id;
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
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * @return the fast
     */
    public String getFast() {
        return fast;
    }

    /**
     * @param fast the fast to set
     */
    public void setFast(String fast) {
        this.fast = fast;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the ending
     */
    public Date getEnding() {
        return ending;
    }

    /**
     * @param ending the ending to set
     */
    public void setEnding(Date ending) {
        this.ending = ending;
    }


    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the urlComplete
     */
    public String getUrlComplete() {
        return urlComplete;
    }

    /**
     *
     * @return
     */
    public String getRedirectComplete() {
        return this.protocol + "://" + (this.url).replaceAll("//","/");
    }
        
    /**
     * @param urlComplete the urlComplete to set
     */
    public void setUrlComplete(String urlComplete) {
        this.urlComplete = urlComplete + "/" + fast;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

 
    /**
     * @return the qrcodeSmall
     */
    public ObjectId getQrcodeSmall() {
        return qrcodeSmall;
    }

    /**
     * @param qrcodeSmall the qrcodeSmall to set
     */
    public void setQrcodeSmall(ObjectId qrcodeSmall) {
        this.qrcodeSmall = qrcodeSmall;
    }

    /**
     * @return the qrcodeMedium
     */
    public ObjectId getQrcodeMedium() {
        return qrcodeMedium;
    }

    /**
     * @param qrcodeMedium the qrcodeMedium to set
     */
    public void setQrcodeMedium(ObjectId qrcodeMedium) {
        this.qrcodeMedium = qrcodeMedium;
    }

    /**
     * @return the qrcodeBig
     */
    public ObjectId getQrcodeBig() {
        return qrcodeBig;
    }

    /**
     * @param qrcodeBig the qrcodeBig to set
     */
    public void setQrcodeBig(ObjectId qrcodeBig) {
        this.qrcodeBig = qrcodeBig;
    }
}
