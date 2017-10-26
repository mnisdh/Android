// 외부파일 사용하기
var db = require("./db");

// 글 추가하기
// var row = new db.Bbs();
// row.no = 3;
// row.title = "test title";
// row.content = "content test";
// row.date = "2017/10/23 11:12:33";
// row.user_id = "mnisdh";
// db.create(row, function(result){ console.log(result); });

// 글 조회하기
// var search = new db.Search();
// search.type = "no";
// search.no = 2;
// db.select(search, function(dataset){
//     dataset.forEach(
//         function(item){ console.log(item); }
//     );
// });

// 글 수정하기
var search = new db.Search();
search.type = "no";
search.no = 2;
db.select(search, function(dataset){
    if(dataset.length > 0){
        var row = dataset[0];
        var json = JSON.stringify(row);
        
        // networking....

        // 안드로이드
        var modified = JSON.parse(json);
        modified.title = "수정함";
        var mod_json = JSON.stringify(modified);

        // 서버로 수정요청
        var completed = JSON.parse(mod_json);
        db.update(completed);
    }
});