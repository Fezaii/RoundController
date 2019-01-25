var express = require('express');
var app = express();
var db = require('./db');

var UserController = require('./user/UserController');
app.use('/api/v1/user/', UserController);
var AuthController = require('./auth/AuthController');
app.use('/api/v1/', AuthController);
var LocationController = require('./location/LocationController');
app.use('/api/v1/location/', LocationController);
module.exports = app;