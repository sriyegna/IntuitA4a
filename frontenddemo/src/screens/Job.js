import { Box } from "@mui/material";
import { useParams } from "react-router-dom";
import { default as JobComponent } from "../components/job/Job";

export const Job = () => {
  const params = useParams();

  return (
    <Box
      maxWidth={1000}
      m={2}
      marginTop={5}
      padding={3}
      borderRadius={5}
      boxShadow={4}
      margin="0 auto"
    >
      <JobComponent id={params.id} />
    </Box>
  );
};
