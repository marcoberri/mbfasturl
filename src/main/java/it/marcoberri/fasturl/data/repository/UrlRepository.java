package it.marcoberri.fasturl.data.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import it.marcoberri.fasturl.data.entity.UrlEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UrlRepository implements ReactivePanacheMongoRepository<UrlEntity> {
    public Uni<UrlEntity> findByFast(String fast) {
        return find("fast",fast).firstResult();
    }
}
