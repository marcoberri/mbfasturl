package it.marcoberri.fasturl.data.entity;

import java.util.Date;

public class IpSpecify {

    public String ip;
    public String city;
    public String postalIso;
    public String continent;
    public String continentIso;
    public String country;
    public String countryIso;
    public String division;
    public String divisionIso;
    //    @Indexed(IndexDirection.GEO2D)
    //   private double[] loc;
    public Date created = new Date();
}
