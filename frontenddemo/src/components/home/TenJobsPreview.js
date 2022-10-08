import { Stack, Grid, Pagination, Typography } from "@mui/material";
import JobCard from "./JobCard";
import { useState, useEffect, useCallback } from "react";
/**
 * Shows ten jobCards for the homescreen
 * @param  {} {data}
 */
const TenJobsPreview = ({ data }) => {
  const [visibleData, setVisibleData] = useState([]);
  const [page, setPage] = useState(1);

  /**
   * When data is received, set the visible data and page for pagination
   */
  useEffect(() => {
    setVisibleData(data.slice(0, 2));
    setPage(1);
  }, [data]);

  /**
   * Change handler for MUI pagination component
   * @param  {} event
   * @param  {} value - of new page number
   */
  const handleChange = useCallback(
    (event, value) => {
      setPage(value);
      setVisibleData(data.slice(value * 2 - 2, value * 2));
    },
    [data]
  );

  return data && data.length > 0 ? (
    <>
      <Grid container spacing={2}>
        {visibleData.map((item) => (
          <Grid item xs={6} key={item.id}>
            <JobCard job={item} />
          </Grid>
        ))}
      </Grid>
      <Stack alignItems="center">
        <Pagination
          count={Math.ceil(data.length / 2)}
          page={page}
          onChange={handleChange}
        />
      </Stack>
    </>
  ) : (
    <Typography variant="h5" textAlign={"center"}>
      No ads available yet.
    </Typography>
  );
};

export default TenJobsPreview;
