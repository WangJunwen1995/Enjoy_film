const mongoose = require('mongoose');
const config = require('./config');

// 使用es6的Promise代替Mongoose的Promise
mongoose.Promise = global.Promise;
mongoose.connect(config.MONGO_URL);

const db = mongoose.connection;

db.on('error', () => {
  console.log('Something wrong when connectting to mongodb!');
});

db.on('open', () => {
  console.log('Connectted to mongodb successfully!');
});

module.exports = mongoose;
