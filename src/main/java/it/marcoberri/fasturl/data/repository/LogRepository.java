package it.marcoberri.fasturl.data.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import it.marcoberri.fasturl.data.entity.LogEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogRepository implements ReactivePanacheMongoRepository<LogEntity> {

}
