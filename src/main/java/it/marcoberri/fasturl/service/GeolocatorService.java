package it.marcoberri.fasturl.service;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

@Startup
@ApplicationScoped
public class GeolocatorService {

    private final DatabaseReader reader;

    public GeolocatorService() throws IOException {
        InputStream dbPath = getClass().getClassLoader().getResourceAsStream("GeoLite2-City.mmdb");
        reader = new DatabaseReader.Builder(dbPath).withCache(new CHMCache()).build();
    }

    public CityResponse getCityResponse(String host) throws IOException, GeoIp2Exception {
        return reader.city(InetAddress.getByName(host));
    }
}
