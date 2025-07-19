import React, { useEffect, useState, useMemo } from 'react';
import {
  Typography,
  Box,
  Button,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  SelectChangeEvent,
  Grid,
  Alert,
  Skeleton,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import {
  useMatchRequestService,
  MatchRequest,
  SubmitMatchRequest,
} from '../services/matchRequestService';
import MatchRequestCard from './MatchRequestCard';
import CreateMatchRequestDialog from './CreateMatchRequestDialog';
import { useUserID } from '../contexts/UserIDContext';

type SortOption = 'date' | 'status' | 'location';

const MatchRequests = () => {
  const { getMatchRequests, deleteMatchRequest, submitMatchRequest } = useMatchRequestService();
  const userID = useUserID();
  const [sortBy, setSortBy] = React.useState<SortOption>('date');
  const [matchRequests, setMatchRequests] = useState<MatchRequest[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [errorIs404, setErrorIs404] = useState(false);
  const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false);

  const handleSortChange = (event: SelectChangeEvent<SortOption>) => {
    setSortBy(event.target.value as SortOption);
  };

  const fetchMatchRequests = async () => {
    if (!userID) return;
    try {
      setLoading(true);
      setError(null);
      setErrorIs404(false);
      const data = await getMatchRequests(userID);
      setMatchRequests(data);
    } catch (err) {
      const anyErr = err as any;
      if (anyErr?.status === 404) {
        setError(null);
        setErrorIs404(true);
        setMatchRequests([]);
      } else {
        setError(anyErr?.message || 'Failed to load match requests. Please try again later.');
        setErrorIs404(false);
        setMatchRequests([]);
        console.error('Error loading match requests:', err);
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (userID) fetchMatchRequests();
  }, [userID]);

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
    } catch (err: any) {
      if (err?.response?.status === 409) {
        setError('You can only create one match request per day.');
      } else {
        setError('Failed to create match request. Please try again later.');
      }
      console.error('Error creating match request:', err);
    }
  };

  // Skeleton component for match request cards
  const MatchRequestCardSkeleton = () => (
    <Box
      sx={{
        border: 1,
        borderColor: 'divider',
        borderRadius: 2,
        p: 2,
        height: '200px',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-between',
      }}
    >
      {/* Header with status chip */}
      <Box
        sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 2 }}
      >
        <Skeleton variant="text" width="40%" height={20} />
        <Skeleton variant="rectangular" width={60} height={24} sx={{ borderRadius: 1 }} />
      </Box>

      {/* Date and time */}
      <Box sx={{ mb: 2 }}>
        <Skeleton variant="text" width="60%" height={16} sx={{ mb: 1 }} />
        <Skeleton variant="text" width="50%" height={16} />
      </Box>

      {/* Location */}
      <Box sx={{ mb: 2 }}>
        <Skeleton variant="text" width="70%" height={16} />
      </Box>

      {/* Action buttons */}
      <Box sx={{ display: 'flex', gap: 1, mt: 'auto' }}>
        <Skeleton variant="rectangular" width="50%" height={32} sx={{ borderRadius: 1 }} />
        <Skeleton variant="rectangular" width="50%" height={32} sx={{ borderRadius: 1 }} />
      </Box>
    </Box>
  );

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
        <Grid container spacing={3} justifyContent="flex-start">
          {[1, 2, 3, 4, 5, 6].map((index) => (
            <Grid item xs={12} sm={12} md={6} lg={4} xl={3} key={index}>
              <MatchRequestCardSkeleton />
            </Grid>
          ))}
        </Grid>
      ) : errorIs404 ? (
        <Typography sx={{ p: 2, textAlign: 'center' }}>
          No match requests found. Create your first match request to get started!
        </Typography>
      ) : error ? (
        <Box>
          <Alert severity="error" sx={{ mt: 1 }}>
            {error}
          </Alert>
        </Box>
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
