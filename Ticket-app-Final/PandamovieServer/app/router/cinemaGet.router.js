
module.exports = function(app) {
    var api = require('../controller/cinemaGet.controller');
    app.post('/cinema',api.findcinema);
}