const express = require("express");
const dotnev = require("dotenv");
const cors = require("cors");
const mongoose = require("mongoose");

dotnev.config({ path: "./config/config.env" });

mongoose.connect(
  process.env.DB_CONNECTION,
  {
    useUnifiedTopology: true,
    useNewUrlParser: true,
  },
  () => console.log("Connected to DB")
);

const app = express();

app.use(cors());
const PORT = process.env.PORT || 5000;
app.all("*", (req, res, next) => {
  res.header("Access-Control-Allow-Origin", "x");
  next();
});

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use("/disease", require("./routes/disease"));
app.use("/user/api", require("./routes/user"));
app.use("/imageToText/api", require("./routes/imageToText"));

app.listen(PORT, console.log(`Server running ${process.env.NODE_ENV} on port ${PORT}`));
app.get("/", (req, res) => {
  res.send("Hello World!");
});
