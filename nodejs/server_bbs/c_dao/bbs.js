// ./dao/bbs.js
var m = require("mongodb").MongoClient;
var dbname = "bbsdb";
var dburl = "mongodb://localhost:27017/" + dbname;
var table = "bbs";
var page_count = 20;

/*
    bbs = {
        no : 12,
        title : "제목",
        content : "내용",
        date : "2017/10/23 12:43:23",
        user_id : "root"
    }

    search = {
        type : "all"    // all = 전체검색, no = 번호만 검색, title = 제목검색 ...
        no : "번호검색",
        title : "제목검색",
        content : "내용검색",
        date : "날짜검색",
        user_id : "아이디로 검색"
    }
*/

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
    conn(function(db){
        var query = {};
        switch(search.type){
            case "_id":     query = { _id : ObjectID(search._id) }; break;
            case "no":      query = { no : parseInt(search.no) };   break;
            case "title":   query = { title : search.title };       break;
            case "content": query = { content : search.content };   break;
            case "date":    query = { date : search.date };         break;
            case "user_id": query = { user_id : search.user_id };   break;
        }

        var start = 0;
        if(search.page) start = (parseInt(search.page) - 1) * page_count;

        // sort 1:오름차순 / -1:내림차순

        // 집합
        // skip  - 카운트를 시작할 index의 위치
        // limit - 가져올 갯수

        db.collection(table).find(query).sort({ no : -1 }).skip(start).limit(page_count).toArray(function(err, documents){
            if(!err) callback(documents);
        });

        db.close();
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