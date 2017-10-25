var port = 8090;

var h = require("http");
var u = require("url");
var q = require("querystring");
var fs = require("fs");
var m = require("mime");

var s = h.createServer(function(request, response){
    var url = u.parse(request.url);
    
    //주소에서 명령어 = 서버자원의 id(uri)를 먼저 꺼낸다
    var path = url.pathname;
    var cmds = path.split("/");

    switch(cmds[1]){
        case "getfile":
            if(request.method == "POST"){
                // body에 넘어온 filepath
            }
            else if(request.method == "GET"){
                var query = q.parse(url.query);
                if(query.filepath){
                    var filepath = query.filepath;
                    var mType = m.getType(filepath);
                    if(mType == "video/mp4"){
                        response.writeHead(200, { 'Content-Type' : mType });

                        // 1. stream 생성
                        var stream = fs.createReadStream(filepath);

                        var count = 0;
                        // 2. stream 전송 이벤트 등록
                        stream.on('data', function(fragment){
                            // 데이터를 읽을 수 있는 최소 단위의 조각이 콜백함수로 전달된다
                            response.write(fragment);
                            console.log("count : " + count++);
                        });
                        // 3. stream 완료 이벤트 등록
                        stream.on('end', function(){ response.end(); });
                        // 4. stream 에러 이벤트 등록
                        stream.on('error', function(error){ response.end(error + ""); });
                    }
                    else{
                        // path에 해당되는 파일을 읽는다  파일경로, 콜백함수
                        var file = fs.readFile(filepath, function(error, data){
                            if(error){
                                response.writeHead(500,{'Content-Type' : 'text/html'});
                                response.end("<H1>404 Page not found!</H1>");
                            }
                            else{
                                response.writeHead(200, { 'Content-Type' : mType });
                                response.end(data);
                            }
                        });
                    }
                }
            }
        break;
        case "html":
            var filepath = path.substring(1);
            var file = fs.readFile(filepath, "utf-8", function(error, data){
                if(error){
                    response.writeHead(500,{'Content-Type' : 'text/html'});
                    response.end("<H1>404 Page not found!</H1>");
                }
                else{
                    console.log(filepath);
                    response.writeHead(200, { 'Content-Type' : 'text/html' });
                    response.end(data);
                }
            });
        break;
        case "signin":
            // request.url은 위에서 parsing해서 url 변수에 담아둔 상태
            var id = "mnisdh";
            var pw = "1234";
            var sign = q.parse(url.query);
            
            var postData = "";
            request.on("data", function(data){ postData += data; });
            request.on("end", function(){ 
                var sign = q.parse(postData);
                console.log(sign.id + " / " + sign.pw);
                if(sign.id == id || sign.pw == pw){
                    response.writeHead(200,{'Content-Type' : 'text/html'});
                    response.end("OK");
                }
                else{
                    response.writeHead(200,{'Content-Type' : 'text/html'});
                    response.end("FAIL");
                }
            });
        break;
        case "file":
            var filepath = path.substring(1);
            var mType = m.getType(filepath);
            if(mType == "video/mp4"){
                response.writeHead(200, { 'Content-Type' : mType });

                // 1. stream 생성
                var stream = fs.createReadStream(filepath);

                var count = 0;
                // 2. stream 전송 이벤트 등록
                stream.on('data', function(fragment){
                    // 데이터를 읽을 수 있는 최소 단위의 조각이 콜백함수로 전달된다
                    response.write(fragment);
                    console.log("count : " + count++);
                });
                // 3. stream 완료 이벤트 등록
                stream.on('end', function(){
                    response.end();
                });
                // 4. stream 에러 이벤트 등록
                stream.on('error', function(error){
                    response.end(error + "");
                });
            }
            else{
                var file = fs.readFile(filepath, function(error, data){
                    if(error){
                        response.writeHead(500,{'Content-Type' : 'text/html'});
                        response.end("<H1>404 Page not found!</H1>");
                    }
                    else{
                        response.writeHead(200, { 'Content-Type' : mType });
                        response.end(data);
                    }
                });
            }
        break;
        default:
            response.writeHead(404,{'Content-Type' : 'text/html'});
            response.end("404 Page not found!");
        break;
    }
});

s.listen(port, function(){
    console.log(port + " server is running...");
});



var errorToHtml = function(message){
    return "<html><meta charset='utf-8'/><body>" + message + "</body></html>";
};
