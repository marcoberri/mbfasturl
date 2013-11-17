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

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import com.github.jmkgreen.morphia.annotations.PrePersist;
import com.github.jmkgreen.morphia.annotations.Property;
import com.google.gson.Gson;
import java.util.Date;
import java.util.HashMap;
import org.bson.types.ObjectId;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@Entity("Log.log")
public class Log {

    @Id
    private ObjectId id;
    private HashMap<String,String> headers;
    private Date created;
    @Indexed
    private String ip;
    @Indexed
    @Property
    private ObjectId urlId;
    private String fast;
    private String url;
    @Embedded
    private IpSpecify ipSpecify;
    @Embedded
    private UAgent agent;

    

    @PrePersist
    void prePersist() {
        if (getCreated() == null) {
            setCreated(new Date());
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
     *
     * @param key
     * @param value
     */
    public void addHeader(String key, String value) {
        if (this.getHeaders() == null) {
            setHeaders(new HashMap());
        }

        getHeaders().put(key.toLowerCase(), value);

    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the headers
     */
    public HashMap<String,String> getHeaders() {
        return headers;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(HashMap<String,String> headers) {
        this.headers = headers;
    }

    /**
     * @return the urlId
     */
    public ObjectId getUrlId() {
        return urlId;
    }

    /**
     * @param urlId the urlId to set
     */
    public void setUrlId(ObjectId urlId) {
        this.urlId = urlId;
    }

    /**
     *
     * @param fast
     */
    public void setFast(String fast) {
        this.fast = fast;
    }

    /**
     *
     * @return
     */
    public String getFast() {
        return fast;
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
     * @return the ipSpecify
     */
    public IpSpecify getIpSpecify() {
        return ipSpecify;
    }

    /**
     * @param ipSpecify the ipSpecify to set
     */
    public void setIpSpecify(IpSpecify ipSpecify) {
        this.ipSpecify = ipSpecify;
    }

    /**
     * @return the agent
     */
    public UAgent getAgent() {
        return agent;
    }

    /**
     * @param agent the agent to set
     */
    public void setAgent(UAgent agent) {
        this.agent = agent;
    }




}
