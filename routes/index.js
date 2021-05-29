var express = require('express');
var router = express.Router();

var admin = require("firebase-admin");
var serviceAccount = require("../jipsa-e1d62-firebase-adminsdk-2d75s-6e9432e9aa.json");
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    //databaseURL: "https://jipsa-e1d62-default-rtdb.firebaseio.com"
});
const db = admin.firestore();
var pwdck = "";

/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('app', { title: 'Express' });
});

// 데이터 가져오는거 get()
async function showdata(db) {
    const snapshot = await db.collection('members').get();
    snapshot.forEach((doc) => {
        console.log(doc.id, '=>', doc.data());
    });
}
showdata(db);

router.post('/post', async (req, res) => {

    console.log("user_id : " + req.body.id + " ,  name:" + req.body.name);

    const aTuringRef = db.collection('members').doc(req.body.id);

    await aTuringRef.set({
        'pwd': req.body.pwd,
        'name': req.body.name,
        'birth': req.body.birth,
        'email': req.body.email
    });

    res.write("OK!");
    res.end();
});


router.post('/idck', async (req, res) => {

    console.log("user_id : " + req.body.id);

    const docRef = db.collection('members').doc(req.body.id + "");
    const doc = await docRef.get();

    if (!doc.exists) {
        console.log('No matching documents.');
        res.write("No!");
    } else {
        console.log(doc.data());
        res.write("OK!");
    }

    res.end();
});

router.post('/idlogin', async (req, res) => {

    console.log("user_id : " + req.body.id);

    const docRef = db.collection('members').doc(req.body.id + "");
    const doc = await docRef.get();

    if (!doc.exists) {
        console.log('No matching documents.');

        res.write("No!");
    } else {
        this.pwdck = doc.data().pwd;

        console.log(doc.data().pwd);
        pwdck = doc.data().pwd;

        res.write(pwdck + "");
    }
    res.end();
});
module.exports = router;