var mongoose = require('../../config/mongoose');

// 电影模型
var userSchema = mongoose.Schema({
  moviename: {type: String, required: true, unique: true},
  introduction: {type: String, required: true},
  image: {type: String, required: true},
  newid: {type: String, required: true}
});

module.exports = mongoose.model('movie', userSchema);