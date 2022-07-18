import * as React from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import { ApiHelper } from '../helpers/fetchHelper';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';
import InteractiveList from '../DataTable';
import BasicSelect from './Select';




export default function SearchDiagnosis(props) {    
    const defaultOptions = [
    ];
    
  const [value, setValue] = React.useState('');
  const [options,setOptions] = React.useState(defaultOptions);
  

  const icddiagnosis=()=>{
    
    const url = 'https://id.who.int/icd/entity/search?q=erysipelas&useFlexisearch=false&flatResults=true&highlightingEnabled=true';
        return fetch(url, {  // Return promise
            method: 'GET',
            withCredentials: false,
            headers:{   
                'Authorization':'Bearer xxx',
                'API-Version':'v2',
                'Accept-Language':'en',     
                'Content-Type': 'application/json',     
                }
    
        })
            .then(res => res.json())
            .then((result) => {
                return result;
            }, (error) => {
                return error;
            })
}
const handleOptions=async ()=>{
    const response =await icddiagnosis()
    const options = []
    if(response.error === false && response.destinationEntities.length > 0){
        const limit = response.destinationEntities.length > 5? 5: response.destinationEntities.length;
        for(let i = 0 ;i < limit ;i+=1) {
            let {title} =  response.destinationEntities[i];
            let {id} =  response.destinationEntities[i];
            id = id.split('/entity/')[1];
            title = title.replace("<em class='found'>", ""); 
            title = title.replace("</em>", ""); 
            options.push(id+" : "+title);
        }
        setOptions([...options]);
    }
}


  return (
    <React.Fragment>
      <Autocomplete
        value={value}
        onChange={(event, newValue) => {
            setValue(newValue);
          
        }}
        onKeyPress={(event, newValue) => {
            setValue(newValue);    
        }}
        id="free-solo-dialog-demo"
        options={options}
        getOptionLabel={option => option}
        handleHomeEndKeys
        renderOption={(props, option) => <li {...props}>{option}</li>}
        sx={{ width: '100%'}}
        freeSolo
        renderInput={(params) => <TextField {...params} label="Search" />}
      />
      <IconButton onClick={handleOptions} sx={{marginLeft:'40vh',marginTop:'-8vh'}} aria-label="search" color="primary" >
        <SearchIcon />
      </IconButton>
      <BasicSelect options={options} addDiagnosis={props.addDiagnosisICD}></BasicSelect>
    </React.Fragment>
  );
}

