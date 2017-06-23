module.exports = function(app) {
    var api = require('../controller/login.controller');
    app.post('/login',api.login);
}