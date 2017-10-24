/*
    Rest Api 설계
    ------------------------------
    /서비스명/값
    ------------------------------
    /fibonacci/100 = 숫자배열
    /anagram/aabca/abcaa = true/false
*/

// 1. 서버 모듈(라이브러리)을 import
var http = require("http");
var port = 8089;

// 2. 서버 모듈을 사용해서 서버를 정의
var server = http.createServer(function(request, response){
    response.writeHead(200, { 'Content-Type' : 'text/html' });

    // decodeURI(주소) -> %20 등의 주소문자를 원래 문자로 변환
    // encodeURI(문자) -> 주소로 사용할 수 있는 문자열로 변환
    
    // 1. 요청이 온 주소체계가 내가 제공하는 api 구조와 일치하는지 확인
    var cmd = decodeURI(request.url).split("/");

    if(cmd.length < 3){
        response.write("<h>Your request is wrong!!!</h>");
    }
    else{
        switch(cmd[1]){
            case "fibonacci":
                if(!isNaN(cmd[2])) response.write(getFibonacci(cmd[2]));
                else response.write("<h>Value is wrong!!</h>");
                break;
            case "anagram":
                if(cmd.length == 4) response.write(getAnagram(cmd[2], cmd[3]));
                else response.write("<h>Value is wrong!!</h>");
                break;
            default:
                response.write("<h>No service!!!</h>");
                break;
        }
    }

    response.end("");
});

// 3. 서버 실행
server.listen(port, function(){
    console.log(port + " server is running...");
});

function getFibonacci(cnt) {
    var str = "<h>";
    var before = 0, current = 0;
    for (var i = 0; i < cnt; i++) {
        if (i === 0) {
            str += i + "</br>";
            before = i;
        }
        else if (i === 1) {
            str += i + "</br>";
            current = i;
        }
        else {
            var temp = before + current;
            before = current;
            current = temp;
            str += current + "</br>";
        }
    }
    return str + "</h>";
}

function getAnagram(string1, string2){
    if(string1.length != string2.length) return "<h>false</h>";

    var str1 = string1.split("").sort();
    var str2 = string2.split("").sort();

    for(var i = 0; i < str1.length; i++){
        if(str1[i] != str2[i]) return "<h>false</h>";
    }

    return "<h>true</h>";
}

