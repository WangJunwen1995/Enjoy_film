
module.exports = function(app) {
    var api = require('../controller/ticketUpdate.controller');
    app.post('/ticket',api.updateTicket);
}   