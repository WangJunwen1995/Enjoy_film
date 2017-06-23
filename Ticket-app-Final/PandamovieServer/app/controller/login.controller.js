var userModel = require('../model/user');
var config = require('../../config/config');
//根据用户名和密码来查询数据库，如果存在则返回true
exports.login = function(req, res) {
  var username = req.body.username;
  var password = req.body.password;
  console.log(username);
  userModel.find({username: username, password: password}, function(err, users) {
    if (err) {
      console.log('ERROR IN FIND: ' + err);
      return res.json({err: 'Error happens while finding...'});
    } else if (users.length === 0) {
      return res.json({err: 'Username or password is wrong...'});
    } else {
          return res.json(true);
        }
      });
  };