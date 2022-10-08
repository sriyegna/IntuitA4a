import { Box } from "@mui/material";
import PostJobForm from "../components/postJob/PostJobForm";

export const PostJob = () => {
  return (
    <div>
      <form>
        <Box
          display="flex"
          flexDirection={"column"}
          maxWidth={1000}
          alignItems="center"
          justifyContent="center"
          margin="auto"
          marginTop={5}
          padding={3}
          borderRadius={5}
          boxShadow={"5px 5px 10px #ccc"}
          sx={{
            ":hover": {
              boxShadow: "10px 10px 20px #ccc",
            },
          }}
        >
          <PostJobForm />
        </Box>
      </form>
    </div>
  );
};
