import { Box, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import TenJobsPreview from "../components/home/TenJobsPreview";
import { fetchTenMostRecent, fetchTenMostActive } from "../data/fetches/Job";

/**
 * Home screen to show 2 sets of up to 10 cards each
 * First ten is most recent jobs
 * Second ten is most active jobs
 */
const Home = () => {
  const [tenRecentJobs, setTenRecentJobs] = useState([]);
  const [tenMostActiveJob, setTenMostActiveJobs] = useState([]);

  useEffect(() => {
    fetchTenMostRecent().then((res) => {
      setTenRecentJobs(res.data);
    });

    fetchTenMostActive().then((res) => {
      setTenMostActiveJobs(res.data);
    });
  }, []);

  return (
    <>
      <Box m={2} mt={4}>
        <Box my={2}>
          <Typography variant="h5" textAlign={"center"}>
            Ten Recent Jobs
          </Typography>
          <TenJobsPreview data={tenRecentJobs} />
        </Box>
        <Box my={2}>
          <Typography variant="h5" textAlign={"center"}>
            Ten Active Jobs
          </Typography>
          <TenJobsPreview data={tenMostActiveJob} />
        </Box>
      </Box>
    </>
  );
};

export default Home;
