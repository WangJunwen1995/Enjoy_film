
module.exports = function(app) {
    var api = require('../controller/signup.controller');
    app.post('/signup',api.signup);
}   