var db = require("mysql");
var settings = {
    host : "localhost",
    id : "root",
    pw : "root",
    port : "3306",
    database : "memo"
};

var conn = db.createConnection(settings);
conn.connect();
conn.query("select * from memo", function(error, recordSet, columns){
    recordSet.forEach(function(record) {
        console.log(record);
    });
    this.end();
});

conn.end();
