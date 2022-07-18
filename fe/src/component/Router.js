
import "../App.css";
import Toggle from "./Toggle";

function Login() {
  return (
    <div className="App">
      <div className="full-page ">
        <div className="navbar">
          <div>
            <a href="#">MedClinic</a>
          </div>
        </div>
        <div id="login-form" className="login-page">
          <div className="form-box">
            <div>
              <Toggle />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;

