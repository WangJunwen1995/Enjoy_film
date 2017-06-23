var userModel = require('../model/user');
var path = require('path');
var config = require('../../config/config');
//根据用户名查询数据库，如果不存在则将用户名和密码保存
exports.signup = function(req, res) {
  var username = req.body.username;
  var password = req.body.password;

  userModel.find({username: username}, function(err, users) {
    if (err) {
      console.log('ERROR IN FIND: ' + err);
      return res.json({err: 'Error happens while finding...'});
    } else if (users.length) {
      return res.json({err: 'Username is used by others...'});
    } else {
      var image = 'dog.jpg';
      var u = new userModel({username: username, password: password, image: image});
      u.save(function(err) {
        if (err) {
          console.log('ERROR IN SAVE: ' + err);
          return res.json({err: 'Error happens while saving user...'});
        }
      });
      return res.json(true);
    }
  });
};
