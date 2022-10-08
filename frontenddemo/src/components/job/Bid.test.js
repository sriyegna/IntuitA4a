import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Bid from "./Bid";
import { JOBS } from "../../mocks/mocks";

test("Load bid with lowestBid set", async () => {
  const job = JOBS[0];

  render(<Bid open={true} onClose={() => null} job={job} />);

  // Assert
  expect(
    screen.getByText(`Current lowest bid is: ${job.lowestBid}`)
  ).toBeInTheDocument();
});

test("Load bid with no lowestBid set", async () => {
  const job = JOBS[1];

  render(<Bid open={true} onClose={() => null} job={job} />);

  // Assert
  expect(screen.getByText(`Make the first bid`)).toBeInTheDocument();
});
