const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

/*
    param json
    {
        token : [ "토큰값" ],
        payload : {
            data : [key: string],
            notification: {
                [key: string],
                tag: string,
                body: string,
                icon: string,
                badge: string,
                color: string,
                sound: string,
                title: string,
                bodyLocKey: string,
                bodyLocArgs: string,
                clickAction: string,
                titleLocKey: string,
                titleLocArgs: string
            }
        }
        options: {
            [key: string],
            dryRun: boolean,
            priority: string,
            timeToLive: number,
            collapseKey: string,
            mutableContent: boolean,
            contentAvailable: boolean,
            restrictedPackageName: string
        }
    }
*/
exports.sendNotification = functions.https.onRequest((req, res) => {
    // fcm 서버쪽으로 전송
    admin.messaging().sendToDevice(req.body.token, req.body.payload, req.body.options)
        .then(function(response) { res.status(200).send(response); })
        .catch(function(error) { res.status(501).send(error); });
});