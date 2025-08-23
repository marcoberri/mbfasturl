package it.marcoberri.fasturl.resource;

import io.quarkus.logging.Log;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.smallrye.mutiny.Uni;
import it.marcoberri.fasturl.data.entity.UrlEntity;
import it.marcoberri.fasturl.service.LogService;
import it.marcoberri.fasturl.service.UrlService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;

@Path("")
public class UrlResource {

    @Inject
    UrlService urlService;

    @Inject
    LogService logService;

    @Path("/v/{fast}")
    @GET
    public Uni<UrlEntity> view(@RestPath String fast) throws InterruptedException {
        Log.info("view fast: " + fast);
        return urlService.getUrl(fast);
    }

    @Path("/s")
    @POST
    public String save(@RestForm String url,
                       @RestForm String urlComplete,
                       @RestForm String fast) {
        Log.info("Save url: " + url);
        return "test";
    }

    @Route(path = "/r/:fast", methods = Route.HttpMethod.GET)
    void redirect(@Param String fast, RoutingExchange ex) {
        Log.infof("Request for redirect with fast key: %s", fast);

        urlService.getUrl(fast)
                .subscribe()
                .with(
                        item -> {
                            if (item != null && item.url != null) {
                                // 1. Esegui il redirect immediatamente per non far attendere l'utente
                                String url = item.protocol + "://" + item.url;
                                Log.infof("Redirecting to: %s", url);
                                ex.response()
                                        .putHeader("Location", url)
                                        .setStatusCode(302) // 302 per redirect temporaneo, 301 per permanente
                                        .end();

                                // 2. Avvia il salvataggio del log in background ("fire and forget")
                                //    Aggiungiamo un subscribe con gestione dell'errore per il solo logging,
                                //    in modo che un fallimento qui non impatti l'utente.
                                Log.infof("Scheduling log persistence for fast key: %s", fast);
                                logService.saveLogView(ex.request().headers(), item.id, item.fast, item.url, ex.request())
                                        .subscribe().with(v -> {
                                        }, throwable -> Log.errorf(throwable, "Failed to save log view for key %s", fast));

                            } else {
                                Log.warnf("URL not found for fast key: %s", fast);
                                ex.response().setStatusCode(404).end("Not Found");
                            }
                        },
                        failure -> {
                            Log.errorf("Error retrieving URL key: %s", fast, failure);
                            ex.response().setStatusCode(500).end("Internal Server Error");
                        }
                );
    }
}
