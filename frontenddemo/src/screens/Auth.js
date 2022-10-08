import { Box } from "@mui/material";
import { useState, useCallback } from "react";
import Register from "../components/auth/Register";
import Login from "../components/auth/Login";
import { setUserAccount } from "../utils/Auth";
import { useNavigate } from "react-router-dom";
import ROUTES from "../constants/Routes";

/**
 * Auth screen to show either a Login or Registration form
 */
const Auth = () => {
  const [isLogin, setIsLogin] = useState(true);
  const navigate = useNavigate();

  const onChangeForm = useCallback(() => {
    setIsLogin((prevState) => !prevState);
  }, []);

  const onSuccess = useCallback(
    (user) => {
      setUserAccount(user);
      navigate(`/${ROUTES.home}`);
    },
    [navigate]
  );

  return (
    <div>
      <form>
        <Box
          display="flex"
          flexDirection={"column"}
          maxWidth={400}
          alignItems="center"
          justifyContent="center"
          margin="auto"
          marginTop={5}
          padding={3}
          borderRadius={5}
          boxShadow={"5px 5px 10px #ccc"}
          sx={{
            ":hover": {
              boxShadow: "10px 10px 20px #ccc",
            },
          }}
        >
          {isLogin ? (
            <Login onChangeForm={onChangeForm} onSuccess={onSuccess} />
          ) : (
            <Register onChangeForm={onChangeForm} onSuccess={onSuccess} />
          )}
        </Box>
      </form>
    </div>
  );
};

export default Auth;
