import React, { useState } from "react";
import CircularProgress from "@mui/material/CircularProgress";
import Box from "@mui/material/Box";

function Progress(props) {
  const { isLoading } = props;
  //   const [show, setShow] = useState(false);
  return (
    <div className={`progress-loader`}>
      <Box sx={{ display: "flex" }}>
        <CircularProgress />
      </Box>
    </div>
  );
}

export default Progress;