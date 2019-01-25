var mongoose = require('mongoose');  
var LocationSchema = new mongoose.Schema({ 
  tagID: String,   
  name: String
});
mongoose.model('Location', LocationSchema);

module.exports = mongoose.model('Location');