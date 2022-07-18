import React from "react";
import Grid from '@mui/material/Grid';
import Card from './Card'
import InteractiveList from '../DataTable';
import SearchDiagnosis from './SearchDiagnosis';
import SearchSnowmed from "./SeachSnowmed";

const DiagnosisForm=()=>{
    const [diagnosis,setDiagnosis] = React.useState([]);
    const [diagnosisICD,setDiagnosisICD] = React.useState([]);
    const addDiagnosis = (data)=>{
        if(!diagnosis.includes(data)){
            diagnosis.push(data)
            setDiagnosis([...diagnosis]);
        }
    }
    const addDiagnosisICD = (data)=>{
        if(!diagnosisICD.includes(data)){
            diagnosisICD.push(data)
            setDiagnosisICD([...diagnosisICD]);
        }
    }
    const deleteDiagnosis = (data)=>{
        if(diagnosis.includes(data)){
            const index = diagnosis.indexOf(data)
            diagnosis.splice(index,1);
            setDiagnosis([...diagnosis]);

        }   
    }
        const deleteDiagnosisICD = (data)=>{
            if(diagnosisICD.includes(data)){
                const index = diagnosisICD.indexOf(data)
                diagnosisICD.splice(index,1);
                setDiagnosisICD([...diagnosisICD]);
    
            } 
    };

        return(<>
        <Grid container spacing={2}>
        <Grid xs={6} item>
        <Card variant="outlined" styleCard={{height:'91%','margin-top': '1em'}} >
        <Grid container spacing={2}>
        <Grid xs={6} item>
                <h2>Add Diagnosis from ICD-11</h2>
                <br></br>
                <SearchDiagnosis addDiagnosisICD={addDiagnosisICD}/>
                </Grid>
                <Grid xs={8} item>
                
                <InteractiveList data={diagnosisICD} setSymptoms={deleteDiagnosisICD}></InteractiveList>
            </Grid>
            </Grid>
        </Card>
            </Grid>
            <Grid xs={6} item>

        <Card variant="outlined" styleCard={{height:'91%','margin-top': '1em'}} >
        <Grid container spacing={2}>
        <Grid xs={6} item>
        <h2>Add Diagnosis from SNOMED</h2>
        <br></br>
        <SearchSnowmed addDiagnosis={addDiagnosis}></SearchSnowmed>
        </Grid>
        <Grid xs={8} item>
                
                <InteractiveList data={diagnosis} setSymptoms={deleteDiagnosis}></InteractiveList>
            </Grid>
            </Grid>
        </Card>

            </Grid>
            </Grid>

        </>
        )
}

export default DiagnosisForm;