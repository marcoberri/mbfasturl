package it.marcoberri.fasturl.resource;

import io.smallrye.mutiny.Uni;
import it.marcoberri.fasturl.data.entity.UrlEntity;
import it.marcoberri.fasturl.service.UrlService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("")
public class UrlResource {

    @Inject
    UrlService urlService;

    @Path("/v/{fast}")
    @GET
   // @Produces(MediaType.SERVER_SENT_EVENTS)
    //@RestStreamElementType(MediaType.APPLICATION_JSON)
    public Uni<UrlEntity> view(@RestPath String fast) throws InterruptedException {
        return urlService.getUrl(fast);
    }

    @Path("/s")
    @POST
    public String save(@RestForm String url,
                       @RestForm String urlComplete,
                       @RestForm String fast) {
        return urlService.politeHello("Test");
    }

}
