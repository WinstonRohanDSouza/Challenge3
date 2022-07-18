import React from "react";
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FolderIcon from '@mui/icons-material/Folder';
import DeleteIcon from '@mui/icons-material/Delete';
import { FormattedMessage } from "react-intl";


export default function InteractiveList(props) {
    const data = props.data;  
    const [tableData,setTableData] = React.useState(props.data);

    React.useEffect(()=>{
            setTableData([...props.data]);
    },[data,props.data])
    const handleClick=(value)=>{
        props.setSymptoms(value);
    }
    return(
        <>
        <List dense={true}>
               {
                tableData.map(item=> {return (
                    <ListItem
                    secondaryAction={
                      <IconButton edge="end" aria-label="delete">
                        <DeleteIcon onClick={()=>{
                         handleClick(item) ;      
                }}/>
                      </IconButton>
                    }
                  >
                    <ListItemText
                      primary={<FormattedMessage id={item}/>}
                    />
                  </ListItem>
                )})}
              
            </List>
            </>

        
    )
}