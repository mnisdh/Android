var port = 8090;

// 1. 서버 모듈(라이브러리)을 import
var http = require("http");
var url = require("url");
var qString = require("querystring");

// 2. 서버 모듈을 사용해서 서버를 정의
var server = http.createServer(function(request, response){
    var parsedUrl = url.parse(request.url);
    var parsedQString = qString.parse(parsedUrl.query);


    response.end(request.url);
    // response.writeHead(200, { 'Content-Type' : 'text/html' });
});

// 3. 서버 실행
server.listen(port, function(){
    console.log(port + " server is running...");
});
