import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import { PostJob } from "../screens/PostJob";
import Auth from "../screens/Auth";
import Home from "../screens/Home";
import NavBar from "../components/NavBar";
import { Job } from "../screens/Job";
import ProtectedRoute from "./ProtectedRoute";
import ROUTES from "../constants/Routes";

const Router = () => {
  return (
    <BrowserRouter>
      <NavBar />
      <Routes>
        <Route path="/" element={<Navigate to={`/${ROUTES.home}`} />} />,
        <Route exact path={`/${ROUTES.auth}`} element={<Auth />} />
        ,
        <Route exact path={`/${ROUTES.home}`} element={<Home />} />,
        <Route
          exact
          path={`/${ROUTES.post}`}
          element={
            <ProtectedRoute route={`${ROUTES.post}`}>
              <PostJob />
            </ProtectedRoute>
          }
        />
        <Route exact path={`/${ROUTES.job}/:id`} element={<Job />} />
      </Routes>
    </BrowserRouter>
  );
};

export default Router;
