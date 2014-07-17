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
package it.marcoberri.mbfasturl.model.mr;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;
import com.google.gson.Gson;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */

public class StatsBaseTime {

    @Id
    @Embedded
    @Indexed(value = IndexDirection.ASC, unique = true, dropDups = true)
    private IdObject id;
    private String value;

    /**
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the id
     */
    public IdObject getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(IdObject id) {
        this.id = id;
    }


   

    /**
     *
     */
    public static class IdObject {

        private String time;
        private String url;
        private String fast;

        /**
         *
         * @param time
         */
        public void setTime(String time) {
            this.time = time;
        }

        /**
         *
         * @param url
         */
        public void setUrl(String url) {
            this.url = url;
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
            return this.fast;
        }

        /**
         *
         * @return
         */
        public String getUrl() {
            return this.url;
        }

        /**
         *
         * @return
         */
        public String getTime() {
            return this.time;
        }
    }
}
