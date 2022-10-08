import { Navigate } from "react-router-dom";
import { isLoggedIn, isUserPoster } from "../utils/Auth";
import ROUTES from "../constants/Routes";

const ProtectedRoute = (props) => {
  if (!(props.route === ROUTES.post && isUserPoster())) {
    return <Navigate to={`/${ROUTES.auth}`} />;
  } else if (!isLoggedIn()) {
    return <Navigate to={`/${ROUTES.auth}`} />;
  } else {
    return props.children;
  }
};

export default ProtectedRoute;
