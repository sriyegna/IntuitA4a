import axios from "./axios";
import { BID_API_ROUTES } from "../../constants/APIRoutes";

export const postBid = async (userId, jobId, bidAmount) => {
  return axios
    .post(BID_API_ROUTES.postBid, {
      userId,
      jobId,
      bidAmount,
    })
    .then((res) => {
      return res.data;
    });
};
