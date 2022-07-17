const express = require("express");
const router = express.Router();
var tesseract = require("tesseract.js");

router.post("/", async (req, res) => {
  const { imageUrl } = req.body;
  tesseract
    .recognize(imageUrl, "eng", { logger: (m) => console.log(m) })
    .then(({ data: { text } }) => {
      const medicationsData = [
        {
          id: 1,
          medicationName: "Paracetamol",
          source: "HRP",
          dosage: "100mg",
          dosageForm: "tablet",
          frequency: "twice a day",
        },
        {
          id: 2,
          medicationName: "electrolyte replacement solutions, oral",
          source: "File",
          dosage: "100mg",
          dosageForm: "powder",
          frequency: "once a day",
        },
        {
          id: 3,
          medicationName: "Paracetamol",
          source: "Millennium",
          dosage: "100mg",
          dosageForm: "tablet",
          frequency: "once a day",
        },
        {
          id: 4,
          medicationName: "brompheniramine/DM/phenylephrine/guaiFENesin",
          source: "Millennium",
          dosage: "50mg",
          dosageForm: "tablet",
          frequency: "twice a day",
        },
        {
          id: 5,
          medicationName: "magnesium citrate",
          source: "fileSystem",
          dosage: "100mg",
          dosageForm: "powder",
          frequency: "once in a month",
        },
        {
          id: 6,
          medicationName: "magnesium citrate",
          source: "Millennium",
          dosage: "100mg",
          dosageForm: "tablet",
          frequency: "twice a day",
        },
        {
          id: 7,
          medicationName: "sacrosidase",
          source: "fileSystem",
          dosage: "100mg",
          dosageForm: "tablet",
          frequency: "twice a day",
        },
      ];

      res.send({ medications: JSON.stringify(medicationsData) });
    })
    .catch((err) => {
      res.send({ error: err });
    });
});
module.exports = router;
