const router = require("express").Router();
const User = require("../models/userModel");
const bcrypt = require("bcryptjs");
const Joi = require("@hapi/joi");
const jwt = require("jsonwebtoken");
const _ = require('lodash');

  router.post("/register", async (req, res) => {
  
    //Check if the user is allready in the db
    try{
      console.log(req.body);

    const emailExists = await User.findOne({ email: req.body.email });
  
    if (emailExists) return res.status(400).send("Email allready exists");
  
    console.log(req.body.password);
    const hashPassword = req.body.password;
  
    //create new user
    const user = new User({
      firstName: req.body.firstName,
      lastName: req.body.lastName,
      email:req.body.email,
      password: hashPassword
    });
  
      const savedUser = await user.save();
      res.status(200).send({res:"success"});
  }catch (err) {
    console.log(err);
      res.status(400).send({res:err});
    }
  });
router.post("/login", async (req, res) => {
  
    const user = await User.findOne({ email: req.body.email });
    if (!user) return res.status(400).send("Email or password is wrong");
  
    const validPass = req.body.password === user.password;
    if (!validPass) return res.status(400).send("Email or password is wrong");
    res.status(200).send({code:200,result:user});
  });
module.exports = router;