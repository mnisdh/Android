// ./dao/bbs.js
var items = require("./items");

var m = require("mongodb").MongoClient;
var dbname = "bbsdb";
var dburl = "mongodb://localhost:27017/" + dbname;
var table = "bbs";
var page_count = 20;

var code_error = 200;
var code_success = 400;

function conn(callback){
    m.connect(dburl, function(error, db){
        if(!error) callback(db);
    });
}

/*
    callback(int result_code)
*/
exports.create = function(bbs, callback){
    conn(function(db){
        if(!db) { callback(code_error); return; }

        db.collection(table).insert(bbs, function(error, inserted){
            if(error) callback(code_error);
            else callback(code_success);
        });
        db.close();
    });
};

/*
    sort 1:오름차순 / -1:내림차순

    집합
    skip  - 카운트를 시작할 index의 위치
    limit - 가져올 갯수

    callback(documents)
*/
exports.select = function(search, callback){
    conn(function(db){
        if(!db) { callback(null); return; }

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
        
        db.collection(table).find(query)
                            .sort({ no : -1 })
                            .skip(start)
                            .limit(page_count)
                            .toArray(function(err, documents){ if(!err) callback(documents); });

        db.close();
    });
};

/*
    callback(result_code)
*/
exports.update = function(bbs){
    conn(function(db){
        if(!db) { callback(code_error); return; }

        // 1. 수정대상쿼리
        var query = { no:bbs.no };
        // 2. 데이터 수정명령 - 실제 변경될 컬럼이름과 값
        var operator = {
            title : bbs.title,
            content : bbs.content,
            date : bbs.date,
            user_id : bbs.user_id
        };
        // property 삭제
        delete operator._id;
        // 3. 수정옵션 - upsert true 일때 데이터가 없으면 insert
        var option = { upsert:true };

        db.collection(table).update(query, operator, option, function(error, upserted){
            if(error) callback(code_error);
            else callback(code_success);
        });

        db.close();
    });
};

/*
    callback(result_code)
*/
exports.delete = function(bbs){
    conn(function(db){
        if(!db) { callback(code_error); return; }

        // 1. 수정대상쿼리
        var query = { no:bbs.no };

        db.collection(table).remove(query, function(err, removed){
            if(error) callback(code_error);
            else callback(code_success);
        });

        db.close();
    });
};