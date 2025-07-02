import React, { useState } from 'react';
import {
  Typography,
  Paper,
  Box,
  Button,
  Chip,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import { MatchRequest } from '../services/matchRequestService';
import mensaGarching from '../assets/mensa_garching.jpg';
import mensaArcisstr from '../assets/mensa_arcisstr.jpg';

interface MatchRequestCardProps {
  matchRequest: MatchRequest;
  onDelete: (requestId: string) => void;
}

const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', {
    weekday: 'short',
    month: 'short',
    day: 'numeric',
  });
};

const formatLocation = (location: string) => {
  return location === 'garching' ? 'Mensa Garching' : 'Mensa Arcisstraße';
};

const TIMESLOT_MAP: { [key: number]: { start: string; end: string } } = {
  1: { start: '10:00', end: '10:15' },
  2: { start: '10:15', end: '10:30' },
  3: { start: '10:30', end: '10:45' },
  4: { start: '10:45', end: '11:00' },
  5: { start: '11:00', end: '11:15' },
  6: { start: '11:15', end: '11:30' },
  7: { start: '11:30', end: '11:45' },
  8: { start: '11:45', end: '12:00' },
  9: { start: '12:00', end: '12:15' },
  10: { start: '12:15', end: '12:30' },
  11: { start: '12:30', end: '12:45' },
  12: { start: '12:45', end: '13:00' },
  13: { start: '13:00', end: '13:15' },
  14: { start: '13:15', end: '13:30' },
  15: { start: '13:30', end: '13:45' },
  16: { start: '13:45', end: '14:00' },
};

const formatTimeslots = (timeslots: number[]) => {
  if (!timeslots.length) return '';
  const sorted = [...timeslots].sort((a, b) => a - b);
  const ranges: { start: number; end: number }[] = [];
  let rangeStart = sorted[0];
  let prev = sorted[0];

  for (let i = 1; i < sorted.length; i++) {
    if (sorted[i] === prev + 1) {
      prev = sorted[i];
    } else {
      ranges.push({ start: rangeStart, end: prev });
      rangeStart = sorted[i];
      prev = sorted[i];
    }
  }
  ranges.push({ start: rangeStart, end: prev });

  return ranges
    .map(({ start, end }) => `${TIMESLOT_MAP[start].start}-${TIMESLOT_MAP[end].end}`)
    .join(', ');
};

const getStatusColor = (status: string) => {
  switch (status) {
    case 'PENDING':
      return 'warning';
    case 'MATCHED':
      return 'success';
    case 'UNMATCHABLE':
      return 'error';
    case 'REMATCH':
      return 'info';
    case 'EXPIRED':
      return 'default';
    default:
      return 'default';
  }
};

const getMensaImage = (location: string) => {
  if (location === 'garching') return mensaGarching;
  if (location === 'arcisstr') return mensaArcisstr;
  return undefined;
};

