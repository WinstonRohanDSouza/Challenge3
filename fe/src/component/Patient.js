import React from "react";
import DrawerNavBar from "./DrawerNavBar";
import Card from "./Card";
import SymptomForm from "./SymptomForm";
import "./Patient.css";
function Patient() {
  return (
    <>
      {" "}
      <DrawerNavBar />
      <br></br>
      <div className="card-div">
      <Card>
        <SymptomForm></SymptomForm>
      </Card>
      </div>
      
    </>
  );
}
export default Patient;
