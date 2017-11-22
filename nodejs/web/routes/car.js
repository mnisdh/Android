var express = require("express");
var router = express.Router();

var dao = require("../dao/carDao");

router.get("/", function(req, res, next){
    var result = {
        code : "404",
        msg : "not found",
        count : "",
        data : []
    };

    res.send(result);
});

router.get("/:zone_id", function(req, res, next){
    var result = {
        code : "",
        msg : "",
        count : "",
        carData : []
    };

    dao.selectArea(req.params.zone_id, function(err, items, fields){
        if(err) {
            result.code = "501";
            result.msg = err;
        }
        else {
            result.code = "200";
            result.msg = "success";
            result.count = items.length;
            result.carData = items;
        }

        res.send(result);
    });
});

module.exports = router;