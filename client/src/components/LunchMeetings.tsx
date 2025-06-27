import React from 'react';
import { Typography, Paper, Box } from '@mui/material';

const LunchMeetings = () => {
  return (
    <Box
      sx={{
        maxWidth: '800px',
        margin: '0 auto',
      }}
    >
      <Typography variant="h4" component="h1" gutterBottom>
        Lunch Meetings
      </Typography>
      <Paper
        elevation={3}
        sx={{
          p: 3,
          backgroundColor: 'white',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: '100px',
        }}
      >
        <Typography
          variant="body1"
          sx={{
            fontSize: '1.1rem',
            color: 'text.primary',
          }}
        >
          Coming Soon: Schedule and manage your lunch meetings.
        </Typography>
      </Paper>
    </Box>
  );
};

export default LunchMeetings;
