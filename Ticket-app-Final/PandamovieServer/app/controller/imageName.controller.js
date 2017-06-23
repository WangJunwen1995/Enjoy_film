var movie = require('../model/movie');
//根据newid来获取电影图片名字
exports.imageGetName = function(req, res) {
   var newid = req.body.newid;
   movie.find({newid: newid}, function(err, movies) {
    if (err) {
      console.log('ERROR IN FIND: ' + err);
      return res.json({err: 'Error happens while finding...'});
    } else if (movies.length === 0) {
      return res.json({err: 'newid is wrong...'});
    } else {
          return res.json({image: movies[0].image});
        }
    });
};