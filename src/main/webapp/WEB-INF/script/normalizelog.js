fasturl = {};

var dbFastUrl = db.getSisterDB("FastUrl");

fasturl.normLog = function() {
    var log_coll = dbFastUrl.Log.log;
    var url_coll = dbFastUrl.Url.url;
    var count = log_coll.count();
    print("Tot record :" + count);
    var cursor = log_coll.find();
    cursor.forEach(function(logData) {
        var url = url_coll.findOne({"_id": logData.urlId}, {"_id": false, "fast": 1, "url": 1});
        if (url && url.url && url.fast) {
            log_coll.update({"_id": logData["_id"]}, {$set: url});
        }
    });

}

 