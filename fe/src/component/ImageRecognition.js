import React from "react";
import TextField from "@mui/material/TextField";
import PersistentDrawerRight from "./DrawerNavBar";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import { IntlProvider } from "react-intl";
import SearchRoundedIcon from "@mui/icons-material/SearchRounded";
import { ApiHelper } from "../helpers/fetchHelper";
import TableData from "./TableData";
import Progress from "./Progress";


function ImageRecognition() {
  const [show, setShow] = React.useState(false);
  const [medicationsDetails, setMedicationsDetails] = React.useState([]);
  const [isLoading, setLoader] = React.useState(false);

  const [values, setValues] = React.useState("");
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
      <IntlProvider locale="en" defaultLocale="en">
      <PersistentDrawerRight />
      <div>
        <div id="image-search" className="toggle-login-register-btns">
          <Stack spacing={1} direction="row" className="div-img-url-box">
            <TextField
              value={values}
              InputProps={{ style: { fontSize: 15 } }}
              multiline={true}
              fullWidth
              style={{ marginLeft: 200 }}
              label="Source"
              color="primary"
              onChange={getData}
            />
            <Button size="medium" onClick={fetchImageToTextValues} variant="contained" endIcon={<SearchRoundedIcon />}>
              Analyze for Medications
            </Button>
          </Stack>
        </div>
        <>{isLoading ? <Progress isLoading={isLoading} /> : <TableData show={show} medications={medicationsDetails} />}</>
      </div>
      </IntlProvider>
    </div>
  );
}

export default ImageRecognition;
