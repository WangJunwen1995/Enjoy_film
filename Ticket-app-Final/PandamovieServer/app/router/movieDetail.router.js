module.exports = function(app) {
    var api = require('../controller/movieDetail.controller');
    app.post('/movie',api.getDetail);
}