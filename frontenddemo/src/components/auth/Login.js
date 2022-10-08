import { Typography, TextField, Button } from "@mui/material";
import { useState, useCallback } from "react";
import { login } from "../../data/fetches/User";

/**
 * Login component for authorization screen
 * @param function onChangeForm - To handle switching between registration/login form
 * @param function onSuccess - To handle what happens on a succesful login
 */
const Login = ({ onChangeForm, onSuccess }) => {
  const [inputs, setInputs] = useState({
    name: "",
    password: "",
  });

  const onChangeHandler = useCallback((event) => {
    setInputs((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  }, []);

  /**
   * Makes a login API request with the name/password and calls onSuccess
   * @param  {} event
   */
  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();
      login(inputs.name, inputs.password)
        .then((data) => {
          onSuccess(data);
        })
        .catch((err) => {
          alert(err?.response?.data);
        });
    },
    [onSuccess, inputs.name, inputs.password]
  );

  return (
    inputs && (
      <>
        <Typography variant="h3" padding={3} textAlign="center">
          Login
        </Typography>
        <TextField
          value={inputs.name}
          onChange={onChangeHandler}
          variant="outlined"
          label="Name"
          name="name"
          margin="normal"
        />
        <TextField
          type="password"
          value={inputs.password}
          onChange={onChangeHandler}
          variant="outlined"
          label="Password"
          name="password"
          margin="normal"
        />
        <Button
          variant="contained"
          color="warning"
          sx={{ marginTop: 3, borderRadius: 3 }}
          onClick={handleSubmit}
        >
          Login
        </Button>
        <Button sx={{ marginTop: 3, borderRadius: 3 }} onClick={onChangeForm}>
          Register
        </Button>
      </>
    )
  );
};

export default Login;
