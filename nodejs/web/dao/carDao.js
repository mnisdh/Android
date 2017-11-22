var conns = require("../modules/database");

var task = {
    selectArea : function(zone_id, callback){
        var query_str = "select * from crawl_car where zone_id = " + zone_id;
        conns.query(query_str, callback);
    }
};

module.exports = task;