const mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
  firstName: {
    type: String,
    required: true,
  },
  lastName: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    required: true,
  },
  diagnosis: { type: JSON },
  symptoms:{ type: JSON},
  password: {
    type: String,
    required: true,
  },
  confirmPassword: {
    type: String,
    required: false
  },
  date: {
    type: Date,
    default: Date.now,
  },
});

module.exports = mongoose.model("User", userSchema);
