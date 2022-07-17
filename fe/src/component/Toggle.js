import React, { useState } from "react";
import LoginForm from "./LoginForm";
import RegistrationForm from "./RegistrationForm";

function Toggle() {
  const [active, setActive] = useState(true);

  return (
    <div id="toggle-tracker">
      <div id="toggle-display-btns">
        <div id="btn"></div>
        <div id="toggle-login-register-btns">
          <button className="toggle-btn1 toggle-btn toggle-btn-login" onClick={() => setActive(true)}>
            Login
          </button>
          <button className="toggle-btn1 toggle-btn toggle-btn-register" onClick={() => setActive(false)} type="button">
            Register
          </button>
        </div>
        {/* <button className="toggle-btn toggle-btn-login" onClick={() => setActive(true)}>
          Login
        </button>
        <button className="toggle-btn toggle-btn-register" onClick={() => setActive(false)} type="button">
          Register
        </button> */}

        <>{active && <LoginForm />}</>
        <>{!active && <RegistrationForm />}</>
      </div>
    </div>
  );
}

export default Toggle;
