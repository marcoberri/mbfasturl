package it.marcoberri.fasturl.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import io.netty.util.internal.StringUtil;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import it.marcoberri.fasturl.data.entity.LogViewEntity;
import it.marcoberri.fasturl.data.repository.LogViewRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class LogService {

    @Inject
    LogViewRepository logViewRepository;

    @Inject
    GeolocatorService geolocatorService;

    private UserAgentAnalyzer uaa = null;

    @PostConstruct
    public void automaticStartup() {
        uaa = UserAgentAnalyzer.newBuilder()
                .showFullVersion()
                .useJava8CompatibleCaching()
                .withAllFields()
                .hideMatcherLoadStats()
                .withCache(1000)
                .immediateInitialization()
                .build();
    }


    public Uni<Void> saveLogView(MultiMap headers, ObjectId id, String fast, String url, HttpServerRequest request) {

        LogViewEntity logViewEntity = new LogViewEntity();
        logViewEntity.urlId = id;

        HashMap<String, String> m = new HashMap<>();
        headers.entries().forEach(entry -> m.put(entry.getKey(), entry.getValue()));
        logViewEntity.headers = m;

        logViewEntity.created = new Date();

        logViewEntity.fast = fast;
        logViewEntity.url = url;

        logViewEntity.addHeader("request_remote_addr", request.remoteAddress().hostAddress());
        logViewEntity.addHeader("request_remote_host", request.remoteAddress().host());
        //logViewEntity.addHeader("request_remote_user", request.remoteAddress());
        logViewEntity.addHeader("request_remote_remote_port", "" + request.remoteAddress().port());
        logViewEntity.addHeader("request_remote_url", request.remoteAddress().path());

        // questo è da capire se ha senso
        String ip = request.remoteAddress().hostAddress();

        if ((ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) && logViewEntity.headers.containsKey("x-forwarded-for")) {
            logViewEntity.addHeader("MBURL_request_getRemoteAddr", ip);
            ip = logViewEntity.headers.get("x-forwarded-for");
        }

        // fix per "ip" : "192.168.132.114, 79.174.225.43"
        if (ip.contains(",")) {
            final String[] split = ip.split(",");
            if (split.length > 0)
                ip = split[split.length - 1];
        }

        ip = ip.trim();

        logViewEntity.ip = ip;


        AtomicReference<String> ua = new AtomicReference<>(headers.get("user-agent"));
        Log.infof("User Agent: %s", ua);

        if (StringUtil.isNullOrEmpty(ua.get())) {
            headers.forEach((key, value) ->
            {
                if (key.equalsIgnoreCase("user-agent")) {
                    ua.set(value);
                }
            });
        }

        UserAgent userAgent = uaa.parse(ua.get());
        Log.infof("User Agent parsed: %s", userAgent);

        logViewEntity.agentYauaa = new HashMap<>();
        userAgent.getAvailableFieldNamesSorted().forEach(f -> logViewEntity.agentYauaa.put(f, userAgent.getValue(f)));


        try {
            CityResponse result = geolocatorService.getCityResponse(ip);
            Log.infof("City Response: %s", result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeoIp2Exception e) {
            throw new RuntimeException(e);
        }

        // persist() restituisce un Uni. Dobbiamo sottoscriverlo per eseguire l'operazione.
        // Restituendo l'Uni, permettiamo al chiamante di concatenare l'operazione.
        // Usiamo .replaceWithVoid() per segnalare il completamento senza restituire l'entità.
        return logViewRepository.persist(logViewEntity).replaceWithVoid();
    }
}
