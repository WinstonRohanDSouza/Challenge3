import * as React from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';

export default function RadioButtonsGroup(props) {
    const handleChange=(event)=>{
    };
    // onChange={handleChange}
  return (
    <FormControl>
      <FormLabel id="demo-radio-buttons-group-label"  sx={{paddingRight: '10%',paddingLeft: '10%'}}>{props.question}</FormLabel>
      <RadioGroup
        aria-labelledby="demo-radio-buttons-group-label"
        defaultValue="female"
        name="radio-buttons-group"
        sx={{paddingRight: '3%',paddingLeft: '3%'}}
        onChange={(event)=>{handleChange(event,props.question)}}
      >
        {
            props.answer.map((ans)=>{
                return (
                    <FormControlLabel value={ans} control={<Radio />} label={ans}/>
                )
            })
        }
      </RadioGroup>
    </FormControl>
  );
}