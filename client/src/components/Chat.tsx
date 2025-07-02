import { Typography, Paper, Box } from '@mui/material';

const Chat = () => {
  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        Chat
      </Typography>
      <Paper sx={{ p: 3 }}>
        <Typography variant="body1">
          Coming Soon: Chat with your colleagues and meeting participants.
        </Typography>
      </Paper>
    </Box>
  );
};

export default Chat;
