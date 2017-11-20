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
                    var zone_info_popup = html('.arti .zone_info_popup').text();
                    var desc = html('.carInfo .desc').text();
                    var spec = html('.carInfo .spec').text();

                    console.log(zone_info_popup);
                    console.log(desc);
                    console.log(spec);
                }


                // var html = cheerio.load(body.substring(start, end));
                // var sections = html('.section');
                // for(sectionIdx in sections){
                //     var arti = sections[sectionIdx]('.arti');
                // }


                //console.log(html('.section').text());

                // var sections = body.substring(start, end).split(/<div class="section/);

                // for(var idx in sections){
                //     var section = sections[idx];

                //     var name = section.substring(section.indexOf("<H5>"), section.indexOf("</H5>"));
                //     var spec = section.substring(section.indexOf("spec"), section.indexOf("more carDetail view_detail_car"));
                //     var name = section.substring(section.indexOf("<H5>"), section.indexOf("</H5>"));
                // }
                

                // console.log(body);


                // //var text = unescape(replaceAll(body, "\\", "%"))
                // var obj = JSON.parse(body).result;
                // // db에 연결하고 obj.result 에 있는 모든 값을 insert
                // var query_str = "insert into crawl_car( zone_id,zone_name";
                // query_str += ",zone_attr,zone_alias,zone_addr,zone_props";
                // query_str += ",oper_time,oper_way,state,albe_num,total_num";
                // query_str += ",lat,lng,visit,visit_link,link,notice,notice_oneway)";
                // query_str += " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                // var values = [obj.zone_id, obj.zone_name,obj.zone_attr
                //     ,obj.zone_alias,obj.zone_addr,obj.zone_props,obj.oper_time
                //     ,obj.oper_way,obj.state,obj.albe_num,obj.total_num
                //     ,obj.lat,obj.lng,obj.visit,obj.visit_link,obj.link
                //     ,obj.notice,obj.notice_oneway];
                // con.query(query_str, values, function(error,result){
                //     if(error){
                //         console.log(error);
                //     }else{
                //         console.log("insertion completed! : "+ obj.zone_id);
                //     }
                // });
            }
        }
    );

    zone_idx++;

    if(zone_idx < zones.length) setTimeout(runRequest, 1000);
}