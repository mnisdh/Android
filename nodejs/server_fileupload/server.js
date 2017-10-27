var h = require("http");
var u = require("url");

// 파일 업로드하기
var formidable = require("formidable");
var fs = require("fs");

var s = h.createServer(function(request, response){
    var url = u.parse(request.url);
    if(url.pathname === "/upload"){
        var form = new formidable.IncomingForm();
        form.multiples = true;
        form.parse(request, function(err, names, files){ // 임시폴더에 저장
            for(i in files){
                var oldPath = files[i].path;
                var realPath = "/Users/daeho/Documents/android/nodejs/server_fileupload/upload/" + files[i].name;
                
                renameFile(oldPath, realPath, 0);
                //fs.renameSync(oldPath, realPath);
            }

            response.end("upload completed!");
        });
    }
    else{
        response.end("404 not found");
    }
});

s.listen(8090, function(){ console.log("Server is running..."); });

function renameFile(oldFile, newFile, index){
    var checkName = newFile;
    if(index > 0) {
        var split = checkName.split(".");
        checkName = split[0] + "_" + index + "." + split[1];
    }

    if(fs.existsSync(checkName)) renameFile(oldFile, newFile, ++index);
    else fs.renameSync(oldFile, checkName);
}