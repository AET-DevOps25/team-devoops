import { renderHook, act } from '@testing-library/react';
import { useMatchActions } from '../../hooks/useMatchActions';
import { useMatchesService } from '../../services/matchesService';
import userEvent from '@testing-library/user-event';

// Mock the matches service
const mockAcceptMatch = jest.fn();
const mockRejectMatch = jest.fn();
const mockGetMatches = jest.fn();

jest.mock('../../services/matchesService', () => ({
  useMatchesService: () => ({
    getMatches: mockGetMatches,
    acceptMatch: mockAcceptMatch,
    rejectMatch: mockRejectMatch,
  }),
}));

describe('useMatchActions', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('initializes with default state', () => {
    const { result } = renderHook(() => useMatchActions());

    expect(result.current.acceptDialogOpen).toBe(false);
    expect(result.current.rejectDialogOpen).toBe(false);
    expect(result.current.selectedMatchId).toBe(null);
    expect(result.current.accepting).toBe(false);
    expect(result.current.rejecting).toBe(false);
    expect(result.current.snackbarOpen).toBe(false);
    expect(result.current.snackbarMessage).toBe('');
    expect(result.current.snackbarSeverity).toBe('success');
  });

  it('opens accept dialog correctly', () => {
    const { result } = renderHook(() => useMatchActions());

    act(() => {
      result.current.openAcceptDialog('match-123');
    });

    expect(result.current.acceptDialogOpen).toBe(true);
    expect(result.current.selectedMatchId).toBe('match-123');
  });

  it('opens reject dialog correctly', () => {
    const { result } = renderHook(() => useMatchActions());

    act(() => {
      result.current.openRejectDialog('match-456');
    });

    expect(result.current.rejectDialogOpen).toBe(true);
    expect(result.current.selectedMatchId).toBe('match-456');
  });

  it('closes accept dialog correctly', () => {
    const { result } = renderHook(() => useMatchActions());

    // Open dialog first
    act(() => {
      result.current.openAcceptDialog('match-123');
    });

    expect(result.current.acceptDialogOpen).toBe(true);

    // Close dialog
    act(() => {
      result.current.handleCloseAcceptDialog();
    });

    expect(result.current.acceptDialogOpen).toBe(false);
    expect(result.current.selectedMatchId).toBe(null);
  });

  it('closes reject dialog correctly', () => {
    const { result } = renderHook(() => useMatchActions());

    // Open dialog first
    act(() => {
      result.current.openRejectDialog('match-456');
    });

    expect(result.current.rejectDialogOpen).toBe(true);

    // Close dialog
    act(() => {
      result.current.handleCloseRejectDialog();
    });

    expect(result.current.rejectDialogOpen).toBe(false);
    expect(result.current.selectedMatchId).toBe(null);
  });

  it('handles accept match successfully', async () => {
    const user = userEvent.setup();
    const mockOnMatchStatusChange = jest.fn();
    const { result } = renderHook(() => useMatchActions({ onMatchStatusChange: mockOnMatchStatusChange }));

    // Mock successful API call
    mockAcceptMatch.mockResolvedValue(undefined);

    // Open accept dialog
    act(() => {
      result.current.openAcceptDialog('match-123');
    });

    expect(result.current.acceptDialogOpen).toBe(true);
    expect(result.current.selectedMatchId).toBe('match-123');

    // Confirm accept
    await act(async () => {
      await result.current.handleConfirmAccept();
    });

    expect(mockAcceptMatch).toHaveBeenCalledWith('match-123');
    expect(mockOnMatchStatusChange).toHaveBeenCalledWith('match-123', 'CONFIRMED');
    expect(result.current.acceptDialogOpen).toBe(false);
    expect(result.current.snackbarOpen).toBe(true);
    expect(result.current.snackbarMessage).toBe('Match accepted successfully');
    expect(result.current.snackbarSeverity).toBe('success');
  });

  it('handles reject match successfully', async () => {
    const user = userEvent.setup();
    const mockOnMatchStatusChange = jest.fn();
    const { result } = renderHook(() => useMatchActions({ onMatchStatusChange: mockOnMatchStatusChange }));

    // Mock successful API call
    mockRejectMatch.mockResolvedValue(undefined);

    // Open reject dialog
    act(() => {
      result.current.openRejectDialog('match-123');
    });

    expect(result.current.rejectDialogOpen).toBe(true);
    expect(result.current.selectedMatchId).toBe('match-123');

    // Confirm reject
    await act(async () => {
      await result.current.handleConfirmReject();
    });

    expect(mockRejectMatch).toHaveBeenCalledWith('match-123');
    expect(mockOnMatchStatusChange).toHaveBeenCalledWith('match-123', 'REJECTED');
    expect(result.current.rejectDialogOpen).toBe(false);
    expect(result.current.snackbarOpen).toBe(true);
    expect(result.current.snackbarMessage).toBe('Match rejected successfully');
    expect(result.current.snackbarSeverity).toBe('success');
  });

  it('handles accept match error', async () => {
    const user = userEvent.setup();
    const mockOnMatchStatusChange = jest.fn();
    const { result } = renderHook(() => useMatchActions({ onMatchStatusChange: mockOnMatchStatusChange }));

    // Mock failed API call
    mockAcceptMatch.mockRejectedValue(new Error('API Error'));

    // Open accept dialog
    act(() => {
      result.current.openAcceptDialog('match-123');
    });

    // Confirm accept
    await act(async () => {
      await result.current.handleConfirmAccept();
    });

    expect(mockAcceptMatch).toHaveBeenCalledWith('match-123');
    expect(mockOnMatchStatusChange).not.toHaveBeenCalled();
    expect(result.current.acceptDialogOpen).toBe(false);
    expect(result.current.snackbarOpen).toBe(true);
    expect(result.current.snackbarMessage).toBe('Failed to accept match');
    expect(result.current.snackbarSeverity).toBe('error');
  });

  it('handles reject match error', async () => {
    const user = userEvent.setup();
    const mockOnMatchStatusChange = jest.fn();
    const { result } = renderHook(() => useMatchActions({ onMatchStatusChange: mockOnMatchStatusChange }));

    // Mock failed API call
    mockRejectMatch.mockRejectedValue(new Error('API Error'));

    // Open reject dialog
    act(() => {
      result.current.openRejectDialog('match-456');
    });

    // Confirm reject
    await act(async () => {
      await result.current.handleConfirmReject();
    });

    expect(mockRejectMatch).toHaveBeenCalledWith('match-456');
    expect(mockOnMatchStatusChange).not.toHaveBeenCalled();
    expect(result.current.rejectDialogOpen).toBe(false);
    expect(result.current.snackbarOpen).toBe(true);
    expect(result.current.snackbarMessage).toBe('Failed to reject match');
    expect(result.current.snackbarSeverity).toBe('error');
  });

  it('closes snackbar correctly', async () => {
    const { result } = renderHook(() => useMatchActions());

    // First, we need to trigger a snackbar to open by doing an action
    act(() => {
      result.current.openAcceptDialog('match-123');
    });

    // Mock successful API call to trigger snackbar
    mockAcceptMatch.mockResolvedValue(undefined);

    // Trigger accept to open snackbar - properly await the async operation
    await act(async () => {
      await result.current.handleConfirmAccept();
    });

    expect(result.current.snackbarOpen).toBe(true);

    // Close snackbar
    act(() => {
      result.current.handleCloseSnackbar();
    });

    expect(result.current.snackbarOpen).toBe(false);
  });
}); 