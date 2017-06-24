var cinema = require('../model/cinema');
//根据日期和电影名字来查询电影院
exports.findcinema = function(req, res) {
  var date = req.body.date;
  var moviename = req.body.moviename;
  console.log(date + " " + moviename);
 cinema.find({moviename: moviename,date:date}, function(err, docs) {
    if (err) {
      console.log('ERROR IN CINEMA: ' + err);
      return res.json({err: 'Error happens while getting cinemas..'});
    } else {
      var cinemas = [];
      for (index in docs) {
        var cinema = {};
        cinema['cinemaname'] = docs[index].cinemaname;
        cinema['time'] = docs[index].time;
        cinema['price'] = docs[index].price;
        cinema['ticketmargin'] = docs[index].ticketmargin;
        console.log(docs[index].time);
        cinemas.push(cinema);
      }
      return res.json(cinemas);
    }
  });
};