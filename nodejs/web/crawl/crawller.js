// http 통신으로 request를 할 모듈
var request = require("request");
var fs = require("fs");
// mysql db 연결정보
var mysql = require("mysql");
var conInfo = {
    host : '127.0.0.1',
    user: 'root',
    password : 'root',
    port : '3306',
    database : 'socar'
};
var con;

var req_url = "https://www.socar.kr/reserve#seoul";

var result;
var result_idx = 0; 

request(
    {
        url : req_url,
        method : "get"
    },
    // req_url 로 request 를 전송하면 결과값을 아래 함수로 돌려 받는다
    function(error, answer, body){
        var start = body.indexOf("load_socarzone_list.done(function(code){")
        var end = body.indexOf("spock zone 이동, UI 클릭에 따라")
        body = body.substring(start, end);
        // 전체 텍스트를 줄단위로 저장
        
        result = body.split(/\r?\n/);
        // 데이터베이스 연결
        con = mysql.createConnection(conInfo);
        con.connect();

        run_result();
    }
)

function run_result(){
    //console.log(result)
    //console.log("result_idx="+result_idx)
    var temp = result[result_idx]; // 한줄씩 꺼내서
    if(temp.indexOf("daum_map.add_maker_and_pin") >= 0){
        var zone_id = temp.split(",")[2]; // 한줄 데이터를 ,로 분리한 후 두번째 값이 아이디
        request_zone_id(zone_id);
        console.log("zone_id="+zone_id);
    }
    result_idx++;
    if(result_idx <= result.length)
        setTimeout(run_result,1000);
}



var zone_id_url = "https://api.socar.kr/reserve/zone_info?zone_id=";
function request_zone_id(zone_id){
    var req_url = zone_id_url + zone_id;
    request(
        {
            url : req_url,
            method : "get"
        },
        // req_url 로 request 를 전송하면 결과값을 아래 함수로 돌려 받는다
        function(error, answer, body){
            //var text = unescape(replaceAll(body, "\\", "%"))
            var obj = JSON.parse(body).result;
            // db에 연결하고 obj.result 에 있는 모든 값을 insert
            var query_str = "insert into crawl_car( zone_id,zone_name";
            query_str += ",zone_attr,zone_alias,zone_addr,zone_props";
            query_str += ",oper_time,oper_way,state,albe_num,total_num";
            query_str += ",lat,lng,visit,visit_link,link,notice,notice_oneway)";
            query_str += " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            var values = [obj.zone_id, obj.zone_name,obj.zone_attr
                ,obj.zone_alias,obj.zone_addr,obj.zone_props,obj.oper_time
                ,obj.oper_way,obj.state,obj.albe_num,obj.total_num
                ,obj.lat,obj.lng,obj.visit,obj.visit_link,obj.link
                ,obj.notice,obj.notice_oneway];
            con.query(query_str, values, function(error,result){
                if(error){
                    console.log(error);
                }else{
                    console.log("insertion completed! : "+ obj.zone_id);
                }
            });
        }
    )
}

function replaceAll(strTemp, strValue1, strValue2){ 
    while(1){
        if( strTemp.indexOf(strValue1) != -1 )
            strTemp = strTemp.replace(strValue1, strValue2);
        else
            break;
    }
    return strTemp;
}