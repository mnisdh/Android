var mysql = require("mysql");
var connInfo = {
    host : '127.0.0.1',
    user: 'root',
    password : 'root',
    port : '3306',
    database : 'socar'
};

var connection = mysql.createPool(connInfo);

module.exports = connection;