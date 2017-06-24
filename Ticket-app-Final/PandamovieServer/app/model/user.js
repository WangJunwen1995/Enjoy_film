var mongoose = require('../../config/mongoose');

// 用户模型
var userSchema = mongoose.Schema({
  username: {type: String, required: true, unique: true},
  password: {type: String, required: true},
  image: {type: String, required: true}
});

module.exports = mongoose.model('user', userSchema);