var app = require('./app');
var port = process.env.PORT || 8887;
app.listen(port, function(){
 console.log("Server running on port " + port);
});


