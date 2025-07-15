import {
  Typography,
  Paper,
  Box,
  Chip,
  Grid,
  Button,
  Snackbar,
  Alert,
  useTheme,
  useMediaQuery,
  Link,
  Skeleton,
} from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import { useEffect, useState, useMemo } from 'react';
import { useMatchesService } from '../services/matchesService';
import { useMatchRequestService } from '../services/matchRequestService';
import { MatchRequest } from '../services/matchRequestService';
import { Match, MatchesResponse } from '../types/matches';
import { useAuth0 } from '@auth0/auth0-react';
import RegisterProfileDialog from './RegisterProfileDialog';
import { useUserID } from '../contexts/UserIDContext';
import { Link as RouterLink } from 'react-router-dom';
import { useMatchActions } from '../hooks/useMatchActions';
import MatchActionDialogs from './MatchActionDialogs';

const Dashboard = () => {
  const { user } = useAuth0();
  const userID = useUserID();
  const { getMatchRequests } = useMatchRequestService();
  const { getMatches } = useMatchesService();

  const [matches, setMatches] = useState<Match[]>([]);
  const [matchRequests, setMatchRequests] = useState<MatchRequest[]>([]);
  const [matchesLoading, setMatchesLoading] = useState(true);
  const [matchRequestsLoading, setMatchRequestsLoading] = useState(true);
  const [matchesError, setMatchesError] = useState<string | null>(null);
  const [matchesErrorIs404, setMatchesErrorIs404] = useState(false);
  const [matchRequestsError, setMatchRequestsError] = useState<string | null>(null);
  const [matchRequestsErrorIs404, setMatchRequestsErrorIs404] = useState(false);
  const [showRegisterDialog, setShowRegisterDialog] = useState(false);

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
      setMatches((prev) => prev.map((m) => (m.matchID === matchID ? { ...m, status } : m)));
    },
  });

  useEffect(() => {
    if (!userID) return;
    setMatchesLoading(true);
    setMatchesError(null);
    setMatchesErrorIs404(false);
    getMatches(userID)
      .then((matchesRes: MatchesResponse) => {
        setMatches(matchesRes.matches);
        setMatchesError(null);
        setMatchesErrorIs404(false);
      })
      .catch((err: any) => {
        if (err?.status === 404) {
          setMatchesError(null);
          setMatchesErrorIs404(true);
          setMatches([]);
        } else {
          setMatchesError('Failed to load matches.');
          setMatchesErrorIs404(false);
          setMatches([]);
          console.error('Error fetching matches:', err);
        }
      })
      .finally(() => setMatchesLoading(false));
  }, [userID]);

  useEffect(() => {
    if (!userID) return;
    setMatchRequestsLoading(true);
    setMatchRequestsError(null);
    setMatchRequestsErrorIs404(false);
    getMatchRequests(userID)
      .then((requests) => {
        setMatchRequests(requests);
        setMatchRequestsError(null);
        setMatchRequestsErrorIs404(false);
      })
      .catch((err: any) => {
        if (err?.status === 404) {
          setMatchRequestsError(null);
          setMatchRequestsErrorIs404(true);
          setMatchRequests([]);
        } else {
          setMatchRequestsError('Failed to load match requests.');
          setMatchRequestsErrorIs404(false);
          setMatchRequests([]);
          console.error('Error fetching match requests:', err);
        }
      })
      .finally(() => setMatchRequestsLoading(false));
  }, [userID]);

  // Show register dialog when userID is null and user is authenticated
  useEffect(() => {
    if (user && !userID) {
      setShowRegisterDialog(true);
    } else {
      setShowRegisterDialog(false);
    }
  }, [user, userID]);

  const theme = useTheme();
  const smDown = useMediaQuery(theme.breakpoints.down('sm'));

  // Unanswered meetings (status SENT)
  const unansweredMatches = useMemo(() => matches.filter((m) => m.status === 'SENT'), [matches]);

  // Upcoming meetings (status CONFIRMED, within next 2 days)
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

  // Last 5 match requests
  const last5PendingRequests = useMemo(() => {
    return matchRequests
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

  // Skeleton component for match boxes
  const MatchBoxSkeleton = () => (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'flex-start',
        justifyContent: 'space-between',
        p: 1.5,
        borderRadius: 2,
        bgcolor: 'background.paper',
        minHeight: 130,
        height: '100%',
        boxShadow: 1,
      }}
    >
      <Skeleton variant="text" width="60%" height={20} />
      <Skeleton variant="text" width="40%" height={16} />
      <Skeleton variant="text" width="50%" height={16} />
      <Box sx={{ flexGrow: 1 }} />
      <Box sx={{ display: 'flex', gap: 1, width: '100%', mt: 'auto' }}>
        <Skeleton variant="rectangular" width="50%" height={32} sx={{ borderRadius: 1 }} />
        <Skeleton variant="rectangular" width="50%" height={32} sx={{ borderRadius: 1 }} />
      </Box>
    </Box>
  );

  // Skeleton component for upcoming match boxes
  const UpcomingMatchBoxSkeleton = () => (
    <Box
      sx={{
        display: 'flex',
        flexDirection: { xs: 'column', md: 'row' },
        alignItems: { xs: 'flex-start', md: 'center' },
        justifyContent: 'space-between',
        gap: 2,
        p: 1.5,
        borderRadius: 2,
        bgcolor: 'background.paper',
        height: '100%',
        boxShadow: 1,
      }}
    >
      <Skeleton variant="text" width="60%" height={20} />
      <Skeleton variant="text" width="40%" height={16} />
      <Skeleton variant="text" width="50%" height={16} />
      <Skeleton variant="rectangular" width={80} height={24} sx={{ borderRadius: 1 }} />
    </Box>
  );

  // Skeleton component for match request boxes
  const MatchRequestBoxSkeleton = () => (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'flex-start',
        justifyContent: 'space-between',
        p: 1.5,
        borderRadius: 2,
        bgcolor: 'background.paper',
        minHeight: 110,
        height: '100%',
        boxShadow: 1,
      }}
    >
      <Skeleton variant="text" width="60%" height={20} />
      <Skeleton variant="text" width="40%" height={16} />
      <Skeleton variant="text" width="50%" height={16} />
      <Box sx={{ flexGrow: 1 }} />
      <Skeleton variant="rectangular" width={60} height={20} sx={{ borderRadius: 1 }} />
    </Box>
  );

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
            {matchesLoading ? (
              <Grid container spacing={2}>
                {[1].map((index) => (
                  <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                    <MatchBoxSkeleton />
                  </Grid>
                ))}
              </Grid>
            ) : matchesErrorIs404 ? (
              <Typography variant="body2">No matches found.</Typography>
            ) : matchesError ? (
              <Typography variant="body2">{matchesError}</Typography>
            ) : unansweredMatches.length === 0 ? (
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
                        bgcolor: 'background.paper',
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
                          onClick={() => openRejectDialog(match.matchID)}
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
                          onClick={() => openAcceptDialog(match.matchID)}
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
            {matchesLoading ? (
              <Grid container spacing={2}>
                {[1, 2].map((index) => (
                  <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                    <UpcomingMatchBoxSkeleton />
                  </Grid>
                ))}
              </Grid>
            ) : matchesErrorIs404 ? (
              <Typography variant="body2">No matches found.</Typography>
            ) : matchesError ? (
              <Typography variant="body2">{matchesError}</Typography>
            ) : upcomingMatches.length === 0 ? (
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
                        bgcolor: 'background.paper',
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
              {matchesLoading ? (
                <Grid container spacing={2}>
                  {[1].map((index) => (
                    <Grid item xs={12} sm={6} md={12} lg={12} key={index}>
                      <MatchBoxSkeleton />
                    </Grid>
                  ))}
                </Grid>
              ) : matchesErrorIs404 ? (
                <Typography variant="body2">No matches found.</Typography>
              ) : matchesError ? (
                <Typography variant="body2">{matchesError}</Typography>
              ) : unansweredMatches.length === 0 ? (
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
                          bgcolor: 'background.paper',
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
                            onClick={() => openRejectDialog(match.matchID)}
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
                            onClick={() => openAcceptDialog(match.matchID)}
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
              {matchesLoading ? (
                <Grid container spacing={2}>
                  {[1, 2].map((index) => (
                    <Grid item xs={12} sm={6} md={12} lg={12} key={index}>
                      <UpcomingMatchBoxSkeleton />
                    </Grid>
                  ))}
                </Grid>
              ) : matchesErrorIs404 ? (
                <Typography variant="body2">No matches found.</Typography>
              ) : matchesError ? (
                <Typography variant="body2">{matchesError}</Typography>
              ) : upcomingMatches.length === 0 ? (
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
                          bgcolor: 'background.paper',
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
            to="/matchrequests"
            variant="body2"
            color="primary"
            underline="hover"
            sx={{ ml: 2 }}
          >
            View All Match Requests
          </Link>
        </Box>
        {matchRequestsLoading ? (
          <Grid container spacing={2}>
            {[1, 2, 3].map((index) => (
              <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                <MatchRequestBoxSkeleton />
              </Grid>
            ))}
          </Grid>
        ) : matchRequestsErrorIs404 ? (
          <Typography variant="body2">No match requests found.</Typography>
        ) : matchRequestsError ? (
          <Typography variant="body2">{matchRequestsError}</Typography>
        ) : last5PendingRequests.length === 0 ? (
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
                    bgcolor: 'background.paper',
                    minHeight: 110,
                    height: '100%',
                    boxShadow: 1,
                  }}
                >
                  <Typography variant="body2" sx={{ fontWeight: 600, mb: 0.5 }}>
                    {formatDate(req.date)}
                  </Typography>
                  <Typography variant="body2" sx={{ color: 'text.secondary', mb: 0.5 }}>
                    {req.timeslot && req.timeslot.length > 0 ? formatTime(req.timeslot[0]) : 'No timeslot'}
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
      <RegisterProfileDialog
        open={showRegisterDialog}
        email={user?.email || ''}
        authID={user?.sub || ''}
        onSuccess={() => {
          setShowRegisterDialog(false);
        }}
      />
    </Box>
  );
};

export default Dashboard;
