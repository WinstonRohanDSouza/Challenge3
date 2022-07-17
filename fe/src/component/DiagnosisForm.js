import React from "react";
import Grid from '@mui/material/Grid';
import Card from './Card'
import VerticalTabs from './Tabs';
import FreeSoloCreateOptionDialog from './Search';
import Button from '@mui/material/Button';
import InteractiveList from '../DataTable';
import SearchDiagnosis from './SearchDiagnosis';
import SearchSnowmed from "./SeachSnowmed";

const DiagnosisForm=()=>{
    const [diagnosis,setDiagnosis] = React.useState([]);
    const addDiagnosis = (data)=>{
        if(!diagnosis.includes(data)){
            diagnosis.push(data)
            setDiagnosis([...diagnosis]);
        }
    }

        return(<>
        <Grid container spacing={2}>
        <Grid xs={6} item>
        <Card variant="outlined" styleCard={{height:'91%','margin-top': '1em'}} >
        <Grid xs={6} item>
                <h2>Add Diagnosis from ICD-11</h2>
                <br></br>
                <SearchDiagnosis/>
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
                
                <InteractiveList data={diagnosis}></InteractiveList>
            </Grid>
            </Grid>
        </Card>

            </Grid>
            </Grid>

        </>
        )
}

export default DiagnosisForm;