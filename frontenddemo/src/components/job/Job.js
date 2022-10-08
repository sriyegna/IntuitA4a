import { Box, Button, Typography, Grid } from "@mui/material";
import { useEffect, useState, useCallback } from "react";
import { fetchJobById } from "../../data/fetches/Job";
import { postBid } from "../../data/fetches/Bid";
import { getUserId, isUserBidder } from "../../utils/Auth";
import { getTimeUntil, getDate, isExpired } from "../../utils/DateTime";
import Bid from "./Bid";
import { useLocation } from "react-router-dom";

/**
 * Renders a Job view component
 * @param  {} {id}
 */
const Job = ({ id }) => {
  const location = useLocation();
  const [job, setJob] = useState(null);
  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  /**
   * Handles bid submission
   * Updates new bid value in job/state if lower
   * @param  {} bidAmount
   */
  const handleSubmit = useCallback(
    (bidAmount) => {
      const userId = getUserId();
      postBid(userId, job.id, bidAmount)
        .then((res) => {
          const updatedJob = {
            ...job,
            lowestBid:
              !job.lowestBid || res.bidAmount < job.lowestBid
                ? res.bidAmount
                : job.lowestBid,
            numberOfBids: job.numberOfBids + 1,
          };
          setJob(updatedJob);
        })
        .catch((err) => {
          alert(err?.response?.data);
        });
    },
    [job]
  );

  /**
   * If we have a job in navigation state, use that. If not, fetch it
   */
  useEffect(() => {
    const fetchJob = async () => {
      fetchJobById(id)
        .then((res) => {
          setJob(res.data);
        })
        .catch((err) => {
          alert(JSON.stringify(err?.response?.data));
        });
    };

      fetchJob();
    }, [id]);

  return (
    job && (
      <>
        <Bid
          handleSubmit={handleSubmit}
          handleClose={handleClose}
          open={open}
          job={job}
        />
        <Box>
          <Grid container spacing={2}>
            {job.closed && (
              <>
                <Grid item md={6} xs={12}>
                  <Typography variant="h6">Auction Status</Typography>
                  <Typography>CLOSED</Typography>
                </Grid>
                <Grid item md={6} xs={12}>
                  <Typography variant="h6">Winner</Typography>
                  <Typography>{job.winner}</Typography>
                </Grid>
              </>
            )}
            <Grid item md={6} xs={12}>
              <Typography variant="h6">Name</Typography>
              <Typography>{job.name}</Typography>
            </Grid>
            <Grid item md={6} xs={12}>
              <Typography variant="h6">Contact</Typography>
              <Typography>{job.contact}</Typography>
            </Grid>

            <Grid item md={6} xs={12}>
              <Typography variant="h6">Number of Bids</Typography>
              <Typography>{job.numberOfBids}</Typography>
            </Grid>
            <Grid item md={6} xs={12}>
              <Typography variant="h6">Lowest Bid</Typography>
              <Typography>{job.lowestBid || "No Bid Yet"}</Typography>
            </Grid>

            <Grid item md={6} xs={12}>
              <Typography variant="h6">Description</Typography>
              <Typography sx={{ overflowWrap: "anywhere" }}>
                {job.description}
              </Typography>
            </Grid>
            <Grid item md={6} xs={12}>
              <Typography variant="h6">Requirements</Typography>
              <Typography sx={{ overflowWrap: "anywhere" }}>
                {job.requirements}
              </Typography>
            </Grid>
            <Grid item md={6} xs={12}>
              <Typography variant="h6">Expiration Time</Typography>
              <Typography>{getDate(job.expirationTime).toString()}</Typography>
            </Grid>
            <Grid item md={6} xs={12}>
              <Typography variant="h6">Time Left</Typography>
              <Typography>
                {job.closed ? "Expired" : getTimeUntil(job.expirationTime)}
              </Typography>
            </Grid>
          </Grid>
        </Box>
        {!job.closed && isUserBidder() && (
          <Button
            variant="contained"
            color="warning"
            sx={{ marginTop: 3, borderRadius: 3 }}
            onClick={handleOpen}
          >
            Post Bid
          </Button>
        )}
      </>
    )
  );
};

export default Job;
