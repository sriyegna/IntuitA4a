import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import MenuIcon from "@mui/icons-material/Menu";
import Button from "@mui/material/Button";
import MenuItem from "@mui/material/MenuItem";
import { useNavigate } from "react-router-dom";
import { isLoggedIn, setUserAccount, isUserPoster } from "../utils/Auth";
import ROUTES from "../constants/Routes";

const NavBar = () => {
  const pages = isUserPoster()
    ? [ROUTES.home.toUpperCase(), ROUTES.post.toUpperCase()]
    : [ROUTES.home.toUpperCase()];

  const navigate = useNavigate();
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const isUserLoggedIn = isLoggedIn();

  const handleOpenNavMenu = React.useCallback((event) => {
    setAnchorElNav(event.currentTarget);
  }, []);

  const handleNavigate = React.useCallback(
    (page) => {
      setAnchorElNav(null);
      navigate(`/${ROUTES[page.toLowerCase()]}`);
    },
    [navigate]
  );

  const loginOutButtonHandler = React.useCallback(() => {
    if (isUserLoggedIn) {
      setUserAccount(null);
    }
    navigate(`/${ROUTES.auth}`);
  }, [isUserLoggedIn, navigate]);

  return (
    <AppBar position="static">
      <Box mx={3}>
        <Toolbar disableGutters>
          <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "left",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              open={Boolean(anchorElNav)}
              onClose={handleNavigate}
              sx={{
                display: { xs: "block", md: "none" },
              }}
            >
              {pages.map((page) => (
                <MenuItem key={page} onClick={() => handleNavigate(page)}>
                  <Typography textAlign="center">{page}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>

          <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
            {pages.map((page) => (
              <Button
                key={page}
                onClick={() => handleNavigate(page)}
                sx={{ color: "white", display: "block" }}
                alt={page}
              >
                {page}
              </Button>
            ))}
          </Box>

          <Button color="inherit" onClick={loginOutButtonHandler}>
            {isUserLoggedIn ? "Logout" : "Log in"}
          </Button>
        </Toolbar>
      </Box>
    </AppBar>
  );
};
export default NavBar;
