var h = require("http");
var u = require("url");
var q = require("querystring");
var m = require("mongodb").MongoClient;

var s = h.createServer(function(request, response){
    var url = u.parse(request.url);
    var cmds = url.pathname.split("/");

    switch(cmds[1]){
        case "signin":
            var postData = "";
            request.on('data', function(data){ postData += data; });
            request.on('end', function(){
                //var query = q.parse(postData);
                var query = JSON.parse(postData);

                if(query.id && query.pw){
                    // mongo db 주소 구조 : 프로토콜://주소:포트/데이터베이스명 -> db변수에 전달
                    m.connect("mongodb://localhost:27017/testdb", function(error, db){
                        if(error){
                            response.end(error);
                        }
                        else{
                            // db.collection('테이블명').명령어();
                            //db.collection('user').insert( { name:'shin', age:33 } );
                            var cursor = db.collection('user').find(query);
                            

                            // 데이터셋의 처리방법 2가지
                            // 1. forEach 비동기
                            // 2. each 동기
                            
                            // cursor.each(function(item){
                            //     console.log("1" + result);
                            //     result = "OK";
                            //     console.log("2" + result);
                            // });
                            var obj = {
                                code : "300",
                                msg : "FAIL"
                            };
                            cursor.toArray(function(error, dataset){
                                if(dataset.length > 0) {
                                    obj.code = "200";
                                    obj.msg = "OK";
                                }
                                response.end(JSON.stringify(obj));
                            });
                        }
                    });
                }
                else{
                    response.end("id or password is wrong!");
                }
            });
        break;
        default:
            response.end("page not found");
        break;
    }

    
});

s.listen(8090, function(){ console.log("server is running.."); });