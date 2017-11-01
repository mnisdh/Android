var h = require("http");
var r = require("request");


const fcmServerUrl = "https://fcm.googleapis.com/fcm/send";
const serverKey = "AAAAo8NvlQw:APA91bFE951cDu3uW_wW2e32jHkw_G_S6SOVa5Dpn9ZaVKkpyrolHFHjtzkUg0NhRbuNrSBEMR22bvGAQgO2YsaW3S8xihTew2yi370EzVpzCVIgCnKSExlgY1bADhbn26OoLCOnNhB2";

var msg = {
    to : "",
    notification : {
        title : "노티바에 나타나는 타이틀",
        body : ""
    }
};

var s = h.createServer(function(request, response){
    if(request.url != "/sendNotification") response.end("404 page not found!");
    else{
        // post 데이터 수신
        var postdata = "";
        request.on("data", function(data){ postdata += data; });
        request.on("end", function(){
            // json 스트링을 객체로 변환
            var postObj = JSON.parse(postdata);

            // 메시지 데이터를 완성
            msg.to = postObj.to;
            msg.notification.body = postObj.msg;

            // 메시지 fcm 서버로 전송
            r(
                // http 메시지 객체
                {
                    url : fcmServerUrl,
                    method : "POST",
                    headers : {
                        'Authorization' : "key=" + serverKey,
                        'Content-Type' : "application/json"
                    },
                    body : JSON.stringify(msg)
                },
                // 콜백 함수
                function(err, answer, body){
                    var result = {
                        code : answer.statusCode,
                        msg : body
                    };
                    response.writeHead(200, {"Content-Type":"plain/text"});
                    response.end(JSON.stringify(result));
                }
            );
        });
    }
});

s.listen(8090, function(){ console.log("server is running..."); });