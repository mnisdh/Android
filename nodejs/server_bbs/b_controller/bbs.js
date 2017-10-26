// ./controller/bbs.js
var bbs = require("../c_dao/bbs");

exports.select = function(request, response, query){
    bbs.select(query, function(dataset){
        console.log(dataset);
        var result = {
            code : 200,
            msg : "입력완료",
            bbs : dataset
        };
        
        response.end(JSON.stringify(result));
    });
};

exports.create = function(request, response, body){
    bbs.create(body, function(result_code){
        var result = {
            code : result_code,
            msg : "입력완료"
        };

        response.end(JSON.stringify(result));
    });
};

exports.update = function(request, response, body){
    response.end("");
};

exports.delete = function(request, response, body){
    response.end("");
};
