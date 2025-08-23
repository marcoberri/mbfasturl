package it.marcoberri.fasturl.data.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "Url.url")
public class UrlEntity extends ReactivePanacheMongoEntity {

    public String fast;
    //        @Indexed(value = IndexDirection.ASC, name = "url", unique = true, dropDups = true)
    public String url;
    //        private Date created;
//        private Date ending;
    public String protocol = "http";
    public int port = 80;
//        private ObjectId qrcodeSmall;
    //       private ObjectId qrcodeMedium;
    //      private ObjectId qrcodeBig;
    //      @Transient
    //      private String urlComplete;
}
