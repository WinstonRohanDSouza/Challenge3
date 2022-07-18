import * as React from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import { ApiHelper } from '../helpers/fetchHelper';
import { FormattedMessage } from "react-intl";


export default function FreeSoloCreateOptionDialog(props) {
    const defaultOptions = [
        'Cough',
        'Cold',
        'Fever',
        'Headache',
        'Nausea',
        'Chills'
    ];
    
  const [value, setValue] = React.useState('');
  const [options,setOptions] = React.useState(defaultOptions);

  const handleClose = () => {


  };
  React.useEffect(()=>{
    if(value && value.length > 2){
        const url='https://intelli-search-csh.herokuapp.com/autopredict';
        ApiHelper(url,{
            text: value,
            text_type:'symptoms'
        },'POST')
        .then(resposnse => {
            setOptions(resposnse.predicted_words)
        });
    }



  },[value])



  const handleSubmit = (event) => {
    if (event.key == 'Enter') {
       const data = props.data;
       props.setSymptoms(event.target.value);
    }
    else
    {   
        setValue(event.target.value)    
    }

    handleClose();
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
        options={options}
        getOptionLabel={option => option}
        handleHomeEndKeys
        renderOption={(props, option) => <li {...props}>{option}</li>}
        sx={{ width: '100%'}}
        freeSolo
        renderInput={(params) => <TextField {...params} label={<FormattedMessage id={"Search"}/>} />}
      />
    </React.Fragment>
  );
}

