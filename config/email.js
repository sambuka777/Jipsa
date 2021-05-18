const nodemailer = require('nodemailer');

const smtpTransport = nodemailer.createTransport({
    service: "naver",
    auth: {
        user: "hhs844@naver.com",
        pass: "hhs183729"
    },
    tls: {
        rejectUnauthorized: false
    }
});

module.exports = {
    smtpTransport
}