package it.marcoberri.fasturl.data.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

import java.util.Date;

@MongoEntity(collection = "Url.url")
public class UrlEntity extends ReactivePanacheMongoEntity {

    public String fast;
    public String url;
    public Date created;
    public Date ending;
    public String protocol = "http";
    public int port = 80;
//        private ObjectId qrcodeSmall;
    //       private ObjectId qrcodeMedium;
    //      private ObjectId qrcodeBig;
    //      @Transient
    //      private String urlComplete;
}
