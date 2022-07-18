import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";

const columns = [
  { field: "id", headerName: "Id", width: 110 },
  { field: "source", headerName: "Source", width: 150 },
  { field: "medicationName", headerName: "Medications", width: 200 },
  { field: "dosage", headerName: "dosage", width: 120 },
  {
    field: "dosageForm",
    headerName: "dosageForm",
    width: 140,
  },
  {
    field: "frequency",
    headerName: "frequency",
    width: 170,
  }
];

export default function TableData(props) {
  const { show, medications } = props;
  const [display, setDisplay] = useState(false);
  const [medDetails, setMedDetails] = useState([]);

  return (
    <>
      {display && (
        <div className={`table-grid ${medications.length === 0 ? "hide" : ""}`}>
          <DataGrid className="data-grid-table-img-url" rows={medications} columns={columns} pageSize={5} rowsPerPageOptions={[6]} checkboxSelection />
        </div>
      )}
      {show && (
        <div className={`table-grid ${medications.length === 0 ? "hide" : ""}`}>
          <DataGrid className="data-grid-table-img-url" rows={medications} columns={columns} pageSize={5} rowsPerPageOptions={[6]} checkboxSelection />
        </div>
      )}
    </>
  );
}

// button-box