import * as React from 'react';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';


export default function BasicSelect(props) {
  const [selectedOption, setSelectedOption] = React.useState('');
  const [options,setOptions] = React.useState(props.options);

  const handleChange = (event) => {
    props.addDiagnosis(event.target.value);
    setSelectedOption(event.target.value);
  };
//   React.useEffect(()=>{
//     setOptions(props.options)
//   },[props.options])

  return (
    <Box sx={{ minWidth: 120 }}>
      <FormControl fullWidth >
        <InputLabel id="demo-simple-select-label">Select A Symptom</InputLabel>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={selectedOption}
          label="Select A Symptom"
          onChange={handleChange}
        >
            {props.options.map((option)=>{
                return  (<MenuItem value={option}>{option}</MenuItem>)
            })}
        </Select>
      </FormControl>
    </Box>
  );
}
