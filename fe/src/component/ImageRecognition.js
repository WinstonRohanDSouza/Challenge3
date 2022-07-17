import React from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import PersistentDrawerRight from "./DrawerNavBar";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import FormControl from "@mui/material/FormControl";
import SearchRoundedIcon from "@mui/icons-material/SearchRounded";
import { ApiHelper } from "../helpers/fetchHelper";
import TableData from "./TableData";
import Progress from "./Progress";

function ImageRecognition() {
  const [show, setShow] = React.useState(false);
  const [medicationsDetails, setMedicationsDetails] = React.useState([]);
  const [isLoading, setLoader] = React.useState(false);

  const container = React.useRef(null);
  const [values, setValues] = React.useState("");
  const handleClick = () => {
    setShow(!show);
  };
  const getData = (e) => {
    setValues(e.target.value);
  };

  const fetchImageToTextValues = (e) => {
    setLoader(true);
    let medications;
    ApiHelper(`http://localhost:5000/imageToText/api`, { imageUrl: `${values}` }, "POST").then((response) => {
      medications = response.medications ? JSON.parse(response.medications) : [];
      setLoader(false);
      setShow(true);
      setMedicationsDetails(medications);
    });
  };

  return (
    <div>
      hh
      <PersistentDrawerRight />
      <div>
        <div id="image-search" className="toggle-login-register-btns">
          <Stack spacing={1} direction="row" className="div-img-url-box">
            {/* <InputLabel htmlFor="outlined-adornment-img-url">ImageUrl</InputLabel> */}
            {/* <OutlinedInput id="outlined-adornment-img-url" value={values} onChange={handleChange()} startAdornment={<InputAdornment position="start"></InputAdornment>} label="imageUrl" /> */}
            <TextField
              value={values}
              // disabled
              InputProps={{ style: { fontSize: 15 } }}
              multiline={true}
              fullWidth
              style={{ marginLeft: 200 }}
              label="imageUrl"
              color="primary"
              onChange={getData}
              // defaultValue="https://tesseract.projectnaptha.com/img/eng_bw.png"
            />
            <Button size="medium" onClick={fetchImageToTextValues} variant="contained" endIcon={<SearchRoundedIcon />}>
              Analyze for Medications
            </Button>
          </Stack>
        </div>
        <>{isLoading ? <Progress isLoading={isLoading} /> : <TableData show={show} medications={medicationsDetails} />}</>
      </div>
    </div>
    // <></>
  );
}

export default ImageRecognition;

// Portal
// Data grid
// fullWidth