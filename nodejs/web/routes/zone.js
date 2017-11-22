var express = require("express");
var router = express.Router();

var zoneDao = require("../dao/zoneDao");

router.get("/", function(req, res, next){
    var result = {
        code : "",
        msg : "",
        count : "",
        data : []
    };

    zoneDao.select(function(err, items, fields){
        if(err) {
            result.code = "501";
            result.msg = err;
        }
        else {
            result.code = "200";
            result.msg = "success";
            result.count = items.length;
            result.data = items;
        }

        res.send(result);
    });
});

router.get("/", function(req, res, next){
    var result = {
        code : "",
        msg : "",
        count : "",
        data : []
    };

    if(req.params){
        var minLat = req.params.minLat;
        var minLng = req.params.minLng;
        var maxLat = req.params.maxLat;
        var maxLng = req.params.maxLng;

        zoneDao.selectBoundary(minLat, minLng, maxLat, maxLng, function(err, items, fields){
            if(err) {
                result.code = "501";
                result.msg = err;
            }
            else {
                result.code = "200";
                result.msg = "success";
                result.count = items.length;
                result.data = items;
            }

            res.send(result);
        });
    }
    else{
        zoneDao.select(function(err, items, fields){
            if(err) {
                result.code = "501";
                result.msg = err;
            }
            else {
                result.code = "200";
                result.msg = "success";
                result.count = items.length;
                result.data = items;
            }

            res.send(result);
        });
    }
});

module.exports = router;