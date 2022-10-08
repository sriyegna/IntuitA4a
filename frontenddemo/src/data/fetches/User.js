import axios from "./axios";
import { USER_API_ROUTES } from "../../constants/APIRoutes";

export const login = (name, password) => {
  return axios
    .post(USER_API_ROUTES.login, {
      name,
      password,
    })
    .then((res) => {
      return res.data;
    });
};

export const register = (name, password, actorType) => {
  return axios
    .post(USER_API_ROUTES.logout, {
      name,
      password,
      actorType,
    })
    .then((res) => {
      return res.data;
    });
};
