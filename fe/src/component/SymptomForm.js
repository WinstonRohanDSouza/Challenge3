import React from "react";
import Grid from '@mui/material/Grid';
import Card from './Card'
import VerticalTabs from './Tabs';
import FreeSoloCreateOptionDialog from './Search';
import Button from '@mui/material/Button';
import InteractiveList from '../DataTable';

const SymptomForm=()=>{
    const [symptoms,setSymptoms] = React.useState([]);
    const [allSymptomsData,setAllSymptomsData] = React.useState({});
    const addSymptom = (data)=>{
        const symptomsNew = symptoms;
        if(!symptomsNew.includes(data)){
            symptomsNew.push(data)
            setSymptoms([...symptomsNew]);
        }
    }
    const symptomsForm = React.useRef(null);
    const deleteSymptom = (data)=>{
        if(symptoms.includes(data)){
            const index = symptoms.indexOf(data)
            symptoms.splice(index,1);
            setSymptoms([...symptoms]);

        }   
    };
    const submitValues = ()=>{
            alert('Symptoms Submitted Successfully');
    }
    const [fetchDiseases,SetfetchDiseases] = React.useState(false)
    const gotoScreeningForm=()=>{
        const allSympts = {}
        document.getElementById('grid1').style.display = 'none';
        document.getElementById('grid2').style.display = 'block';
        SetfetchDiseases(true)
    }
        return(<>
        <Grid container spacing={2}>
            <Grid item xs={8}>
              
                <Card variant="outlined" styleCard={{height:'91%', 'margin-left': '2%','margin-top': '1em'}} >
                <Grid container spacing={2} ref={symptomsForm} id='grid1'>
                <Grid xs={8} item>
                <h2>Add your Symptoms</h2>
                <FreeSoloCreateOptionDialog data={symptoms} setSymptoms={addSymptom}></FreeSoloCreateOptionDialog>
                </Grid>
                {/* <VerticalTabs styleTabs={{ height:'100%'}}/> */}
                <br></br>
                <Grid xs={8} item>
                
                    <InteractiveList data={symptoms} setSymptoms={deleteSymptom}></InteractiveList>
                </Grid>
                <Grid xs={8} item>
                <Button sx={{marginLeft: '80%'}}disabled={symptoms.length == 0 ? true: false}onClick={gotoScreeningForm} variant="contained" size="large">Continue</Button>
                </Grid>
                </Grid>
                <Grid container spacing={2} ref={symptomsForm} id='grid2' sx={{display:'none'}}>
                <Grid xs={8} item>
                <h2>Symptoms Checker</h2>
                </Grid>
                <VerticalTabs fetchDiseases={fetchDiseases}  symptoms={symptoms}styleTabs={{ height:'100%'}}/>
                <br></br>
                <Grid xs={8} item>
                
                </Grid>
                <Grid xs={8} item>
                <Button sx={{marginLeft: '100%'}}disabled={symptoms.length == 0 ? true: false}onClick={submitValues} variant="contained" size="large">Submit</Button>
                </Grid>
                </Grid>
                </Card>
            </Grid>
            <Grid xs={4}><img  width="500" height="500" src="TaeAugust07.jpg"/>
            </Grid>
            </Grid>

        </>
        )
}

export default SymptomForm;