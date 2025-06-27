import React, { useEffect, useState, useMemo } from 'react';
import {
  Typography,
  Paper,
  Box,
  Button,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  CircularProgress,
  SelectChangeEvent,
  Grid,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import {
  getMatchRequests,
  MatchRequest,
  deleteMatchRequest,
  submitMatchRequest,
  SubmitMatchRequest,
} from '../services/matchRequestService';
import MatchRequestCard from './MatchRequestCard';
import CreateMatchRequestDialog from './CreateMatchRequestDialog';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';

type SortOption = 'date' | 'status' | 'location';

const DUMMY_USER_ID = 'dummy-user-id';

const MatchRequests = () => {
  const [sortBy, setSortBy] = React.useState<SortOption>('date');
  const [matchRequests, setMatchRequests] = useState<MatchRequest[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false);

  const handleSortChange = (event: SelectChangeEvent<SortOption>) => {
    setSortBy(event.target.value as SortOption);
  };

  const fetchMatchRequests = async () => {
    try {
      setLoading(true);
      const data = await getMatchRequests(DUMMY_USER_ID);
      setMatchRequests(data);
      setError(null);
    } catch (err) {
      setError('Failed to load match requests. Please try again later.');
      console.error('Error loading match requests:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMatchRequests();
  }, []);

  const handleDelete = async (requestId: string) => {
    try {
      await deleteMatchRequest(requestId);
      // Refresh the list after successful deletion
      await fetchMatchRequests();
    } catch (err) {
      setError('Failed to delete match request. Please try again later.');
      console.error('Error deleting match request:', err);
    }
  };

  const handleCreateMatchRequest = async (matchRequestData: SubmitMatchRequest) => {
    try {
      await submitMatchRequest(matchRequestData);
      setIsCreateDialogOpen(false);
      await fetchMatchRequests();
    } catch (err) {
      setError('Failed to create match request. Please try again later.');
      console.error('Error creating match request:', err);
    }
  };

  const sortedMatchRequests = useMemo(() => {
    return [...matchRequests].sort((a, b) => {
      switch (sortBy) {
        case 'date':
          // Sort by date, earliest first
          return new Date(a.date).getTime() - new Date(b.date).getTime();

        case 'status':
          // Sort by status: PENDING first, then MATCHED, then others
          const statusOrder = { PENDING: 0, MATCHED: 1, REMATCH: 2, UNMATCHABLE: 3, EXPIRED: 4 };
          return (
            (statusOrder[a.status as keyof typeof statusOrder] || 5) -
            (statusOrder[b.status as keyof typeof statusOrder] || 5)
          );

        case 'location':
          // Sort alphabetically by location
          return a.location.localeCompare(b.location);

        default:
          return 0;
      }
    });
  }, [matchRequests, sortBy]);

  return (
    <Box>
      <Box sx={{ mb: 3 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
          <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
            <FormControl size="small" sx={{ minWidth: 120 }}>
              <InputLabel id="sort-select-label">Sort by</InputLabel>
              <Select
                labelId="sort-select-label"
                value={sortBy}
                label="Sort by"
                onChange={handleSortChange}
              >
                <MenuItem value="date">Date</MenuItem>
                <MenuItem value="status">Status</MenuItem>
                <MenuItem value="location">Location</MenuItem>
              </Select>
            </FormControl>
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={() => setIsCreateDialogOpen(true)}
            >
              Create Match Request
            </Button>
          </Box>
        </Box>
      </Box>

      {loading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', p: 3 }}>
          <CircularProgress />
        </Box>
      ) : error ? (
        <Typography color="error" sx={{ p: 2 }}>
          {error}
        </Typography>
      ) : matchRequests.length === 0 ? (
        <Typography sx={{ p: 2, textAlign: 'center' }}>
          No match requests found. Create your first match request to get started!
        </Typography>
      ) : (
        <Grid container spacing={3} justifyContent="flex-start">
          {sortedMatchRequests.map((matchRequest) => (
            <Grid item xs={12} sm={12} md={6} lg={4} xl={3} key={matchRequest.requestID}>
              <MatchRequestCard matchRequest={matchRequest} onDelete={handleDelete} />
            </Grid>
          ))}
        </Grid>
      )}

      <CreateMatchRequestDialog
        open={isCreateDialogOpen}
        onClose={() => setIsCreateDialogOpen(false)}
        onSubmit={handleCreateMatchRequest}
      />
    </Box>
  );
};

export default MatchRequests;
