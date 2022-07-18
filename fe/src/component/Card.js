import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';

export default function BasicCard(props) {
  return (
    <Card sx={props.styleCard}variant={props.variant}>
<CardContent></CardContent>
      <CardActions>
        {props.children}
      </CardActions>
    </Card>
  );
}
