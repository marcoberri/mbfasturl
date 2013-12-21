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

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import com.github.jmkgreen.morphia.utils.IndexDirection;
import com.google.gson.Gson;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@Entity(value = "Stats.CountTimeMonthCountryUrl", noClassnameStored = true)
public class StatsCountTimeMonthCountryUrl extends StatsBaseTime {

    @Id
    @Embedded
    @Indexed(value = IndexDirection.ASC, unique = true, dropDups = true)
    private IdObjectCountry id;

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * @param id the id to set
     */
    public void setId(IdObjectCountry id) {
        this.id = id;
    }

    /**
     *
     */
    public static class IdObjectCountry extends StatsBaseTime.IdObject {

        private String countryIso;
        private String country;

        /**
         * @return the countryIso
         */
        public String getCountryIso() {
            return countryIso;
        }

        /**
         * @param countryIso the countryIso to set
         */
        public void setCountryIso(String countryIso) {
            this.countryIso = countryIso;
        }

        /**
         * @return the country
         */
        public String getCountry() {
            return country;
        }

        /**
         * @param country the country to set
         */
        public void setCountry(String country) {
            this.country = country;
        }

    }
}