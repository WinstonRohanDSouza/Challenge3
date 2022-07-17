import * as React from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import { ApiHelper } from '../helpers/fetchHelper';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';
import InteractiveList from '../DataTable';




export default function SearchDiagnosis(props) {    
    const defaultOptions = [
    ];
    
  const [value, setValue] = React.useState('');
  const [options,setOptions] = React.useState(defaultOptions);

  const handleClose = () => {


  };
  

  const icddiagnosis=()=>{
    
    const url = 'https://id.who.int/icd/entity/search?q='+value+'&useFlexisearch=false&flatResults=true&highlightingEnabled=true';
        return fetch(url, {  // Return promise
            method: 'GET',
            withCredentials: false,
            headers:{   
                'Authorization':' Bearer',
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
            let title =  response.destinationEntities[i];
            title = title.replace("<em class='found'>", ""); 
            title = title.replace("</em>", ""); 
            options.push(title);
        }
        setOptions([...options]);
    }
}
//   React.useEffect(()=>{
//     if(value && value.length > 2){
//         const url='https://intelli-search-csh.herokuapp.com/autopredict';
//         ApiHelper(url,{
//             text: value,
//             text_type:'diagnosis'
//         },'POST')
//         .then(resposnse => {
//           // const resArr = resposnse.predicted_words.splice(5)
//             setOptions(resposnse.predicted_words)
//         });
//     }



//   },[value])


  const handleClick=(event)=>{

  }
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
    </React.Fragment>
  );
}

// Top 100 films as rated by IMDb users. http://www.imdb.com/chart/top
