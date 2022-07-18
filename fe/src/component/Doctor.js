import React from "react";
import DrawerNavBar from "./DrawerNavBar";
import Card from "./Card";
import DiagnosisForm from "./DiagnosisForm";
import { IntlProvider } from "react-intl";
import "./Patient.css";
function Doctor() {
  return (
    <>
      {" "}
      <IntlProvider locale="en" defaultLocale="en">
      <DrawerNavBar />
      <br></br>
      <div className="card-div">
      <Card>
      
        <DiagnosisForm></DiagnosisForm>
   
      </Card>
     
      </div>
      </IntlProvider>
      
    </>
  );
}
export default Doctor;
