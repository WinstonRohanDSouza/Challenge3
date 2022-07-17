import React from "react";
import { useFormik } from "formik";
import * as Yup from "yup";


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
const validate = (values) => {
  const errors = {};
  if (!values.firstName) {
    errors.firstName = "Required";
  } else if (values.firstName.length > 15) {
    errors.firstName = "Must be 15 characters or less";
  }

  if (!values.lastName) {
    errors.lastName = "Required";
  } else if (values.lastName.length > 20) {
    errors.lastName = "Must be 20 characters or less";
  }

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

  if (!values.confirmPassword) {
    errors.confirmPassword = "Required";
  } else if (values.confirmPassword !== values.password) {
    errors.confirmPassword = "passwords dont match";
  }

  return errors;
};
const validationSchema = Yup.object({
  firstName: Yup.string().required("Required"),
  lastName: Yup.string().required("Required"),
  email: Yup.string().email("Invalid email format").required("Required"),
  password: Yup.string().required("Required"),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref("password", "")], "Passwords dont match")
    .required("Required"),
});
const RegistrationForm = () => {
  const formik = useFormik({
    initialValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
    },
    validate, 
    onSubmit:  async (values) => {
      const axiosFetchData = await ApiHelper("http://localhost:5000/user/api/register",values,'POST');
      if(axiosFetchData.res == "success") {
        alert('Registered Successfully.Please Login')
      }
      else{
        alert('Please check your credentials and try again')
      }
    },
  });
  return (
    <form id="register" className="input-group-register" onSubmit={formik.handleSubmit}>
      <div className="form-control">
        <label htmlFor="firstName">First Name</label>
        <input className="input-field" id="firstName" name="firstName" type="text" onChange={formik.handleChange} value={formik.values.firstName} />
        {formik.errors.firstName ? <div className="error">{formik.errors.firstName}</div> : null}
      </div>
      <div className="form-control">
        <label htmlFor="lastName">Last Name</label>
        <input className="input-field" id="lastName" name="lastName" type="text" onChange={formik.handleChange} value={formik.values.lastName} />
        {formik.errors.lastName ? <div className="error">{formik.errors.lastName}</div> : null}
      </div>

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
      <div className="form-control">
        <label htmlFor="confirmPassword">Confirm password</label>
        <input className="input-field" id="confirmPassword" name="confirmPassword" type="password" onChange={formik.handleChange} value={formik.values.confirmPassword} />
        {formik.errors.confirmPassword ? <div className="error">{formik.errors.confirmPassword}</div> : null}
      </div>
      <button className="submit-btn submit-btn-register" type="submit">
        Register
      </button>
    </form>
  );
};

export default RegistrationForm;
