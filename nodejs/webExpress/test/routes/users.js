var express = require('express');
var router = express.Router();

// 요청주소가 localhost:3000/users 일때
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

// 요청주소가 localhost:3000/users 일때
router.get('/daeho', function(req, res, next) {
  res.send('respond from daeho');
});

module.exports = router;
