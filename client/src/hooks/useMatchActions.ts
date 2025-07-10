import { useState } from 'react';
import { useMatchesService } from '../services/matchesService';

export type SnackbarSeverity = 'success' | 'error';

interface UseMatchActionsOptions {
  onMatchStatusChange?: (matchID: string, status: 'CONFIRMED' | 'REJECTED') => void;
}

export function useMatchActions(options?: UseMatchActionsOptions) {
  const { acceptMatch, rejectMatch } = useMatchesService();
  const [acceptDialogOpen, setAcceptDialogOpen] = useState(false);
  const [rejectDialogOpen, setRejectDialogOpen] = useState(false);
  const [selectedMatchId, setSelectedMatchId] = useState<string | null>(null);
  const [accepting, setAccepting] = useState(false);
  const [rejecting, setRejecting] = useState(false);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState<SnackbarSeverity>('success');

  // Open dialogs
  const openAcceptDialog = (matchId: string) => {
    setSelectedMatchId(matchId);
    setAcceptDialogOpen(true);
  };
  const openRejectDialog = (matchId: string) => {
    setSelectedMatchId(matchId);
    setRejectDialogOpen(true);
  };

  // Accept/Reject handlers
  const handleConfirmAccept = async () => {
    if (!selectedMatchId) return;
    try {
      setAccepting(true);
      await acceptMatch(selectedMatchId);
      setSnackbarMessage('Match accepted successfully');
      setSnackbarSeverity('success');
      setSnackbarOpen(true);
      options?.onMatchStatusChange?.(selectedMatchId, 'CONFIRMED');
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
  const handleConfirmReject = async () => {
    if (!selectedMatchId) return;
    try {
      setRejecting(true);
      await rejectMatch(selectedMatchId);
      setSnackbarMessage('Match rejected successfully');
      setSnackbarSeverity('success');
      setSnackbarOpen(true);
      options?.onMatchStatusChange?.(selectedMatchId, 'REJECTED');
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

  // Dialog close handlers
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

  return {
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
    selectedMatchId,
  };
} 