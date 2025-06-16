import React from 'react';
import { Typography, Paper, Box } from '@mui/material';

const MeetingPreferences = () => {
  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        Meeting Preferences
      </Typography>
      <Paper sx={{ p: 3 }}>
        <Typography variant="body1">
          Coming Soon: Configure your meeting preferences and availability.
        </Typography>
      </Paper>
    </Box>
  );
};

export default MeetingPreferences;
