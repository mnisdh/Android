// ./route/index.js
var u = require("url");
var q = require("querystring");
var bbsC = require("../b_controller/bbs");

exports.process = function(request, response){
    var url = u.parse(request.url);
    var method = request.method.toLowerCase();
    var cmds = url.pathname.split("/");

    
    switch(cmds[1]){
        case "bbs":
            if("get" === method){
                bbsC.select(request, response, q.parse(url.query));
            }
            else{
                var body = "";
                request.on("data", function(data){ body += data; });
                request.on("end", function(){
                    body = q.parse(body);
                    switch(method){
                        case "post": bbsC.create(request, response, body); break;
                        case "put": bbsC.update(request, response, body); break;
                        case "delete": bbsC.delete(request, response, body); break;
                    }
                });
            }
        break;
    }
};