import ACTOR_TYPES from "../constants/ActorTypes";

export const getUserAccount = () => {
  return (
    localStorage.getItem("user-account") &&
    JSON.parse(localStorage.getItem("user-account"))
  );
};

export const isLoggedIn = () => {
  const userAccount = getUserAccount();
  return !!userAccount;
};

export const getUserId = () => {
  const userAccount = getUserAccount();
  return userAccount.id;
};

export const isUserType = (actorType) => {
  const userAccount = getUserAccount();
  return userAccount && userAccount.actorType === actorType;
};

export const isUserPoster = () => {
  return isUserType(ACTOR_TYPES.poster);
};

export const isUserBidder = () => {
  return isUserType(ACTOR_TYPES.bidder);
};

export const setUserAccount = (userAccount) => {
  localStorage.setItem("user-account", JSON.stringify(null || userAccount));
};
