package it.marcoberri.fasturl.service;

import io.smallrye.mutiny.Uni;
import it.marcoberri.fasturl.data.entity.UrlEntity;
import it.marcoberri.fasturl.data.repository.UrlRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UrlService {

    @Inject
    UrlRepository urlRepository;

    public String politeHello(String name){
        return "Hello Mr/Mrs " + name;
    }
    public Uni<UrlEntity> getUrl(String fast){
        return urlRepository.findByFast(fast);
    }
}
