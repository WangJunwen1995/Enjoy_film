
module.exports = function(app) {
    var api = require('../controller/logout.controller');
    app.post('/logout',api.logout);
}