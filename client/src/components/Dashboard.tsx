import {
  Typography,
  Paper,
  Box,
  Chip,
  Grid,
  CircularProgress,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Snackbar,
  Alert,
  useTheme,
  useMediaQuery,
  Link,
} from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import { useEffect, useState, useMemo } from 'react';
import { matchesService } from '../services/matchesService';
import { getMatchRequests, MatchRequest } from '../services/matchRequestService';
import { Match, MatchesResponse } from '../types/matches';
import { useAuth0 } from '@auth0/auth0-react';
import { Link as RouterLink } from 'react-router-dom';

const Dashboard = () => {
  const { user } = useAuth0();
  const userId = user?.sub || '2c3821b8-1cdb-4b77-bcd8-a1da701e46aa'; // fallback to mock userId

  const [matches, setMatches] = useState<Match[]>([]);
  const [matchRequests, setMatchRequests] = useState<MatchRequest[]>([]);
  const [loading, setLoading] = useState(true);
  const [acceptDialogOpen, setAcceptDialogOpen] = useState(false);
  const [rejectDialogOpen, setRejectDialogOpen] = useState(false);
  const [selectedMatchId, setSelectedMatchId] = useState<string | null>(null);
  const [accepting, setAccepting] = useState(false);
  const [rejecting, setRejecting] = useState(false);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState<'success' | 'error'>('success');

  const theme = useTheme();
  const smDown = useMediaQuery(theme.breakpoints.down('sm'));

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        // Use mock for now, replace with: await matchesService.getMatches(userId)
        const matchesRes: MatchesResponse = await matchesService.getMockMatches();
        setMatches(matchesRes.matches);
        const requests = await getMatchRequests(userId);
        setMatchRequests(requests);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [userId]);

  // 1. Unanswered meetings (status SENT)
  const unansweredMatches = useMemo(() => matches.filter((m) => m.status === 'SENT'), [matches]);

  // 2. Upcoming meetings (status CONFIRMED, within next 2 days)
  const upcomingMatches = useMemo(() => {
    const now = new Date();
    const in2Days = new Date(now);
    in2Days.setDate(now.getDate() + 2);
    return matches.filter((m) => {
      if (m.status !== 'CONFIRMED') return false;
      const date = new Date(m.group.date);
      return date >= now && date <= in2Days;
    });
  }, [matches]);

  // 3. Last 5 pending match requests
  const last5PendingRequests = useMemo(() => {
    return matchRequests
      .filter((r) => r.status === 'PENDING')
      .sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())
      .slice(0, 5);
  }, [matchRequests]);

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      weekday: 'short',
      month: 'short',
      day: 'numeric',
    });
  };

  const formatTime = (timeSlot: number) => {
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

  // Accept/Reject logic
  const handleAccept = (matchId: string) => {
    setSelectedMatchId(matchId);
    setAcceptDialogOpen(true);
  };
  const handleConfirmAccept = async () => {
    if (!selectedMatchId) return;
    try {
      setAccepting(true);
      await new Promise((resolve) => setTimeout(resolve, 1000)); // Simulate API call
      setMatches((prev) =>
        prev.map((m) => (m.matchID === selectedMatchId ? { ...m, status: 'CONFIRMED' } : m))
      );
      setSnackbarMessage('Match accepted successfully');
      setSnackbarSeverity('success');
      setSnackbarOpen(true);
    } catch (err) {
      setSnackbarMessage('Failed to accept match');
      setSnackbarSeverity('error');
      setSnackbarOpen(true);
    } finally {
      setAccepting(false);
      setAcceptDialogOpen(false);
      setSelectedMatchId(null);
    }
  };
  const handleReject = (matchId: string) => {
    setSelectedMatchId(matchId);
    setRejectDialogOpen(true);
  };
  const handleConfirmReject = async () => {
    if (!selectedMatchId) return;
    try {
      setRejecting(true);
      await new Promise((resolve) => setTimeout(resolve, 1000)); // Simulate API call
      setMatches((prev) =>
        prev.map((m) => (m.matchID === selectedMatchId ? { ...m, status: 'REJECTED' } : m))
      );
      setSnackbarMessage('Match rejected successfully');
      setSnackbarSeverity('success');
      setSnackbarOpen(true);
    } catch (err) {
      setSnackbarMessage('Failed to reject match');
      setSnackbarSeverity('error');
      setSnackbarOpen(true);
    } finally {
      setRejecting(false);
      setRejectDialogOpen(false);
      setSelectedMatchId(null);
    }
  };
  const handleCloseAcceptDialog = () => {
    setAcceptDialogOpen(false);
    setSelectedMatchId(null);
  };
  const handleCloseRejectDialog = () => {
    setRejectDialogOpen(false);
    setSelectedMatchId(null);
  };
  const handleCloseSnackbar = () => {
    setSnackbarOpen(false);
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="300px">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box>
      {/* Responsive layout for Unanswered and Upcoming Meetings */}
      {smDown ? (
        <>
          <Paper sx={{ p: 2, mb: 4 }} elevation={3}>
            <Box
              sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 1 }}
            >
              <Typography variant="h6" gutterBottom>
                Unanswered Meetings
              </Typography>
              <Link
                component={RouterLink}
                to="/matches"
                variant="body2"
                color="primary"
                underline="hover"
                sx={{ ml: 2 }}
              >
                View All Matches
              </Link>
            </Box>
            {unansweredMatches.length === 0 ? (
              <Typography variant="body2">No unanswered meetings.</Typography>
            ) : (
              <Grid container spacing={2}>
                {unansweredMatches.map((match) => (
                  <Grid item xs={12} sm={6} md={4} lg={3} key={match.matchID}>
                    <Box
                      sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'flex-start',
                        justifyContent: 'space-between',
                        p: 1.5,
                        borderRadius: 2,
                        bgcolor: 'grey.100',
                        minHeight: 130,
                        height: '100%',
                        boxShadow: 1,
                      }}
                    >
                      <Typography variant="body2" sx={{ fontWeight: 600, mb: 0.5 }}>
                        {formatDate(match.group.date)}
                      </Typography>
                      <Typography variant="body2" sx={{ color: 'text.secondary', mb: 0.5 }}>
                        {formatTime(match.group.time)}
                      </Typography>
                      <Typography variant="body2" sx={{ color: 'text.secondary', mb: 1 }}>
                        {match.group.location}
                      </Typography>
                      <Box sx={{ flexGrow: 1 }} />
                      <Box
                        sx={{
                          display: 'flex',
                          gap: 1,
                          width: '100%',
                          flexWrap: 'wrap',
                          mt: 'auto',
                        }}
                      >
                        <Button
                          variant="outlined"
                          color="error"
                          size="small"
                          startIcon={<CancelIcon />}
                          onClick={() => handleReject(match.matchID)}
                          disabled={rejecting || accepting}
                          sx={{ minWidth: 70, flex: 1 }}
                        >
                          Reject
                        </Button>
                        <Button
                          variant="outlined"
                          color="success"
                          size="small"
                          startIcon={<CheckCircleIcon />}
                          onClick={() => handleAccept(match.matchID)}
                          disabled={accepting || rejecting}
                          sx={{ minWidth: 70, flex: 1 }}
                        >
                          Accept
                        </Button>
                      </Box>
                    </Box>
                  </Grid>
                ))}
              </Grid>
            )}
          </Paper>
          <Paper sx={{ p: 2, mb: 4 }} elevation={3}>
            <Box
              sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 1 }}
            >
              <Typography variant="h6" gutterBottom>
                Upcoming Meetings
              </Typography>
              <Link
                component={RouterLink}
                to="/matches"
                variant="body2"
                color="primary"
                underline="hover"
                sx={{ ml: 2 }}
              >
                View All Matches
              </Link>
            </Box>
            {upcomingMatches.length === 0 ? (
              <Typography variant="body2">No upcoming meetings.</Typography>
            ) : (
              <Grid container spacing={2}>
                {upcomingMatches.map((match) => (
                  <Grid item xs={12} sm={6} md={4} lg={3} key={match.matchID}>
                    <Box
                      sx={{
                        display: 'flex',
                        flexDirection: { xs: 'column', md: 'row' },
                        alignItems: { xs: 'flex-start', md: 'center' },
                        justifyContent: 'space-between',
                        gap: 2,
                        p: 1.5,
                        borderRadius: 2,
                        bgcolor: 'grey.100',
                        minHeight: 110,
                        height: '100%',
                        boxShadow: 1,
                      }}
                    >
                      <Typography variant="body2" sx={{ fontWeight: 600, mb: { xs: 0.5, md: 0 } }}>
                        {formatDate(match.group.date)}
                      </Typography>
                      <Typography
                        variant="body2"
                        sx={{ color: 'text.secondary', mb: { xs: 0.5, md: 0 } }}
                      >
                        {formatTime(match.group.time)}
                      </Typography>
                      <Typography
                        variant="body2"
                        sx={{ color: 'text.secondary', mb: { xs: 1, md: 0 }, flex: 1 }}
                      >
                        {match.group.location}
                      </Typography>
                      <Chip
                        icon={<CheckCircleIcon color="success" />}
                        label="Confirmed"
                        size="small"
                        sx={{ fontWeight: 500, mt: { xs: 'auto', md: 0 } }}
                      />
                    </Box>
                  </Grid>
                ))}
              </Grid>
            )}
          </Paper>
        </>
      ) : (
        <Grid container spacing={4} alignItems="stretch" sx={{ mb: 6 }}>
          <Grid item xs={12} md={6} sx={{ display: 'flex' }}>
            <Paper
              sx={{ p: 2, width: '100%', height: '100%', display: 'flex', flexDirection: 'column' }}
              elevation={3}
            >
              <Box
                sx={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'center',
                  mb: 1,
                }}
              >
                <Typography variant="h6" gutterBottom>
                  Unanswered Meetings
                </Typography>
                <Link
                  component={RouterLink}
                  to="/matches"
                  variant="body2"
                  color="primary"
                  underline="hover"
                  sx={{ ml: 2 }}
                >
                  View All Matches
                </Link>
              </Box>
              {unansweredMatches.length === 0 ? (
                <Typography variant="body2">No unanswered meetings.</Typography>
              ) : (
                <Grid container spacing={2}>
                  {unansweredMatches.map((match) => (
                    <Grid item xs={12} sm={6} md={12} lg={12} key={match.matchID}>
                      <Box
                        sx={{
                          display: 'flex',
                          flexDirection: 'column',
                          alignItems: 'flex-start',
                          justifyContent: 'space-between',
                          p: 1.5,
                          borderRadius: 2,
                          bgcolor: 'grey.100',
                          minHeight: 130,
                          height: '100%',
                          boxShadow: 1,
                        }}
                      >
                        <Typography variant="body2" sx={{ fontWeight: 600, mb: 0.5 }}>
                          {formatDate(match.group.date)}
                        </Typography>
                        <Typography variant="body2" sx={{ color: 'text.secondary', mb: 0.5 }}>
                          {formatTime(match.group.time)}
                        </Typography>
                        <Typography variant="body2" sx={{ color: 'text.secondary', mb: 1 }}>
                          {match.group.location}
                        </Typography>
                        <Box sx={{ flexGrow: 1 }} />
                        <Box
                          sx={{
                            display: 'flex',
                            gap: 1,
                            width: '100%',
                            flexWrap: 'wrap',
                            mt: 'auto',
                          }}
                        >
                          <Button
                            variant="outlined"
                            color="error"
                            size="small"
                            startIcon={<CancelIcon />}
                            onClick={() => handleReject(match.matchID)}
                            disabled={rejecting || accepting}
                            sx={{ minWidth: 70, flex: 1 }}
                          >
                            Reject
                          </Button>
                          <Button
                            variant="outlined"
                            color="success"
                            size="small"
                            startIcon={<CheckCircleIcon />}
                            onClick={() => handleAccept(match.matchID)}
                            disabled={accepting || rejecting}
                            sx={{ minWidth: 70, flex: 1 }}
                          >
                            Accept
                          </Button>
                        </Box>
                      </Box>
                    </Grid>
                  ))}
                </Grid>
              )}
            </Paper>
          </Grid>
          <Grid item xs={12} md={6} sx={{ display: 'flex' }}>
            <Paper
              sx={{ p: 2, width: '100%', height: '100%', display: 'flex', flexDirection: 'column' }}
              elevation={3}
            >
              <Box
                sx={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'center',
                  mb: 1,
                }}
              >
                <Typography variant="h6" gutterBottom>
                  Upcoming Meetings
                </Typography>
                <Link
                  component={RouterLink}
                  to="/matches"
                  variant="body2"
                  color="primary"
                  underline="hover"
                  sx={{ ml: 2 }}
                >
                  View All Matches
                </Link>
              </Box>
              {upcomingMatches.length === 0 ? (
                <Typography variant="body2">No upcoming meetings.</Typography>
              ) : (
                <Grid container spacing={2}>
                  {upcomingMatches.map((match) => (
                    <Grid item xs={12} sm={6} md={12} lg={12} key={match.matchID}>
                      <Box
                        sx={{
                          display: 'flex',
                          flexDirection: { xs: 'column', md: 'row' },
                          alignItems: { xs: 'flex-start', md: 'center' },
                          justifyContent: 'space-between',
                          gap: 2,
                          p: 1.5,
                          borderRadius: 2,
                          bgcolor: 'grey.100',
                          height: '100%',
                          boxShadow: 1,
                        }}
                      >
                        <Typography
                          variant="body2"
                          sx={{ fontWeight: 600, mb: { xs: 0.5, md: 0 } }}
                        >
                          {formatDate(match.group.date)}
                        </Typography>
                        <Typography
                          variant="body2"
                          sx={{ color: 'text.secondary', mb: { xs: 0.5, md: 0 } }}
                        >
                          {formatTime(match.group.time)}
                        </Typography>
                        <Typography
                          variant="body2"
                          sx={{ color: 'text.secondary', mb: { xs: 1, md: 0 }, flex: 1 }}
                        >
                          {match.group.location}
                        </Typography>
                        <Chip
                          icon={<CheckCircleIcon color="success" />}
                          label="Confirmed"
                          size="small"
                          sx={{ fontWeight: 500, mt: { xs: 'auto', md: 0 } }}
                        />
                      </Box>
                    </Grid>
                  ))}
                </Grid>
              )}
            </Paper>
          </Grid>
        </Grid>
      )}
      {/* Accept Dialog */}
      <Dialog open={acceptDialogOpen} onClose={handleCloseAcceptDialog}>
        <DialogTitle>Accept Match</DialogTitle>
        <DialogContent>Are you sure you want to accept this match?</DialogContent>
        <DialogActions>
          <Button onClick={handleCloseAcceptDialog} disabled={accepting}>
            Cancel
          </Button>
          <Button onClick={handleConfirmAccept} color="success" disabled={accepting} autoFocus>
            {accepting ? 'Accepting...' : 'Accept'}
          </Button>
        </DialogActions>
      </Dialog>
      {/* Reject Dialog */}
      <Dialog open={rejectDialogOpen} onClose={handleCloseRejectDialog}>
        <DialogTitle>Reject Match</DialogTitle>
        <DialogContent>Are you sure you want to reject this match?</DialogContent>
        <DialogActions>
          <Button onClick={handleCloseRejectDialog} disabled={rejecting}>
            Cancel
          </Button>
          <Button onClick={handleConfirmReject} color="error" disabled={rejecting} autoFocus>
            {rejecting ? 'Rejecting...' : 'Reject'}
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{ width: '100%' }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
      {/* Last 5 Pending Match Requests */}
      <Paper sx={{ p: 2, mt: { xs: 2, md: 4 } }} elevation={3}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 1 }}>
          <Typography variant="h6" gutterBottom>
            Last 5 Pending Match Requests
          </Typography>
          <Link
            component={RouterLink}
            to="/preferences"
            variant="body2"
            color="primary"
            underline="hover"
            sx={{ ml: 2 }}
          >
            View All Match Requests
          </Link>
        </Box>
        {last5PendingRequests.length === 0 ? (
          <Typography variant="body2">No pending match requests.</Typography>
        ) : (
          <Grid container spacing={2}>
            {last5PendingRequests.map((req) => (
              <Grid item xs={12} sm={6} md={4} lg={3} key={req.requestID}>
                <Box
                  sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'flex-start',
                    justifyContent: 'space-between',
                    p: 1.5,
                    borderRadius: 2,
                    bgcolor: 'grey.100',
                    minHeight: 110,
                    height: '100%',
                    boxShadow: 1,
                  }}
                >
                  <Typography variant="body2" sx={{ fontWeight: 600, mb: 0.5 }}>
                    {formatDate(req.date)}
                  </Typography>
                  <Typography variant="body2" sx={{ color: 'text.secondary', mb: 0.5 }}>
                    {formatTime(req.timeslots?.[0])}
                  </Typography>
                  <Typography variant="body2" sx={{ color: 'text.secondary', mb: 1 }}>
                    {req.location}
                  </Typography>
                  <Box sx={{ flexGrow: 1 }} />
                  <Chip
                    label="Pending"
                    color="warning"
                    size="small"
                    sx={{ mt: 'auto', fontWeight: 500 }}
                  />
                </Box>
              </Grid>
            ))}
          </Grid>
        )}
      </Paper>
    </Box>
  );
};

export default Dashboard;
