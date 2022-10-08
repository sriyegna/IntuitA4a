import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import JobCard from "./JobCard";
import { JOBS } from "../../mocks/mocks";
import { getTimeUntil, getDate } from "../../utils/DateTime";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => jest.fn(),
}));

test("Load JobCard", async () => {
  const job = JOBS[0];
  const { name, created, expirationTime, description } = job;

  render(<JobCard job={job} />);

  // Assert
  expect(screen.getByText(`Name: ${name}`)).toBeInTheDocument();
  expect(
    screen.getByText(
      `Created: ${getDate(created).toLocaleDateString()} ${getDate(
        created
      ).toLocaleTimeString()}`
    )
  ).toBeInTheDocument();
  expect(
    screen.getByText(`Time left: ${getTimeUntil(expirationTime)}`)
  ).toBeInTheDocument();
  expect(screen.getByText(`Description: ${description}`)).toBeInTheDocument();
});
