var express = require('express');
var router = express.Router();

const { smtpTransport } = require('../config/email');


/* min ~ max까지 랜덤으로 숫자를 생성하는 함수 */
var generateRandom = function (min, max) {
    var ranNum = Math.floor(Math.random() * (max - min + 1)) + min;
    return ranNum;
}

router.post('/', async (req, res) => {
    const number = Math.random().toString(36).slice(2);
    console.log(number);
    const sendEmail = req.body.email;

    const mailOptions = {
        from: "집사<hhs844@naver.com>", // 무조건 보내는 메일주소 들어가야한다
        to: sendEmail,
        subject: "[집사]임시 비밀번호 이메일 입니다",
        text: "임시 비밀번호를 전솧합니다 로그인 후 꼭 변경해주시기 바랍니다. : " + number
    };

    smtpTransport.sendMail(mailOptions, (error, response) => {
        if (error) {
            return res.status(500).send({ statusCode: 'FAIL', responseMessage: '인증번호 전송 실패' });
        } else {
            /* 클라이언트에게 인증 번호를 보내서 사용자가 맞게 입력하는지 확인! */
            return res.status(200).send({ statusCode: 'SUCCESS', responseMsg: "인증번호 전송 성공", number: number });
        }
        smtpTransport.close();
    });
})


module.exports = router;