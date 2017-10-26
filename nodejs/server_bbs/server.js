var route = require("./a_route");
var h = require("http");

var s = h.createServer(function(request, response){
    route.process(request, response);
});

s.listen(8090, function(){ console.log("Server is running..."); });