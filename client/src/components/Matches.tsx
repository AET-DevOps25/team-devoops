import { useState, useEffect } from 'react';
import {
  Typography,
  Paper,
  Box,
  CircularProgress,
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
} from '@mui/material';
import { matchesService, MatchesServiceError } from '../services/matchesService';
import { MatchesResponse, Match, MatchStatus, ConversationStarter } from '../types/matches';

const Matches = () => {
  const [matches, setMatches] = useState<MatchesResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [selectedStarters, setSelectedStarters] = useState<ConversationStarter[]>([]);
  const [rejectDialogOpen, setRejectDialogOpen] = useState(false);
  const [selectedMatchId, setSelectedMatchId] = useState<string | null>(null);
  const [rejecting, setRejecting] = useState(false);
  const [acceptDialogOpen, setAcceptDialogOpen] = useState(false);
  const [accepting, setAccepting] = useState(false);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState<'success' | 'error'>('success');

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        setLoading(true);
        setError(null);

        // For development, use mock data
        // In production, replace with: const data = await matchesService.getMatches(userId);
        const data = await matchesService.getMockMatches();
        setMatches(data);
      } catch (err) {
        const serviceError = err as MatchesServiceError;
        setError(serviceError.message || 'Failed to load matches');
      } finally {
        setLoading(false);
      }
    };

    fetchMatches();
  }, []);

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

  const handleAccept = (matchId: string) => {
    setSelectedMatchId(matchId);
    setAcceptDialogOpen(true);
  };

  const handleConfirmAccept = async () => {
    if (!selectedMatchId) return;

    try {
      setAccepting(true);

      // For development, simulate API call
      // In production, replace with: await matchesService.acceptMatch(selectedMatchId);
      await new Promise((resolve) => setTimeout(resolve, 1000)); // Simulate API delay

      // Update local state to reflect the acceptance
      if (matches) {
        const updatedMatches = {
          ...matches,
          matches: matches.matches.map((match) =>
            match.matchID === selectedMatchId ? { ...match, status: 'CONFIRMED' as const } : match
          ),
        };
        setMatches(updatedMatches);
      }

      setSnackbarMessage('Match accepted successfully');
      setSnackbarSeverity('success');
      setSnackbarOpen(true);
    } catch (err) {
      const serviceError = err as MatchesServiceError;
      setSnackbarMessage(serviceError.message || 'Failed to accept match');
      setSnackbarSeverity('error');
      setSnackbarOpen(true);
    } finally {
      setAccepting(false);
      setAcceptDialogOpen(false);
      setSelectedMatchId(null);
    }
  };

  const handleCloseAcceptDialog = () => {
    setAcceptDialogOpen(false);
    setSelectedMatchId(null);
  };

  const handleReject = (matchId: string) => {
    setSelectedMatchId(matchId);
    setRejectDialogOpen(true);
  };

  const handleConfirmReject = async () => {
    if (!selectedMatchId) return;

    try {
      setRejecting(true);

      // For development, simulate API call
      // In production, replace with: await matchesService.rejectMatch(selectedMatchId);
      await new Promise((resolve) => setTimeout(resolve, 1000)); // Simulate API delay

      // Update local state to reflect the rejection
      if (matches) {
        const updatedMatches = {
          ...matches,
          matches: matches.matches.map((match) =>
            match.matchID === selectedMatchId ? { ...match, status: 'REJECTED' as const } : match
          ),
        };
        setMatches(updatedMatches);
      }

      setSnackbarMessage('Match rejected successfully');
      setSnackbarSeverity('success');
      setSnackbarOpen(true);
    } catch (err) {
      const serviceError = err as MatchesServiceError;
      setSnackbarMessage(serviceError.message || 'Failed to reject match');
      setSnackbarSeverity('error');
      setSnackbarOpen(true);
    } finally {
      setRejecting(false);
      setRejectDialogOpen(false);
      setSelectedMatchId(null);
    }
  };

  const handleCloseRejectDialog = () => {
    setRejectDialogOpen(false);
    setSelectedMatchId(null);
  };

  const handleViewAllStarters = (starters: ConversationStarter[]) => {
    setSelectedStarters(starters);
    setDialogOpen(true);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
    setSelectedStarters([]);
  };

  const handleCloseSnackbar = () => {
    setSnackbarOpen(false);
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="300px">
        <CircularProgress size="small" />
      </Box>
    );
  }

  if (error) {
    return (
      <Box>
        <Typography variant="h5" component="h1" gutterBottom>
          Matches
        </Typography>
        <Alert severity="error" sx={{ mt: 1 }}>
          {error}
        </Alert>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h5" component="h1" gutterBottom>
        Matches
      </Typography>

      {matches?.matches.length === 0 ? (
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
                        onClick={() => handleReject(match.matchID)}
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
                        onClick={() => handleAccept(match.matchID)}
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

      {/* Accept Confirmation Dialog */}
      <Dialog open={acceptDialogOpen} onClose={handleCloseAcceptDialog}>
        <DialogTitle>Accept Match</DialogTitle>
        <DialogContent>
          Are you sure you want to accept this lunch match? This action cannot be undone.
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseAcceptDialog} disabled={accepting}>
            Cancel
          </Button>
          <Button
            onClick={handleConfirmAccept}
            color="success"
            variant="contained"
            disabled={accepting}
          >
            {accepting ? <CircularProgress size={16} /> : 'Accept'}
          </Button>
        </DialogActions>
      </Dialog>

      {/* Reject Confirmation Dialog */}
      <Dialog open={rejectDialogOpen} onClose={handleCloseRejectDialog}>
        <DialogTitle>Reject Match</DialogTitle>
        <DialogContent>
          Are you sure you want to reject this lunch match? This action cannot be undone.
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseRejectDialog} disabled={rejecting}>
            Cancel
          </Button>
          <Button
            onClick={handleConfirmReject}
            color="error"
            variant="contained"
            disabled={rejecting}
          >
            {rejecting ? <CircularProgress size={16} /> : 'Reject'}
          </Button>
        </DialogActions>
      </Dialog>

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
