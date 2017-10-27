var m = require("mongodb").MongoClient;
var dbname = "bbsdb";
var dburl = "mongodb://localhost:27017/" + dbname;
var table = "bbs";

var ids = [ "mnisdh","root","testUser","daeho","kkkk","bkcds","lllll","zzzzz","aaaa","erwer" ];
var texts = [ "node","java","android","test","study","school","caff","mongo","mysql","c#","markdown","hello" ];

//min포함 max제외
function getRandom(min, max) {
    return parseInt(Math.random() * (max - min)) + min;
}

function getId(){
    return ids[getRandom(0,ids.length)];
}
function getTitle(){
    var result = "";
    for(var i = 0; i < getRandom(1,6); i++){
        result += " " + texts[getRandom(0,texts.length)];
    }

    return result.substring(1);
}

var Bbs = function(){
    this.no = -1;
    this.title = getTitle();
    this.content = "내용입니다";
    this.date = new Date() + "";
    this.user_id = getId();
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

// insert를 10만개 처리
m.connect(dburl, function(error, db){
    var createCount = 100000;
    var insertCount = 500;

    var bbss = [];
    var cnt = 0;
    while(cnt <= createCount){
        var bbs = new Bbs();
        bbs.no = ++cnt;
        bbss[bbss.length] = bbs.toQuery();

        if(bbss.length === insertCount || cnt == createCount){
            db.collection(table).insertMany(bbss, function(err){
                if(err) console.log(err.message);
            });
            bbss = [];
        }
    }

    db.close();
});
