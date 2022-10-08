import {
  Dialog,
  DialogTitle,
  DialogActions,
  DialogContent,
  DialogContentText,
  TextField,
  Button,
} from "@mui/material";
import { useState, useCallback } from "react";
/**
 * Renders a Bid dialog where user can submit bid
 * @param  {} open - Whether bid dialog should be open
 * @param  {} handleSubmit
 * @param  {} handleClose - Called to close the dialog
 * @param  {} job - Job pertaining to the bid
 */
const Bid = ({ open, handleSubmit, handleClose, job }) => {
  const [bidAmount, setBidAmount] = useState(0);

  const onChange = (event) => {
    if (event.target.value < 0) {
      alert("Bid cannot be lower than 0");
      setBidAmount(0);
    } else {
      setBidAmount(event.target.value);
    }
  };

  const handleSubmitBid = useCallback(() => {
    handleSubmit(bidAmount);
    handleClose();
  }, [bidAmount, handleClose, handleSubmit]);

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Post a new bid</DialogTitle>
      <DialogContent>
        <DialogContentText>
          {job.lowestBid
            ? `Current lowest bid is: ${job.lowestBid}`
            : `Make the first bid`}
        </DialogContentText>
        <TextField
          autoFocus
          margin="dense"
          label="Bid amount"
          type="number"
          min={0}
          fullWidth
          variant="standard"
          value={bidAmount}
          onChange={onChange}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Cancel</Button>
        <Button onClick={handleSubmitBid}>Post Bid</Button>
      </DialogActions>
    </Dialog>
  );
};

export default Bid;
