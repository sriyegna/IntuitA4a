import {
  Typography,
  TextField,
  Box,
  RadioGroup,
  FormControlLabel,
  Button,
  Radio,
} from "@mui/material";
import { useState, useCallback } from "react";
import { register } from "../../data/fetches/User";
import ACTOR_TYPES from "../../constants/ActorTypes";

/**
 * Registration component for authorization screen
 * @param function onChangeForm - To handle switching between registration/login form
 * @param function onSuccess - To handle what happens on a succesful registration
 */
const Register = ({ onChangeForm, onSuccess }) => {
  const [inputs, setInputs] = useState({
    name: "",
    password: "",
    actorType: ACTOR_TYPES.bidder,
  });

  const onChangeHandler = useCallback((event) => {
    setInputs((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  }, []);

  /**
   * Makes a registration API request with the name/password/actorType and calls onSuccess
   * @param  {} event
   */
  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();
      register(inputs.name, inputs.password, inputs.actorType)
        .then((res) => {
          res && onSuccess(res);
        })
        .catch((err) => {
          alert(err?.response?.data);
        });
    },
    [inputs.actorType, inputs.name, inputs.password, onSuccess]
  );

  return (
    inputs && (
      <>
        <Typography variant="h3" padding={3} textAlign="center">
          Register
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
        <Box marginTop={1}>
          <RadioGroup
            name="actorType"
            onChange={onChangeHandler}
            value={inputs.actorType}
          >
            <FormControlLabel
              value={ACTOR_TYPES.bidder}
              control={<Radio />}
              label={ACTOR_TYPES.bidder.toUpperCase()}
            />
            <FormControlLabel
              value={ACTOR_TYPES.poster}
              control={<Radio />}
              label={ACTOR_TYPES.poster.toUpperCase()}
            />
          </RadioGroup>
        </Box>
        <Button
          variant="contained"
          color="warning"
          sx={{ marginTop: 3, borderRadius: 3 }}
          onClick={handleSubmit}
        >
          Register
        </Button>
        <Button sx={{ marginTop: 3, borderRadius: 3 }} onClick={onChangeForm}>
          Login
        </Button>
      </>
    )
  );
};

export default Register;
