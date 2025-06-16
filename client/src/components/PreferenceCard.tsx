import React from 'react';
import { Typography, Paper, Box, Button, Grid, Chip } from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import { MeetingPreference } from '../services/preferenceService';

interface PreferenceCardProps {
  preference: MeetingPreference;
  onDelete: (preferenceId: string) => void;
}

const formatTimeSlot = (start: string, end: string) => {
  const startDate = new Date(start);
  const endDate = new Date(end);

  const dateStr = startDate.toLocaleDateString('de-DE', {
    day: '2-digit',
    month: '2-digit',
    year: '2-digit',
  });

  const startTime = startDate.toLocaleTimeString('de-DE', {
    hour: '2-digit',
    minute: '2-digit',
    hour12: false,
  });

  const endTime = endDate.toLocaleTimeString('de-DE', {
    hour: '2-digit',
    minute: '2-digit',
    hour12: false,
  });

  return `${dateStr} von ${startTime} Uhr bis ${endTime} Uhr`;
};

const PreferenceCard: React.FC<PreferenceCardProps> = ({ preference, onDelete }) => (
  <Paper
    elevation={2}
    sx={{
      p: 2,
      mb: 2,
      display: 'flex',
      gap: 2,
      position: 'relative',
    }}
  >
    {/* Placeholder Image */}
    <Box
      sx={{
        width: 120,
        height: 120,
        bgcolor: 'grey.300',
        borderRadius: 1,
        flexShrink: 0,
      }}
    />

    <Box sx={{ flexGrow: 1 }}>
      <Box
        sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 1 }}
      >
        <Typography variant="h6">
          {preference.mensa.name} -{' '}
          {formatTimeSlot(preference.timeSlot.start, preference.timeSlot.end)}
        </Typography>
        <Button
          variant="outlined"
          color="error"
          size="small"
          onClick={() => onDelete(preference.preferenceID)}
          sx={{
            borderColor: 'error.main',
            color: 'error.main',
            '&:hover': {
              borderColor: 'error.dark',
              backgroundColor: 'error.light',
              color: 'error.dark',
            },
          }}
        >
          Cancel
        </Button>
      </Box>

      <Box sx={{ mb: 2 }}>
        <Grid container spacing={1}>
          <Grid item>
            <Chip
              icon={preference.filters.sameFaculty ? <CheckCircleIcon /> : <CancelIcon />}
              label="Same Faculty"
              color="default"
              variant={preference.filters.sameFaculty ? 'filled' : 'outlined'}
              size="small"
            />
          </Grid>
          <Grid item>
            <Chip
              icon={preference.filters.sameStudyProgram ? <CheckCircleIcon /> : <CancelIcon />}
              label="Same Study Program"
              color="default"
              variant={preference.filters.sameStudyProgram ? 'filled' : 'outlined'}
              size="small"
            />
          </Grid>
        </Grid>
      </Box>

      <Chip
        label={preference.status}
        color={preference.status === 'OPEN' ? 'warning' : 'success'}
        size="small"
      />
    </Box>
  </Paper>
);

export default PreferenceCard;
