import {
  Card,
  CardActions,
  CardContent,
  Button,
  Typography,
  Box,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useCallback } from "react";
import { getTimeUntil, getDate } from "../../utils/DateTime";
import ROUTES from "../../constants/Routes";

/**
 * Renders a jobCard for the home screen
 * @param  {} {job}
 */
const JobCard = ({ job }) => {
  const navigate = useNavigate();

  /**
   * Navigates to the job page when clicked
   */
  const handleOnClick = useCallback(() => {
    navigate(`/${ROUTES.job}/${job.id}`);
  }, [job, navigate]);

  return (
    <Box m={3}>
      <Card sx={{ boxShadow: 3 }}>
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            Name: {job.name}
          </Typography>
          <Typography gutterBottom variant="subtitle2" component="div">
            {`Created: ${getDate(job.created).toLocaleDateString()} ${getDate(
              job.created
            ).toLocaleTimeString()}`}
          </Typography>
          <Typography gutterBottom variant="subtitle2" component="div">
            Time left:{" "}
            {job.closed ? "Expired" : getTimeUntil(job.expirationTime)}
          </Typography>
          <Typography variant="body2" color="text.secondary" noWrap={true}>
            Description: {job.description}
          </Typography>
        </CardContent>
        <CardActions>
          <Button size="small" onClick={handleOnClick}>
            View Full Posting
          </Button>
        </CardActions>
      </Card>
    </Box>
  );
};

export default JobCard;
