import * as React from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import Tooltip from '@mui/material/Tooltip';
import InfoIcon from '@mui/icons-material/Info';
import IconButton from '@mui/material/IconButton';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import { FormattedMessage } from 'react-intl';
import {solidSkin,pusSkin} from '../helpers/imageURLS';

function NewTab(url) {
  window.open(
  url, "_blank");
}
export default function RadioButtonsGroup(props) {
    const handleChange=(event)=>{
    };
  return (
    <FormControl>
      <FormLabel id="demo-radio-buttons-group-label"  sx={{paddingRight: '10%',paddingLeft: '10%'}}>{<FormattedMessage id={props.question}/>}</FormLabel>
      <RadioGroup
        aria-labelledby="demo-radio-buttons-group-label"
        defaultValue=""
        name="radio-buttons-group"
        onChange={(event)=>{handleChange(event,props.question)}}
      >
        {
            props.answer.map((ans)=>{
                if(ans =="Small (1-2mm) solid skin " || ans =="Large pus filled skin eruptions"){
                  return (
                    <FormControlLabel value={ans} control={<Radio sx={{paddingLeft: '5%'}}/>} label={
                      <div>
                        <FormattedMessage id={ans}/> <IconButton onClick={()=>{ ans == "Small (1-2mm) solid skin "? NewTab(solidSkin):NewTab(pusSkin)}}>
                         <InfoIcon />
                        </IconButton>
                        </div>
  
                  }/>
                  )
                }
                else{
                  return (<FormControlLabel value={ans} control={<Radio />} label={<div><FormattedMessage id={ans}/></div>}/>)
                }
               
            })
            
        }
      </RadioGroup>
    </FormControl>
  );
}