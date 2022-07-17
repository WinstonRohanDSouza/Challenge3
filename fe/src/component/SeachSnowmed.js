import * as React from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import BasicSelect from './Select';
import _ from 'lodash';
// import {snomed} from '../../public/snomed';
import {snomed} from '../snomed'


export default function SearchSnowmed(props) {
    const defaultOptions = [
      "Giant cell tumor of bone, malignant",
      "Heart attack",
      "Traumatic amputation ",
    ];
    
  const [value, setValue] = React.useState('');
  const [selectOptions,setSelectOptions] = React.useState([]);

  const handleSubmit = (event) => {
    if (event.key == 'Enter') {
       const data = props.data;
       const optionss = [];
       Object.keys(snomed).forEach((snomed_key)=>{
        if(snomed_key.toLowerCase().includes(event.target.value.toLowerCase()))
        {
          optionss.push(...snomed[snomed_key])
        }
       })
        setSelectOptions([...optionss]);

    //    data.push(event.target.value);
      //  props.addDiagnosis(event.target.value);
    }
    else
    {   
        setValue(event.target.value)    
    }
  };

  return (
    <React.Fragment>
      <Autocomplete
        value={value}
        onChange={(event, newValue) => {
            setValue(newValue);
          
        }}
        onKeyPress={handleSubmit}
        id="free-solo-dialog-demo"
        options={defaultOptions}
        getOptionLabel={option => option}
        handleHomeEndKeys
        renderOption={(props, option) => <li {...props}>{option}</li>}
        sx={{ width: '100%'}}
        freeSolo
        renderInput={(params) => <TextField {...params} label="Search" />}
      />
      <br></br>
      <BasicSelect options={selectOptions} addDiagnosis={props.addDiagnosis}></BasicSelect>
    </React.Fragment>
  );
}

// Top 100 films as rated by IMDb users. http://www.imdb.com/chart/top
