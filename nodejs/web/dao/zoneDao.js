var conns = require("../modules/database");

var task = {
    select : function(callback){
        var query_str = "select * from crawl_zone";
        conns.query(query_str, callback);
    },
    selectBoundary : function(minLat, minLng, maxLat, maxLng, callback){
        var query_str = "select * from crawl_zone where ";
        query_str += "     CONVERT(lat,DECIMAL(16,14)) >= " + minLat
        query_str += " AND CONVERT(lat,DECIMAL(16,14)) <= " + maxLat
        query_str += " AND CONVERT(lng,DECIMAL(16,14)) >= " + minLng
        query_str += " AND CONVERT(lng,DECIMAL(16,14)) <= " + maxLng;
        conns.query(query_str, callback);
    }
};

module.exports = task;