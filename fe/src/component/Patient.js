import React from "react";
import { IntlProvider } from "react-intl";
import DrawerNavBar from "./DrawerNavBar";
import Card from "./Card";
import SymptomForm from "./SymptomForm";
import "./Patient.css";
import { LOCALES } from "../i18n/locales";
import { messages } from "../i18n/messages";
function Patient() {
  const locale = LOCALES.HINDI;
  const [language,setLanguage] = React.useState('en');
  return (
    <>
     <IntlProvider messages={messages[language]} locale={language}>
      <DrawerNavBar setLanguage={setLanguage}/>
      <br></br>
      <div className="card-div">
      
      <Card>
        <SymptomForm></SymptomForm>
      </Card>
      </div>
     </IntlProvider>
    </>
  );
}
export default Patient;
