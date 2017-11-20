var fs = require("fs");
var s = require('sleep');
var mysql = require('mysql');
var connInfo = {
    host : '127.0.0.1',
    user : 'root',
    password : 'mysql',
    port : '3306',
    databse : 'socar'
};

// http 통신으로 request를 할 모듈
var request = require("request");


var rows;


var req_url = "https://www.socar.kr/reserve#seoul";
request(
    {
        url : req_url,
        method : "get"
    },
    // req_url로 request를 전송하면 결과값을 아래 함수로 콜백
    function(err, answer, body){
        var start = body.indexOf("load_socarzone_list.done(function(code){");
        var end = body.indexOf("spock zone 이동, UI 클릭에 따라.");
        rows = body.substring(start, end).split(/\r?\n/);
      
        run();
    }
)

function run(){
    for(var row in rows){
        var rowStr = rows[row];
        if(rowStr.indexOf("daum_map.add_maker_and_pin") >= 0){
            var prams = rowStr.split(/,/);
            if(prams[2]) {
                getdetail(prams[2]);
                s.sleep(2);
            }
        }
    }
}

function getdetail(zone_id){
    req_url = "https://api.socar.kr/reserve/zone_info?zone_id=" + zone_id.replace(' ', '');
    console.log(req_url);
    request(
        {
            url : req_url,
            method : "get"
        },
        // req_url로 request를 전송하면 결과값을 아래 함수로 콜백
        function(err, answer, body){
            //var obj = JSON.parse(body).result;
            console.log(answer);

            // if(err) console.log(zone_id + " : " + err);
            // else console.log(zone_id + " : ok");
    
            // fs.writeFile("/raws/" + zone_id + ".txt", body, 'utf-8', function(error){
            //     if(error) console.log(zone_id + error);
            //     else console.log(zone_id + "ok");
            // });
        }
    );
}