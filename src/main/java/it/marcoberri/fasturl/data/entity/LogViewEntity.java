package it.marcoberri.fasturl.data.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.HashMap;

@MongoEntity(collection = "Log.log")
public class LogViewEntity extends ReactivePanacheMongoEntity {


    public HashMap<String, String> headers;
    public Date created;
    public String ip;
    public ObjectId urlId;
    public String fast;
    public String url;
    public IpSpecify ipSpecify;
    public UAgent agent;

    public void addHeader(String key, String value) {
        if (this.headers == null)
            this.headers = new HashMap<>();
        this.headers.put(key, value);
    }

}
