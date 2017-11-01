const functions = require('firebase-functions');
//const httpUrlConnection = require("request");
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.addMessage = functions.https.onRequest((req, res) => {
    // http 요청에서 ? 다음에 있는 변수중에 text 변수 값을 가져옴
    var msg = req.query.text;
    // 파이어베이스 db의 message 레퍼런스에 그 값을 넣는다
    admin.database().ref('/message')
                    .push({msg : msg})
                    .then(snapshot => {
                        res.end("success!!");
                  });
});


// fcm 설정
const fcmServerUrl = "https://fcm.googleapis.com/fcm/send";
const serverKey = "AAAAo8NvlQw:APA91bFE951cDu3uW_wW2e32jHkw_G_S6SOVa5Dpn9ZaVKkpyrolHFHjtzkUg0NhRbuNrSBEMR22bvGAQgO2YsaW3S8xihTew2yi370EzVpzCVIgCnKSExlgY1bADhbn26OoLCOnNhB2";


exports.sendNotification = functions.https.onRequest((req, res) => {
    
    // 전송할 메시지 객체를 완성
    var msg = {
        notification : {
            title : "노티바에 나타나는 타이틀",
            body : req.body.msg
        }
    };

    var tokens = [];
    tokens.push(req.body.to);

    // fcm 서버쪽으로 전송
    admin.messaging().sendToDevice(tokens, msg)
        .then(function(response) {
            res.status(200).send(response);
        })
        .catch(function(error) {
            res.status(501).send(error);
        });
});