var qs = require("querystring");
var cheerio = require("cheerio");

// http 통신으로 request를 할 모듈
var request = require("request");
// mysql db 연결정보
var mysql = require("mysql");
var conInfo = {
    host : '127.0.0.1',
    user: 'root',
    password : 'root',
    port : '3306',
    database : 'socar'
};
var con = mysql.createConnection(conInfo);
con.connect();

var zones;
var zone_idx = 0;

var query_str = "select * from crawl_zone";
con.query(query_str, function(err, items, fields){
    zones = items;
    runRequest();
});


function runRequest(){
    var obj = zones[zone_idx];

    var form = {
        way : "round",
        start_at : "2017-11-23 15:00",
        end_at:"2017-11-23 16:00",
        loc_type:2,
        class_id:"",
        lat:obj.lat,
        lng:obj.lng,
        zone_id:obj.zone_id,
        region_name:obj.zone_name,
        distance:1.5
    };

    var formData = qs.stringify(form);
    var formLength = formData.length;
    request(
        {
            headers : {
                'Content-Length' : formLength,
                'Content-Type' : 'application/x-www-form-urlencoded'
            },
            url : "https://www.socar.kr/reserve/search",
            body : formData,
            method : "POST"
        },
        function(err, answer, body){
            if(!err){
                var start = body.indexOf("<!-- list -->");
                var end = body.indexOf("<!-- //list -->");

                var sections = body.substring(start, end).split(/<div class="section/);

                for(var idx in sections){
                    var section = sections[idx];

                    var html = cheerio.load(section);

                    var zone_name = html('.arti > h4').text();
                    if(zone_name === "") continue;

                    var zone_id = html('.arti > em').text();
                    var car_name = html('.carInfo .desc > h5').text();
                    var car_id = html('.carInfo .desc > em').text();
                    var oil_type = html('.carInfo .desc .spec > em').text();
                    var option = html('.carInfo .desc .spec').text();
                    option = option.split("옵션 : ");
                    option = option[1].split("\n")[0];

                    var price_name = html('.price > dl > dt').text();
                    var price = html('.price > dl > dd').text();
                    var driving_fee = html('.oil').text();

                    // db에 연결하고 obj.result 에 있는 모든 값을 insert
                    var query_str = "insert into crawl_car( zone_id,zone_name";
                    query_str += ",car_name,car_id,oil_type,price_name";
                    query_str += ",price,driving_fee)";
                    query_str += " values(?,?,?,?,?,?,?,?)";
                    var values = [zone_id,zone_name,car_name,car_id,oil_type,price_name,price,driving_fee];
                    con.query(query_str, values, function(error,result){
                        if(error){
                            console.log(error);
                        }else{
                            console.log("insertion completed! : "+ car_name);
                        }
                    });
                }
            }
        }
    );

    zone_idx++;

    if(zone_idx < zones.length) setTimeout(runRequest, 1000);
}