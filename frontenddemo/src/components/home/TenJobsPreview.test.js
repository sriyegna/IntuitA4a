import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import TenJobsPreview from "./TenJobsPreview";
import { JOBS } from "../../mocks/mocks";
import { getTimeUntil, getDate } from "../../utils/DateTime";

// pay attention to write it at the top level of your file
const mockedUsedNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => mockedUsedNavigate,
}));

test("Load TenJobPreview with data", async () => {
  render(<TenJobsPreview data={JOBS} />);

  // Assert
  for (let i = 0; i < 2; i++) {
    const { name, created, expirationTime, description } = JOBS[i];
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
  }
});

test("Load TenJobPreview with no data", async () => {
  render(<TenJobsPreview data={[]} />);

  // Assert
  expect(screen.getByText("No ads available yet.")).toBeInTheDocument();
});
