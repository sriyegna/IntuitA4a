import axios from "./axios";
import { JOB_URL, JOB_API_ROUTES } from "../../constants/APIRoutes";

export const postJob = (
  userId,
  description,
  requirements,
  name,
  contact,
  expirationTime
) => {
  return axios
    .post(JOB_API_ROUTES.postJob, {
      userId,
      description,
      requirements,
      name,
      contact,
      expirationTime,
    })
    .then((res) => {
      return res.data;
    });
};

export const fetchTenMostRecent = () => {
  return axios.get(JOB_API_ROUTES.fetchTenMostRecent);
};

export const fetchTenMostActive = () => {
  return axios.get(JOB_API_ROUTES.fetchTenMostActive);
};

export const fetchJobById = (id) => {
  return axios.get(`${JOB_URL}?id=${id}`);
};
