
module.exports = function(app) {
    var api = require('../controller/imageGet.controller');
    app.get('/image/:image',api.imageGet);
}