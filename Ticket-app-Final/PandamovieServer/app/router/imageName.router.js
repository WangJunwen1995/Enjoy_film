
module.exports = function(app) {
    var api = require('../controller/imageName.controller');
    app.post('/image',api.imageGetName);
}