package it.marcoberri.fasturl.data.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import it.marcoberri.fasturl.data.entity.LogViewEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogViewRepository implements ReactivePanacheMongoRepository<LogViewEntity> {

}
