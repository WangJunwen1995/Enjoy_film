var mongoose = require('../../config/mongoose');

// 电影院模型
var userSchema = mongoose.Schema({
  cinemaname: {type: String, required: true},
  moviename: {type: String, required: true},
  date: {type: String, required: true},
  time: {type: String, required: true},
  price: {type: String, required: true},
  ticketmargin: {type: String, required: true}
});

module.exports = mongoose.model('cinema', userSchema);