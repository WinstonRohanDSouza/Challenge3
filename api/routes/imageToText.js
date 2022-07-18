const express = require("express");
const router = express.Router();
var tesseract = require("tesseract.js");
const fs = require("fs");
const path = require("path");
const Medications = require("../models/medicationModel");
const _ = require("lodash");

router.post("/", async (req, res) => {
  const { imageUrl } = req.body;

  tesseract
    .recognize(imageUrl, "eng", { logger: (m) => console.log(m) })
    .then(async ({ data: { text } }) => {
      const newText = text.replace(/[^A-Z0-9]/gi, "").toLowerCase();

      const dataPath = path.join(__dirname, "medications.json");
      const fsMedData = fs.readFileSync(dataPath, "utf8");
      const parsedData = JSON.parse(fsMedData).medicationsData;

      const fsMedDetails = _.keyBy(parsedData, "medicationName");
      const fsMedKeyListToBeChecked = _.keys(fsMedDetails);

      const medDataFromImg = _.filter(fsMedKeyListToBeChecked, (medItem) => {
        return newText.includes(medItem.toLowerCase());
      });
      const getMedDataFromFs = _.map(medDataFromImg, (medItem) => {
        return { ...fsMedDetails[medItem], source: "File" };
      });

      const dbRecord = await Medications.find({ medicationName: { $in: fsMedKeyListToBeChecked } }).lean();
      const dbMedDetails = _.keyBy(dbRecord, "medicationName");
      const dbMedKeyListToBeChecked = _.keys(dbMedDetails);
      const medDataFromDB = _.filter(dbMedKeyListToBeChecked, (medItem) => {
        return newText.includes(medItem.toLowerCase());
      });
      const getMedDataFromDB = _.map(medDataFromDB, (medItem) => {
        return { ...dbMedDetails[medItem], source: "HPR" };
      });

      const finalMedRes = [...getMedDataFromFs, ...getMedDataFromDB];
      const medRes = _.map(finalMedRes, (item, index) => {
        return { ...item, id: index };
      });
      res.send({ medications: JSON.stringify(medRes) });
    })
    .catch((err) => {
      console.log(err);
      res.send({ error: err });
    });
});
module.exports = router;
