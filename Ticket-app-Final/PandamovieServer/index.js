var express = require('express');
var morgan = require('morgan');
var bodyParser = require('body-parser');
const config = require('./config/config');
var userModel = require('./app/model/user');
var cookeiParser = require('cookie-parser');
var movie = require('./app/model/movie');
var cinema = require('./app/model/cinema');
var init = require('./util/init');
const port = config.PORT || 3000;
const app = express();
//中间件
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(morgan('dev'));
app.use(cookeiParser());

//获取路由器
require('./app/router/cinemaGet.router')(app);
require('./app/router/imageGet.router')(app);
require('./app/router/imageName.router')(app);
require('./app/router/login.router')(app);
require('./app/router/logout.router')(app);
require('./app/router/signup.router')(app);
require('./app/router/movieDetail.router')(app);  
require('./app/router/ticketUpdate.router')(app);

app.listen(port, function() {
  console.log('Android server is listening on port ' + port);
});
