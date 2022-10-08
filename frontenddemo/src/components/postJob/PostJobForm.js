import {
  Box,
  Button,
  TextareaAutosize,
  TextField,
  Typography,
} from "@mui/material";
import { useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { postJob } from "../../data/fetches/Job";
import { getUserId, isLoggedIn } from "../../utils/Auth";
import { getOneDayFromNow } from "../../utils/DateTime";
import ROUTES from "../../constants/Routes";

/**
 * A form for actorType = poster to post ads
 */
const PostJobForm = () => {
  const navigate = useNavigate();
  const isUserLoggedIn = isLoggedIn();
  const [inputs, setInputs] = useState({
    description: "",
    requirements: "",
    name: "",
    contact: "",
    expirationTime: getOneDayFromNow(),
  });

  const onChangeHandler = useCallback((event) => {
    setInputs((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  }, []);

  /**
   * Simple custom validator to validate inputs before post request
   */
  const isValid = useCallback(() => {
    const { description, requirements, name, contact, expirationTime } = inputs;
    if (description === "") {
      return "Description cannot be empty";
    } else if (new Blob([description]).size > 16384) {
      return "Description has to be less than 16kb";
    } else if (requirements === "") {
      return "Requirements cannot be empty";
    } else if (new Blob([requirements]).size > 16384) {
      return "Requirements has to be less than 16kb";
    } else if (name === "") {
      return "Name cannot be empty";
    } else if (name.length > 250) {
      return "Name has to be less than 250 characters";
    } else if (contact === "") {
      return "Contact cannot be empty";
    } else if (contact.length > 250) {
      return "Contact has to be less than 250 characters";
    } else if (expirationTime === "") {
      return "Expiration Time cannot be unselected";
    }
    return true;
  }, [inputs]);

  /**
   * Handler to validate inputs and fire off post job API request
   */
  const onSubmitHandler = useCallback(() => {
    const formIsValid = isValid();
    if (formIsValid === true) {
      postJob(
        getUserId(),
        inputs.description,
        inputs.requirements,
        inputs.name,
        inputs.contact,
        inputs.expirationTime
      )
        .then((data) => {
          navigate(`/${ROUTES.job}/${data.id}`);
        })
        .catch((err) => {
          alert(err?.response?.data);
        });
    } else {
      alert(formIsValid);
    }
  }, [
    inputs.contact,
    inputs.description,
    inputs.expirationTime,
    inputs.name,
    inputs.requirements,
    isValid,
    navigate,
  ]);

  return (
    inputs && (
      <>
        <Box>
          <Typography variant="h4" textAlign="center">
            Post a job
          </Typography>
          <Typography>Description</Typography>
          <TextareaAutosize
            minRows={4}
            style={{ width: "100%", marginTop: "10px" }}
            value={inputs.description}
            onChange={onChangeHandler}
            name="description"
            aria-label="description"
          />
          <Typography>Requirements</Typography>
          <TextareaAutosize
            minRows={4}
            style={{ width: "100%", marginTop: "10px" }}
            value={inputs.requirements}
            onChange={onChangeHandler}
            name="requirements"
            aria-label="requirements"
          />
          <TextField
            label="Name"
            margin="normal"
            fullWidth
            value={inputs.name}
            onChange={onChangeHandler}
            name="name"
            aria-label="name"
          />
          <TextField
            label="Contact"
            margin="normal"
            fullWidth
            value={inputs.contact}
            onChange={onChangeHandler}
            name="contact"
            aria-label="contact"
          />
          <TextField
            type="datetime-local"
            name="expirationTime"
            aria-label="expiration time"
            onChange={onChangeHandler}
            value={inputs.expirationTime}
            fullWidth
            inputProps={{
              min: getOneDayFromNow(),
            }}
          />
        </Box>
        <Button
          variant="contained"
          color="warning"
          sx={{ marginTop: 3, borderRadius: 3 }}
          disabled={!isUserLoggedIn}
          onClick={onSubmitHandler}
        >
          Post Job
        </Button>
      </>
    )
  );
};

export default PostJobForm;
