var userModel = require('../app/model/user');
var usersUtil = require('./userUtil');

// 初始化五个虚拟用户，便于测试
userModel.count({}, function(err, count) {
  if (err) {
    return console.log('ERROR: ' + err);
  }
  if (count === 0) {
    var userArray = usersUtil.usersUtil();
    var image = 'dog.jpg';
    for (user in userArray) {
      var user = new userModel({username: userArray[user], password: 123456, image:image});
      user.save(function(err) {
        if (err) {
          return console.log('ERROR: ' + err);
        }
      });
    }
    return console.log('Init successfully!');
  }
});
