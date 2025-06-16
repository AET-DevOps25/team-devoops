import React from 'react';
import { Typography, Paper, Box } from '@mui/material';

const Invitations = () => {
  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        Invitations
      </Typography>
      <Paper sx={{ p: 3 }}>
        <Typography variant="body1">
          Coming Soon: View and manage your meeting invitations.
        </Typography>
      </Paper>
    </Box>
  );
};

export default Invitations;
