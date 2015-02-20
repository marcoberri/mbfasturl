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

import java.util.Arrays;
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
@Entity("Log.ip")
public class IpSpecify {

    @Id
    private ObjectId id;
    @Indexed
    private String ip;
    private String city;
    private String postalIso;
    private String continent;
    private String continentIso;
    private String country;
    private String countryIso;
    private String division;
    private String divisionIso;
    @Indexed(IndexDirection.GEO2D)
    private double[] loc;
    private Date created = new Date();

    /**
     * @return the city
     */
    public String getCity() {
	return city;
    }

    /**
     * @return the continent
     */
    public String getContinent() {
	return continent;
    }

    /**
     * @return the continentIso
     */
    public String getContinentIso() {
	return continentIso;
    }

    /**
     * @return the country
     */
    public String getCountry() {
	return country;
    }

    /**
     * @return the countryIso
     */
    public String getCountryIso() {
	return countryIso;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
	return created;
    }

    /**
     * @return the division
     */
    public String getDivision() {
	return division;
    }

    /**
     * @return the divisionIso
     */
    public String getDivisionIso() {
	return divisionIso;
    }

    /**
     * @return the id
     */
    public ObjectId getId() {
	return id;
    }

    /**
     * @return the ip
     */
    public String getIp() {
	return ip;
    }

    /**
     * @return the loc
     */
    public double[] getLoc() {
	return loc;
    }

    /**
     * @return the postalIso
     */
    public String getPostalIso() {
	return postalIso;
    }

    @PrePersist
    void prePersist() {
	if (getCreated() == null) {
	    setCreated(new Date());
	}
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
	this.city = city;
    }

    /**
     * @param continent
     *            the continent to set
     */
    public void setContinent(String continent) {
	this.continent = continent;
    }

    /**
     * @param continentIso
     *            the continentIso to set
     */
    public void setContinentIso(String continentIso) {
	this.continentIso = continentIso;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
	this.country = country;
    }

    /**
     * @param countryIso
     *            the countryIso to set
     */
    public void setCountryIso(String countryIso) {
	this.countryIso = countryIso;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(Date created) {
	this.created = created;
    }

    /**
     * @param division
     *            the division to set
     */
    public void setDivision(String division) {
	this.division = division;
    }

    /**
     * @param divisionIso
     *            the divisionIso to set
     */
    public void setDivisionIso(String divisionIso) {
	this.divisionIso = divisionIso;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(ObjectId id) {
	this.id = id;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
	this.ip = ip;
    }

    /**
     * @param loc
     *            the loc to set
     */
    public void setLoc(double[] loc) {
	this.loc = loc;
    }

    /**
     * @param postalIso
     *            the postalIso to set
     */
    public void setPostalIso(String postalIso) {
	this.postalIso = postalIso;
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
	return "IpSpecify [id=" + id + ", ip=" + ip + ", city=" + city + ", postalIso=" + postalIso + ", continent=" + continent + ", continentIso=" + continentIso + ", country=" + country + ", countryIso=" + countryIso + ", division=" + division + ", divisionIso=" + divisionIso + ", loc=" + Arrays.toString(loc) + ", created=" + created + "]";
    }

}
