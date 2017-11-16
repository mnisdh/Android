var express = require("express");
var app = express();

var gallery = require("./routes/gallery");
var user = require("./routes/user");

// url요청에 따라 모듈을 분기해준다
app.use("/gallery", gallery);
app.use("/user", user);
// public 디렉토리 아래에 정적(image, video.. etc) 파일을 읽어서 넘겨준다
app.use("/public", express.static("public"));





app.listen(8080, function(){ console.log("Server is running..."); });