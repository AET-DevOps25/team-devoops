import React from 'react';
import { Typography, Paper, Box } from '@mui/material';

const Dashboard = () => {
  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        Dashboard
      </Typography>
      <Paper sx={{ p: 3 }}>
        <Typography variant="body1">
          Welcome to your dashboard! This is where you'll see your main content.
        </Typography>
      </Paper>
    </Box>
  );
};

export default Dashboard;
