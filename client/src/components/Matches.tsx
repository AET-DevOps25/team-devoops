import { useState, useEffect } from 'react';
import {
  Typography,
  Paper,
  Box,
  Alert,
  Card,
  CardContent,
  Chip,
  Grid,
  Divider,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  List,
  ListItem,
  ListItemText,
  Snackbar,
  Skeleton,
} from '@mui/material';
import { useMatchesService, MatchesServiceError } from '../services/matchesService';
import { MatchesResponse, Match, MatchStatus, ConversationStarter } from '../types/matches';
import { useMatchActions } from '../hooks/useMatchActions';
import MatchActionDialogs from './MatchActionDialogs';
import { useUserID } from '../contexts/UserIDContext';

const Matches = () => {
  const { getMatches } = useMatchesService();
  const userID = useUserID();
  const [matches, setMatches] = useState<MatchesResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [selectedStarters, setSelectedStarters] = useState<ConversationStarter[]>([]);

  const {
    openAcceptDialog,
    openRejectDialog,
    acceptDialogOpen,
    rejectDialogOpen,
    handleConfirmAccept,
    handleConfirmReject,
    handleCloseAcceptDialog,
    handleCloseRejectDialog,
    snackbarOpen,
    snackbarMessage,
    snackbarSeverity,
    handleCloseSnackbar,
    accepting,
    rejecting,
  } = useMatchActions({
    onMatchStatusChange: (matchID, status) => {
      setMatches((prev) => {
        if (!prev) return prev;
        return {
          ...prev,
          matches: prev.matches.map((m) => (m.matchID === matchID ? { ...m, status } : m)),
        };
      });
    },
  });

  useEffect(() => {
    const fetchMatches = async () => {
      if (!userID) return;

      try {
        setLoading(true);
        setError(null);

        // Fetch matches data
        const data = await getMatches(userID);
        setMatches(data);
      } catch (err) {
        const serviceError = err as MatchesServiceError;
        setError(serviceError.message || 'Failed to load matches');
      } finally {
        setLoading(false);
      }
    };

    fetchMatches();
  }, [userID]);

  const getStatusColor = (status: Match['status']) => {
    switch (status) {
      case 'CONFIRMED':
        return 'success';
      case 'SENT':
        return 'info';
      case 'UNSENT':
        return 'warning';
      case 'REJECTED':
        return 'error';
      case 'EXPIRED':
        return 'default';
      default:
        return 'default';
    }
  };

  const getConfirmedCount = (userStatuses: MatchStatus[]) => {
    return userStatuses.filter((user) => user.status === 'CONFIRMED').length;
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    });
  };

  const formatTime = (timeSlot: number) => {
    // Assuming time slots are 1-16 representing different times
    const times = [
      '8:00 AM',
      '8:30 AM',
      '9:00 AM',
      '9:30 AM',
      '10:00 AM',
      '10:30 AM',
      '11:00 AM',
      '11:30 AM',
      '12:00 PM',
      '12:30 PM',
      '1:00 PM',
      '1:30 PM',
      '2:00 PM',
      '2:30 PM',
      '3:00 PM',
      '3:30 PM',
    ];
    return times[timeSlot - 1] || 'Unknown time';
  };

  const handleViewAllStarters = (starters: ConversationStarter[]) => {
    setSelectedStarters(starters);
    setDialogOpen(true);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
    setSelectedStarters([]);
  };

  // Skeleton component for match cards
  const MatchCardSkeleton = () => (
    <Card sx={{ height: '280px', display: 'flex', flexDirection: 'column' }}>
      <CardContent sx={{ p: 2, flex: 1, display: 'flex', flexDirection: 'column' }}>
        <Box display="flex" justifyContent="space-between" alignItems="flex-start" mb={1.5}>
          <Skeleton variant="text" width="60%" height={20} />
          <Skeleton variant="rectangular" width={60} height={20} sx={{ borderRadius: 1 }} />
        </Box>

        {/* Accept/Reject buttons skeleton */}
        <Box display="flex" gap={1.5} mb={1.5}>
          <Skeleton variant="rectangular" width="50%" height={32} sx={{ borderRadius: 2 }} />
          <Skeleton variant="rectangular" width="50%" height={32} sx={{ borderRadius: 2 }} />
        </Box>

        {/* Match details skeleton */}
        <Box mb={1.5}>
          <Skeleton variant="text" width="40%" height={16} sx={{ mb: 0.5 }} />
          <Skeleton variant="text" width="50%" height={16} sx={{ mb: 0.5 }} />
          <Box display="flex" alignItems="center" gap={1}>
            <Skeleton variant="text" width="30%" height={16} />
            <Skeleton variant="rectangular" width={80} height={18} sx={{ borderRadius: 1 }} />
          </Box>
        </Box>

        <Divider sx={{ my: 1.5 }} />

        {/* Conversation starters skeleton */}
        <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
          <Skeleton variant="text" width="50%" height={16} sx={{ mb: 1 }} />
          <Box sx={{ flex: 1 }}>
            <Skeleton variant="text" width="90%" height={16} sx={{ mb: 0.5 }} />
            <Skeleton variant="text" width="85%" height={16} sx={{ mb: 0.5 }} />
            <Skeleton variant="text" width="30%" height={16} />
          </Box>
        </Box>
      </CardContent>
    </Card>
  );

  if (error) {
    return (
      <Box>
        <Alert severity="error" sx={{ mt: 1 }}>
          {error}
        </Alert>
      </Box>
    );
  }

  return (
    <Box>
      {loading ? (
        <Grid container spacing={2}>
          {[1, 2, 3].map((index) => (
            <Grid item xs={12} md={6} lg={4} key={index}>
              <MatchCardSkeleton />
            </Grid>
          ))}
        </Grid>
      ) : matches?.matches.length === 0 ? (
        <Paper sx={{ p: 2, textAlign: 'center' }}>
          <Typography variant="body2" color="text.secondary">
            No matches found. Check back later for new lunch opportunities!
          </Typography>
        </Paper>
      ) : (
        <Grid container spacing={2}>
          {matches?.matches.map((match) => (
            <Grid item xs={12} md={6} lg={4} key={match.matchID}>
              <Card sx={{ height: '280px', display: 'flex', flexDirection: 'column' }}>
                <CardContent sx={{ p: 2, flex: 1, display: 'flex', flexDirection: 'column' }}>
                  <Box
                    display="flex"
                    justifyContent="space-between"
                    alignItems="flex-start"
                    mb={1.5}
                  >
                    <Typography
                      variant="subtitle1"
                      component="h2"
                      sx={{ fontSize: '0.9rem', fontWeight: 600 }}
                    >
                      {formatDate(match.group.date)}
                    </Typography>
                    <Chip
                      label={match.status}
                      color={getStatusColor(match.status) as any}
                      size="small"
                      sx={{ fontSize: '0.7rem', height: '20px' }}
                    />
                  </Box>

                  {/* Accept/Reject buttons for SENT status */}
                  {match.status === 'SENT' && (
                    <Box display="flex" gap={1.5} mb={1.5}>
                      <Button
                        variant="outlined"
                        onClick={() => openRejectDialog(match.matchID)}
                        size="small"
                        sx={{
                          borderRadius: '16px',
                          borderColor: 'error.main',
                          color: 'error.main',
                          fontSize: '0.75rem',
                          py: 0.5,
                          '&:hover': {
                            borderColor: 'error.dark',
                            backgroundColor: 'error.light',
                            color: 'error.contrastText',
                          },
                          flex: 1,
                        }}
                      >
                        Reject
                      </Button>
                      <Button
                        variant="outlined"
                        onClick={() => openAcceptDialog(match.matchID)}
                        size="small"
                        sx={{
                          borderRadius: '16px',
                          borderColor: 'success.main',
                          color: 'success.main',
                          fontSize: '0.75rem',
                          py: 0.5,
                          '&:hover': {
                            borderColor: 'success.dark',
                            backgroundColor: 'success.light',
                            color: 'success.contrastText',
                          },
                          flex: 1,
                        }}
                      >
                        Accept
                      </Button>
                    </Box>
                  )}

                  <Box mb={1.5}>
                    <Typography
                      variant="body2"
                      color="text.secondary"
                      sx={{ fontSize: '0.8rem', mb: 0.5 }}
                    >
                      <strong>Time:</strong> {formatTime(match.group.time)}
                    </Typography>
                    <Typography
                      variant="body2"
                      color="text.secondary"
                      sx={{ fontSize: '0.8rem', mb: 0.5 }}
                    >
                      <strong>Location:</strong> {match.group.location}
                    </Typography>
                    <Box display="flex" alignItems="center" gap={1}>
                      <Typography
                        variant="body2"
                        color="text.secondary"
                        sx={{ fontSize: '0.8rem' }}
                      >
                        <strong>Participants:</strong>
                      </Typography>
                      <Chip
                        label={`${getConfirmedCount(match.group.userStatus)}/${match.group.userStatus.length} confirmed`}
                        size="small"
                        variant="outlined"
                        sx={{
                          borderColor: 'primary.main',
                          color: 'primary.main',
                          fontSize: '0.65rem',
                          height: '18px',
                        }}
                      />
                    </Box>
                  </Box>

                  <Divider sx={{ my: 1.5 }} />

                  <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
                    <Typography
                      variant="subtitle2"
                      gutterBottom
                      sx={{ fontSize: '0.8rem', fontWeight: 600 }}
                    >
                      Conversation Starters:
                    </Typography>
                    <Box sx={{ flex: 1, overflow: 'hidden' }}>
                      {match.group.conversationStarters.conversationsStarters
                        .slice(0, 2)
                        .map((starter, index) => (
                          <Typography
                            key={index}
                            variant="body2"
                            color="text.secondary"
                            sx={{
                              fontStyle: 'italic',
                              fontSize: '0.75rem',
                              mb: 0.5,
                              lineHeight: 1.3,
                              '&:last-child': { mb: 0 },
                            }}
                          >
                            "{starter.prompt}"
                          </Typography>
                        ))}
                      {match.group.conversationStarters.conversationsStarters.length > 2 && (
                        <Button
                          size="small"
                          onClick={() =>
                            handleViewAllStarters(
                              match.group.conversationStarters.conversationsStarters
                            )
                          }
                          sx={{
                            mt: 0.5,
                            fontSize: '0.7rem',
                            textTransform: 'none',
                            color: 'primary.main',
                            p: 0,
                            minWidth: 'auto',
                            '&:hover': {
                              backgroundColor: 'transparent',
                              textDecoration: 'underline',
                            },
                          }}
                        >
                          View all ({match.group.conversationStarters.conversationsStarters.length})
                        </Button>
                      )}
                    </Box>
                  </Box>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}

      {/* Conversation Starters Dialog */}
      <Dialog open={dialogOpen} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
        <DialogTitle sx={{ pb: 1 }}>
          <Typography variant="h6">Conversation Starters</Typography>
        </DialogTitle>
        <DialogContent>
          <List>
            {selectedStarters.map((starter, index) => (
              <ListItem key={index} sx={{ px: 0 }}>
                <ListItemText
                  primary={
                    <Typography
                      variant="body1"
                      sx={{
                        fontStyle: 'italic',
                        lineHeight: 1.5,
                      }}
                    >
                      "{starter.prompt}"
                    </Typography>
                  }
                />
              </ListItem>
            ))}
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>

      <MatchActionDialogs
        acceptDialogOpen={acceptDialogOpen}
        rejectDialogOpen={rejectDialogOpen}
        handleConfirmAccept={handleConfirmAccept}
        handleConfirmReject={handleConfirmReject}
        handleCloseAcceptDialog={handleCloseAcceptDialog}
        handleCloseRejectDialog={handleCloseRejectDialog}
        accepting={accepting}
        rejecting={rejecting}
      />

      {/* Snackbar for notifications */}
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{ width: '100%' }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </Box>
  );
};

export default Matches;
