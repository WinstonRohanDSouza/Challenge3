import React from "react";
import DrawerNavBar from "./DrawerNavBar";
import Card from "./Card";
import DiagnosisForm from "./DiagnosisForm";
import "./Patient.css";
function Doctor() {
  return (
    <>
      {" "}
      <DrawerNavBar />
      <br></br>
      <div className="card-div">
      <Card>
        <DiagnosisForm></DiagnosisForm>
      </Card>
      </div>
      
    </>
  );
}
export default Doctor;
