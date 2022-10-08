import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Job from "./Job";
import { JOBS } from "../../mocks/mocks";
import { getDate } from "../../utils/DateTime";
import { useLocation } from "react-router-dom";
import { fetchJobById } from "../../data/fetches/Job";
import { isUserBidder } from "../../utils/Auth";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"), // use actual for all non-hook parts
  useLocation: jest.fn(),
}));

jest.mock("../../data/fetches/Job", () => ({
  fetchJobById: jest.fn(),
}));

jest.mock("../../utils/Auth", () => ({
  isUserBidder: jest.fn(),
}));


test("Load job with mocked job request", async () => {
  const job = JOBS[0];
  const {
    name,
    contact,
    numberOfBids,
    description,
    requirements,
    expirationTime,
  } = job;
  fetchJobById.mockReturnValue(Promise.resolve({ data: job }));

  render(<Job id={0} />);

  await screen.findByText(name);

  // Assert
  expect(screen.getByText(name)).toBeInTheDocument();
  expect(screen.getByText(contact)).toBeInTheDocument();
  expect(screen.getByText(numberOfBids)).toBeInTheDocument();
  expect(screen.getByText(description)).toBeInTheDocument();
  expect(screen.getByText(requirements)).toBeInTheDocument();
  expect(
    screen.getByText(getDate(expirationTime).toString())
  ).toBeInTheDocument();
  expect(screen.queryByText("CLOSED")).not.toBeInTheDocument();
});

test("Load closed job", async () => {
  const job = JOBS[3];
  const {
    name,
    contact,
    numberOfBids,
    description,
    requirements,
    expirationTime,
  } = job;
  fetchJobById.mockReturnValue(Promise.resolve({ data: job }));

  render(<Job id={3} />);

  await screen.findByText(name);

  expect(screen.getByText(name)).toBeInTheDocument();
  expect(screen.getByText(contact)).toBeInTheDocument();
  expect(screen.getByText(numberOfBids)).toBeInTheDocument();
  expect(screen.getByText(description)).toBeInTheDocument();
  expect(screen.getByText(requirements)).toBeInTheDocument();
  expect(
    screen.getByText(getDate(expirationTime).toString())
  ).toBeInTheDocument();
  expect(screen.getByText("CLOSED")).toBeInTheDocument();
  expect(screen.getByText("No Winner")).toBeInTheDocument();
});

test("Load job as bidder", async () => {
  const job = JOBS[0];
  const {
    name,
    contact,
    numberOfBids,
    description,
    requirements,
    expirationTime,
  } = job;
  fetchJobById.mockReturnValue(Promise.resolve({ data: job }));
  isUserBidder.mockReturnValue(true);

  render(<Job id={0} />);

  await screen.findByText(name);

  // Assert
  expect(screen.getByText(name)).toBeInTheDocument();
  expect(screen.getByText(contact)).toBeInTheDocument();
  expect(screen.getByText(numberOfBids)).toBeInTheDocument();
  expect(screen.getByText(description)).toBeInTheDocument();
  expect(screen.getByText(requirements)).toBeInTheDocument();
  expect(
    screen.getByText(getDate(expirationTime).toString())
  ).toBeInTheDocument();
  expect(screen.getByText("Post Bid")).toBeInTheDocument();
});

test("Load job as not bidder", async () => {
  const job = JOBS[0];
  const {
    name,
    contact,
    numberOfBids,
    description,
    requirements,
    expirationTime,
  } = job;
  fetchJobById.mockReturnValue(Promise.resolve({ data: job }));
  isUserBidder.mockReturnValue(false);

  render(<Job id={0} />);

  await screen.findByText(name);

  // Assert
  expect(screen.getByText(name)).toBeInTheDocument();
  expect(screen.getByText(contact)).toBeInTheDocument();
  expect(screen.getByText(numberOfBids)).toBeInTheDocument();
  expect(screen.getByText(description)).toBeInTheDocument();
  expect(screen.getByText(requirements)).toBeInTheDocument();
  expect(
    screen.getByText(getDate(expirationTime).toString())
  ).toBeInTheDocument();
  expect(screen.queryByText("Post Bid")).not.toBeInTheDocument();
});
