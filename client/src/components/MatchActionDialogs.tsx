import React from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from '@mui/material';

interface MatchActionDialogsProps {
  acceptDialogOpen: boolean;
  rejectDialogOpen: boolean;
  handleConfirmAccept: () => void;
  handleConfirmReject: () => void;
  handleCloseAcceptDialog: () => void;
  handleCloseRejectDialog: () => void;
  accepting: boolean;
  rejecting: boolean;
}

const MatchActionDialogs: React.FC<MatchActionDialogsProps> = ({
  acceptDialogOpen,
  rejectDialogOpen,
  handleConfirmAccept,
  handleConfirmReject,
  handleCloseAcceptDialog,
  handleCloseRejectDialog,
  accepting,
  rejecting,
}) => {
  return (
    <>
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
    </>
  );
};

export default MatchActionDialogs;
