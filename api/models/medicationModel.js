const mongoose = require("mongoose");

const MedicationSchema = new mongoose.Schema({
  medicationId: { type: String, required: false },
  medicationName: { type: String, required: false },
  frequency: { type: String },
  dosage: { type: String, required: false },
  dosageForm: { type: String, required: false },
  duration: { type: String },
});

module.exports = mongoose.model("Medication", MedicationSchema);
