import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import { FormattedMessage } from 'react-intl';
import { ApiHelper } from '../helpers/fetchHelper';
import RadioControl from './RadioControl';


function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `vertical-tab-${index}`,
    'aria-controls': `vertical-tabpanel-${index}`,
  };
}

export default function VerticalTabs(props) {
  const [value, setValue] = React.useState(0);
  const [symptomTabs,setSymptomTabs] = React.useState({});

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const renderForm = (mainSymptom,symptom)=>{
    return (
        <>{
            Object.keys(symptom).map((key)=>{
                return (<RadioControl question={key} answer={symptom[key]}></RadioControl>)
        })
        }
        </>
    )

  }

  React.useEffect(()=>{
        const url ='http://localhost:5000/disease/get';
        ApiHelper(url, props.symptoms,'POST')
        .then(resposnse => {
            setSymptomTabs(resposnse);
        });

  },[props.fetchDiseases])
  return (
    <Box
      sx={{ flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height: 224 }}
    >
      <Tabs
        orientation="vertical"
        value={value}
        onChange={handleChange}
        aria-label="Vertical tabs example"
        sx={props.styleTabs}
      >
        {Object.keys(symptomTabs).map((symptom,index)=>{
             return <Tab label={<FormattedMessage id={symptom}/>} {...a11yProps(index)}   sx={{paddingBottom: '20%',borderBottom: '1px solid #c8cfca'}} />
        })}
      </Tabs>
            {Object.keys(symptomTabs).map((symptom,index)=>{
                return (
                    <TabPanel value={value} index={index}>
                       {renderForm(symptom,symptomTabs[symptom][symptom])}
                    </TabPanel>
                )
           })}

    </Box>
  );
}
