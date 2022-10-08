import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import NavBar from "./NavBar";
import { getDate } from "../utils/DateTime";
import { useLocation } from "react-router-dom";
import { fetchJobById } from "../data/fetches/Job";
import { isLoggedIn, isUserBidder, isUserPoster } from "../utils/Auth";
import ROUTES from "../constants/Routes";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"), // use actual for all non-hook parts
  useNavigate: jest.fn(),
}));

jest.mock("../utils/Auth", () => ({
  isUserPoster: jest.fn(),
  isLoggedIn: jest.fn(),
}));

test("Load navbar as poster", async () => {
  isUserPoster.mockReturnValue(true);
  isLoggedIn.mockReturnValue(true);

  render(<NavBar />);

  // Assert
  expect(screen.getAllByText(ROUTES.home.toUpperCase()).length).toBeGreaterThan(
    0
  );
  expect(screen.getAllByText(ROUTES.post.toUpperCase()).length).toBeGreaterThan(
    0
  );
  expect(screen.getByText("Logout")).toBeInTheDocument();
});

test("Load navbar as bidder", async () => {
  isUserPoster.mockReturnValue(false);
  isLoggedIn.mockReturnValue(true);

  render(<NavBar />);

  // Assert
  expect(screen.getAllByText(ROUTES.home.toUpperCase()).length).toBeGreaterThan(
    0
  );
  expect(screen.queryByText(ROUTES.post.toUpperCase())).not.toBeInTheDocument();
  expect(screen.getByText("Logout")).toBeInTheDocument();
});
