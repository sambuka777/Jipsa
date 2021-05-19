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
showdata(db)
// let users = [
//     {
//         id: 1,
//         name: 'alice'
//     },
//     {
//         id: 2,
//         name: 'bek'
//     },
//     {
//         id: 3,
//         name: 'chris'
//     }
// ]

// router.get('/users', (req, res) => {
//     console.log('who get in here/users');
//     res.json(users)
// });

router.post('/post', (req, res) => {
    console.log('who get in here post /users');

    var inputData = req.body;

    console.log(inputData);
    console.log("user_id : " + inputData.id + " ,  name:" + inputData.name);
    database_init(db, inputData.id, inputData.pwd, inputData.name, inputData.birth, inputData.email);

    res.write("OK!");
    res.end();
    // console.log('who get in here post /users');
    // var inputData;
    // req.on('data', (data) => {
    //     console.log(data)
    //     inputData = JSON.parse(data);
    // });


    // req.on('end', () => {
    //     console.log(inputData);
    //     console.log("user_id : " + inputData.id + " ,  name:" + inputData.name);
    //     database_init(db, inputData.id, inputData.pwd, inputData.name);

    // });

    // res.write("OK!");
    // res.end();
});
router.post('/idck', (req, res) => {
    var inputData = req.body;
    console.log(inputData);

    console.log("user_id : " + inputData.id);

    var flag = database_idck(db, inputData.id);

    if (!flag) {
        console.log("아니 돼야 한다고 왜 안되는데");

        res.write("OK!");
    } else {
        res.write("No!");
        console.log("아니 안돼야 한다고 왜 안되는데");
    }
    res.end();
});
router.post('/idlogin', (req, res) => {
    var inputData = req.body;
    console.log(inputData);

    console.log("user_id : " + inputData.id);

    var flag = database_idlogin(db, inputData.id);

    if (flag) {
        console.log("아니 돼야 한다고 왜 안되는데");

        res.write(pwdck + "");
    } else {
        res.write("No!");

    }
    res.end();
});
async function database_init(db, id, pwd, name, birth, email) {
    const aTuringRef = db.collection('members').doc(id);

    await aTuringRef.set({
        'pwd': pwd,
        'name': name,
        'birth': birth,
        'email': email
    });
}
async function database_idck(db, idck) {
    const docRef = db.collection('members').doc(idck + "");
    const doc = await docRef.get();
    if (!doc.exists) {
        console.log('No matching documents.');
        return false;
    } else {
        console.log(doc.data());
        return true;
    }
}
async function database_idlogin(db, idck) {
    const docRef = db.collection('members').doc(idck + "");
    const doc = await docRef.get();
    if (!doc.exists) {
        console.log('No matching documents.');
        return null;
    } else {
        setpwd(doc.data().pwd);
        console.log(doc.data().pwd);
        pwdck = doc.data().pwd;

        return pwdck;
    }
    // const memberRef = db.collection('members').doc(idck+"");
    // const snapshot = await memberRef.where('id', '==', idck).get();
    // if (snapshot.empty) {
    //     console.log('No matching documents.');
    //     return false;
    // } else {
    //     snapshot.forEach(doc => {
    //         console.log(doc.id, '=>', doc.data().pwd);
    //         pwdck = doc.data().pwd;
    //         console.log(pwdck)
    //         setpwd(pwdck);
    //     });
    //     return true;
    // }
}
function setpwd(pwd) {
    this.pwdck = pwd;
}
module.exports = router;