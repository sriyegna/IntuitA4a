const BASE_URL = "http://localhost:8080";

export const USER_URL = `${BASE_URL}/user`;
export const USER_API_ROUTES = {
  login: `${USER_URL}/login`,
  logout: `${USER_URL}/register`,
};

export const JOB_URL = `${BASE_URL}/job`;
export const JOB_API_ROUTES = {
  postJob: `${JOB_URL}/post`,
  fetchTenMostRecent: `${JOB_URL}/tenRecent`,
  fetchTenMostActive: `${JOB_URL}/tenActive`,
};

export const BID_URL = `${BASE_URL}/bid`;
export const BID_API_ROUTES = {
  postBid: `${BID_URL}/post`,
};
