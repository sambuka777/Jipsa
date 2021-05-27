const express = require('express');
const app = express();
const indexRouter = require('./routes/index');
const userRouter = require('./routes/user');
const pwdmailRouter = require('./routes/pwdmail');
app.use(express.json());
const port = 3000;

app.use('/', indexRouter);
app.use('/mail', userRouter);
app.use('/pwdmail', pwdmailRouter);

app.listen(port, function () {
    console.log('Example app listening on port : ' + port);
});


// var admin = require("firebase-admin");

// var serviceAccount = require("./jipsa-e1d62-firebase-adminsdk-2d75s-6e9432e9aa.json");

// admin.initializeApp({
//     credential: admin.credential.cert(serviceAccount),
//     // databaseURL: "https://jipsa-e1d62-default-rtdb.firebaseio.com"
// });

// const db = admin.firestore();
// // 데이터 가져오는거 get()
// async function showdata(db) {
//     const snapshot = await db.collection('members').get();
//     snapshot.forEach((doc) => {
//         console.log(doc.id, '=>', doc.data());
//     });
// }

// showdata(db);
//데이터 저장 set()
// async function database(db) {
//     const aTuringRef = db.collection('members').doc("2");

//     await aTuringRef.set({
//         'id': 'kwon',
//         'pwd': 'kwon1234',
//         'name': '권대웅'
//     });
// }


// var refreshToken; // Get refresh token from OAuth2 flow

// admin.initializeApp({
//     credential: admin.credential.refreshToken(refreshToken),
//     databaseURL: 'https://jipsa-e1d62-default-rtdb.firebaseio.com'
// });

