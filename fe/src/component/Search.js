import * as React from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import { ApiHelper } from '../helpers/fetchHelper';


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
          // const resArr = resposnse.predicted_words.splice(5)
            setOptions(resposnse.predicted_words)
        });
    }



  },[value])



  const handleSubmit = (event) => {
    if (event.key == 'Enter') {
       const data = props.data;
    //    data.push(event.target.value);
       props.setSymptoms(event.target.value);
    }
    else
    {   
        setValue(event.target.value)    
    }
        

    // setValue({
    //   title: dialogValue.title,
    //   year: parseInt(dialogValue.year, 10),
    // });

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
        renderInput={(params) => <TextField {...params} label="Search" />}
      />
    </React.Fragment>
  );
}

// Top 100 films as rated by IMDb users. http://www.imdb.com/chart/top
