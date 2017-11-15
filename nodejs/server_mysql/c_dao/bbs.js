// ./dao/bbs.js
var dbname = "bbsdb";
var dburl = "mongodb://localhost:27017/" + dbname;
var table = "bbs";

var m = require("mysql");
var settings = {
    host : "localhost",
    id : "root",
    pw : "root",
    port : "3306",
    database : "memo"
};
var page_count = 20;


function conn(callback){
    m.connect(dburl, function(error, db){
        if(!error) callback(db);
    });
}

exports.Bbs = function(){
    this.no = -1;
    this.title = "";
    this.content = "";
    this.date = "";
    this.user_id = "";

    this.toQuery = function(){
        return {
            no : this.no,
            title : this.title,
            content : this.content,
            date : this.date,
            user_id : this.user_id
        };
    };
};
exports.Search = function(){
    this.type = "";
    this.no = -1;
    this.title = "";
    this.content = "";
    this.date = "";
    this.user_id = "";

    this.toQuery = function(){
        return {
            type : this.type,
            no : this.no,
            title : this.title,
            content : this.content,
            date : this.date,
            user_id : this.user_id
        };
    };
};

var executeQuery = function(query, callback){
    var conn = db.createConnection(settings);
    conn.connect();
    conn.query(query, function(error, recordSet, columns){
        callback(error, recordSet, columns);
        this.end();
    });
    conn.end();
};

exports.create = function(bbs, callback){
    console.log(bbs);
    conn(function(db){
        db.collection(table).insert(bbs, function(err, inserted){
            if(!err) callback(200);
            else callback(400);
        });
        db.close();
    });
};

exports.select = function(search, callback){
    var where = "";
    switch(search.type){
        case "no":      where = " where no=" + search.no;                       break;
        case "title":   where = " where title=\"" + search.title + "\"";        break;
        case "content": where = " where content=\"" + search.content + "\"";    break;
        case "date":    where = " where date=" + search.date;                   break;
        case "user_id": where = " where user_id=\"" + search.user_id + "\"";    break;
    }
    
    var start = 0;
    if(search.page) start = (parseInt(search.page) - 1) * page_count;
    var limit = " limit " + page_count + " , " + start;

    executeQuery("select * from memo" + where + limit, function(error, recordSet, columns){
        callback(error, recordSet, columns);
        // recordSet.forEach(function(record) {   
        // });
    });
};

exports.update = function(bbs){
    conn(function(db){
         // 1. 수정대상쿼리
         var query = { no:bbs.no };
         // 2. 데이터 수정명령 - 실제 변경될 컬럼이름과 값
         var operator = {
             title : bbs.title,
             content : bbs.content,
             date : bbs.date,
             user_id : bbs.user_id
          };
          delete operator._id;

         // 3. 수정옵션 - upsert true 일때 데이터가 없으면 insert
         var option = { upsert:true };
 
         db.collection(table).update(query, operator, option, function(err, upserted){
 
         });
 
         db.close();
    });
};

exports.delete = function(bbs){
    conn(function(db){
        // 1. 수정대상쿼리
        var query = { no:bbs.no };
            db.collection(table).remove(query, function(err, removed){
    
            });
    
            db.close();
    });
};