import React from 'react';
import {BrowserRouter as BRouter,Routes,Route} from 'react-router-dom';
import Login from './component/Router';
import Patient from './component/Patient';
import Doctor from './component/Doctor';
import ImageRecognition from './component/ImageRecognition';
function App() {
  return (
    <BRouter>
    <Routes>
    <Route path="/diagnosis" element={<Doctor></Doctor>}></Route>
    <Route path="/analyseDocs" element={<ImageRecognition></ImageRecognition>}></Route>
    <Route path="/symptomsPatient" element={<Patient></Patient>}></Route>
    <Route path="/diagnosis" element={<Patient></Patient>}></Route>
    <Route path="/" exact element={<Login></Login>}></Route>
     </Routes>
    </BRouter>
  );
}
export default App;