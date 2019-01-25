
var express = require("express");
var router = express.Router();
var Location = require('./Location');
var VerifyToken = require('../auth/VerifyToken');

var bodyParser = require('body-parser');

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

router.use(express.json());


// CREATES A NEW LOCATION
router.post('/createLocation', VerifyToken, function (req, res) {
  Location.create({
          tagID: req.body.tagID,
          name : req.body.name,
      }, 
      function (err, location) {
          if (err) return res.status(500).send("There was a problem adding the information to the database.");
          res.status(200).send(location);
      });
});

// RETURNS ALL THE LOCATIONS IN THE DATABASE
router.get('/locations', VerifyToken, function (req, res) {
  Location.find({}, function (err, locations) {
      if (err) return res.status(500).send("There was a problem finding the locations.");
      res.status(200).send(locations);
  });
});

// GETS A LOCATION FROM THE DATABASE
router.post('/getLocation', VerifyToken, function (req, res) {
  Location.findOne({ tagID: req.body.tagID}, function (err, location) {
      if (err) return res.status(500).send("There was a problem finding the location.");
      if (!location) return res.status(404).send("No location found.");
      res.status(200).send(location);
  });
});

// DELETES A LOCATION FROM THE DATABASE
router.post('/deleteLocation', VerifyToken, function (req, res) {
  Location.findOneAndDelete({tagID: req.body.tagID}, function (err, location) {
      if (err) return res.status(500).send("There was a problem deleting the location.");
      if (!location) return res.status(404).send("No location found.");
      res.status(200).send("Location was deleted.");
  });
});

// UPDATES A LOCATION IN THE DATABASE
router.put('/updateLocation', VerifyToken, function (req, res) {
  Location.findOneAndUpdate({tagID: req.body.tagID}, req.body, {new: true}, function (err, location) {
      if (err) return res.status(500).send("There was a problem updating the location.");
      if (!location) return res.status(404).send("No location found.");
      res.status(200).send(location);
  });
});
module.exports = router;