const MatchRequestCard: React.FC<MatchRequestCardProps> = ({ matchRequest, onDelete }) => {
  const [confirmOpen, setConfirmOpen] = useState(false);

  const handleCancelClick = () => setConfirmOpen(true);
  const handleCloseDialog = () => setConfirmOpen(false);
  const handleConfirmDelete = () => {
    setConfirmOpen(false);
    onDelete(matchRequest.requestID);
  };

  return (
    <Paper
      elevation={3}
      sx={{
        maxWidth: 400,
        width: '100%',
        mb: 2,
        overflow: 'hidden',
        borderRadius: 2,
        transition: 'all 0.3s ease-in-out',
        '&:hover': {
          elevation: 6,
          transform: 'translateY(-2px)',
        },
      }}
    >
      {/* Mensa Image */}
      <Box
        sx={{
          width: '100%',
          height: 160,
          position: 'relative',
          overflow: 'hidden',
        }}
      >
        <img
          src={getMensaImage(matchRequest.location)}
          alt={matchRequest.location}
          style={{
            width: '100%',
            height: '100%',
            objectFit: 'cover',
          }}
        />
        {/* Status badge overlay */}
        <Box
          sx={{
            position: 'absolute',
            top: 12,
            right: 12,
          }}
        >
          <Chip
            label={matchRequest.status}
            color={getStatusColor(matchRequest.status)}
            size="small"
            sx={{
              fontWeight: 'bold',
              fontSize: '0.75rem',
            }}
          />
        </Box>
      </Box>

      {/* Content */}
      <Box sx={{ p: 2 }}>
        {/* Mensa name and date */}
        <Box sx={{ mb: 2 }}>
          <Typography
            variant="h6"
            sx={{
              fontWeight: 600,
              mb: 0.5,
              color: 'text.primary',
            }}
          >
            {formatLocation(matchRequest.location)}
          </Typography>
          <Typography
            variant="body2"
            sx={{
              color: 'text.secondary',
              fontWeight: 500,
            }}
          >
            {formatDate(matchRequest.date)}
          </Typography>
        </Box>

        {/* Available times */}
        <Box sx={{ mb: 2 }}>
          <Typography
            variant="body2"
            color="text.secondary"
            sx={{
              mb: 1,
              fontWeight: 500,
            }}
          >
            Available times:
          </Typography>
          <Typography
            variant="body2"
            sx={{
              color: 'primary.main',
              fontWeight: 600,
              fontSize: '0.875rem',
            }}
          >
            {formatTimeslots(matchRequest.timeslots)}
          </Typography>
        </Box>

        {/* Preferences chips */}
        <Box sx={{ mb: 2 }}>
          <Typography
            variant="body2"
            color="text.secondary"
            sx={{
              mb: 1,
              fontWeight: 500,
            }}
          >
            Preferences:
          </Typography>
          <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 1 }}>
            <Chip
              icon={matchRequest.preferences.degreePref ? <CheckCircleIcon /> : <CancelIcon />}
              label={matchRequest.preferences.degreePref ? 'Same Degree' : 'Any Degree'}
              color={matchRequest.preferences.degreePref ? 'primary' : 'default'}
              variant={matchRequest.preferences.degreePref ? 'filled' : 'outlined'}
              size="small"
              sx={{ fontSize: '0.75rem' }}
            />
            <Chip
              icon={matchRequest.preferences.agePref ? <CheckCircleIcon /> : <CancelIcon />}
              label={matchRequest.preferences.agePref ? 'Similar Age' : 'Any Age'}
              color={matchRequest.preferences.agePref ? 'primary' : 'default'}
              variant={matchRequest.preferences.agePref ? 'filled' : 'outlined'}
              size="small"
              sx={{ fontSize: '0.75rem' }}
            />
            <Chip
              icon={matchRequest.preferences.genderPref ? <CheckCircleIcon /> : <CancelIcon />}
              label={matchRequest.preferences.genderPref ? 'Same Gender' : 'Any Gender'}
              color={matchRequest.preferences.genderPref ? 'primary' : 'default'}
              variant={matchRequest.preferences.genderPref ? 'filled' : 'outlined'}
              size="small"
              sx={{ fontSize: '0.75rem' }}
            />
          </Box>
        </Box>

        {/* Cancel button */}
        <Button
          variant="outlined"
          color="error"
          size="small"
          onClick={handleCancelClick}
          fullWidth
          sx={{
            borderColor: 'error.main',
            color: 'error.main',
            fontWeight: 600,
            '&:hover': {
              borderColor: 'error.dark',
              backgroundColor: 'error.light',
              color: 'error.dark',
            },
          }}
        >
          Cancel Request
        </Button>
      </Box>

      {/* Confirmation Dialog */}
      <Dialog open={confirmOpen} onClose={handleCloseDialog}>
        <DialogTitle>Cancel Match Request</DialogTitle>
        <DialogContent>Are you sure you want to delete this match request?</DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Close</Button>
          <Button onClick={handleConfirmDelete} color="error" variant="contained">
            Confirm
          </Button>
        </DialogActions>
      </Dialog>
    </Paper>
  );
};

export default MatchRequestCard;
