import React, { useEffect, useState } from "react";
import { useFormik } from "formik";
import axios from "axios";
import useFetchData from "./useFetchData";
import {Link} from 'react-router-dom';

const validate = (values) => {
  const errors = {};

  if (!values.email) {
    errors.email = "Required";
  } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
    errors.email = "Invalid email address";
  }

  if (!values.password) {
    errors.password = "Required";
  } else if (values.password.length > 15) {
    errors.password = "Must be 15 characters or less";
  }

  return errors;
};


const LoginForm = () => {
    // const history = React.usseHistory()
  const [dataRes, setDataRes] = useState(true);

 function ApiHelper(url, data = {}, method = 'GET') {
    return fetch(url, {  // Return promise
        method: method,
        withCredentials: false,
        headers:{        
            'Content-Type': 'application/json',      
            },
       body:JSON.stringify(data)

    })
        .then(res => res.json())
        .then((result) => {
            return result;
        }, (error) => {
            return error;
        })
}


  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
    },
    validate,
    onSubmit: async (values) => {
      const axiosFetchData = await ApiHelper("http://localhost:5000/user/api/login",values,'POST');
      if(axiosFetchData?.code == 200){
        localStorage.setItem('name',axiosFetchData.result.firstName+' '+axiosFetchData.result.lastName);
        if(axiosFetchData.result.firstName.includes('doc')){
            window.history.pushState({}, '', '/diagnosis'); 
            window.location.reload(); 
        }else{
            window.history.pushState({}, '', '/symptomsPatient');
            window.location.reload();
        }
      }
      alert(JSON.stringify(values, null, 2));

      // const { data } = useFetchData("/Users/nv082513/Desktop/hackathon/naela-repo/hackathon/test.json");
    },
  });
  return (
    <form id="login" className="input-group-login" onSubmit={formik.handleSubmit}>
      <div className="form-control">
        <label htmlFor="email">Email</label>
        <input className="input-field" id="email" name="email" type="email" onChange={formik.handleChange} value={formik.values.email} />
        {formik.errors.email ? <div className="error">{formik.errors.email}</div> : null}
      </div>
      <div className="form-control">
        <label htmlFor="password">Password</label>
        <input className="input-field" id="password" name="password" type="password" onChange={formik.handleChange} value={formik.values.password} />
        {formik.errors.password ? <div className="error">{formik.errors.password}</div> : null}
      </div>
      <div id="btn"></div> 
      <button className="submit-btn submit-btn-login" type="submit">
        submit
      </button>
    </form>
  );
};

export default LoginForm;
