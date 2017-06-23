var cinema = require('../model/cinema');
//根据日期，时间，电影院名字，电影名字，查询电影院信息，如果票余量大于0，则更新票余量-1
exports.updateTicket = function(req, res) {
  var date = req.body.date;
  var cinemaname = req.body.cinemaname;
  var time = req.body.time;
  var moviename = req.body.moviename;
  console.log(moviename + " " + date);
 cinema.find({cinemaname:cinemaname, date: date, moviename: moviename,time: time}, function(err, cinemas) {
    if (err) {
      console.log('ERROR IN CINEMA: ' + err);
      return res.json({err: 'Error happens while getting cinemas..'});
    } else {
        var ticket = parseInt(cinemas[0].ticketmargin) -1;
        console.log(ticket);
        if (ticket >= 0) {
      cinema.update({cinemaname:cinemaname,date: date, moviename: moviename,time: time},{$set:{ticketmargin:ticket}},function(err){
           if (err) {
            console.log('ERROR IN STEPS: ' + err);
            return res.json({err: 'Error happens while updating tickets..'});
          } else {
            return res.json(true);
          }
      });
        } else {
        return res.json({err: 'There is no ticket...'});

        }
    }
  });
};