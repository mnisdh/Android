var express = require("express");
var router = express.Router();

router.get("/", function(req, res, next){
    res.send("respond from user select");
});

router.post("/", function(req, res, next){
    res.send("respond from user write");
});

router.put("/", function(req, res, next){
    res.send("respond from user update");
});

router.delete("/", function(req, res, next){
    res.send("respond from user delete");
});

module.exports = router;