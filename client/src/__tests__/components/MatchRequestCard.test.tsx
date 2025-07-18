import React from 'react';
import { screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { render } from '../utils/test-utils';
import MatchRequestCard from '../../components/MatchRequestCard';
import { mockMatchRequest, mockOnDelete } from '../utils/mocks';

// Mock the image imports with the correct paths
jest.mock('../../assets/mensa_garching.jpg', () => 'mensa-garching-mock.jpg', { virtual: true });
jest.mock('../../assets/mensa_arcisstr.jpg', () => 'mensa-arcisstr-mock.jpg', { virtual: true });

describe('MatchRequestCard', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders match request information correctly', () => {
    render(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={mockOnDelete} />);

    // Check if basic information is rendered
    expect(screen.getByTestId('match-location')).toHaveTextContent('Mensa Garching');
    expect(screen.getByTestId('match-status')).toHaveTextContent('PENDING');
    expect(screen.getByTestId('match-date')).toHaveTextContent('Mon, Jan 15');
    expect(screen.getByTestId('match-timeslots')).toHaveTextContent('12:00-12:45');

    // Check if the mensa image is displayed
    const image = screen.getByAltText('GARCHING');
    expect(image).toBeInTheDocument();

    // Check preferences are displayed correctly
    expect(screen.getByTestId('preference-same-degree')).toBeInTheDocument();
    expect(screen.getByTestId('preference-any-age')).toBeInTheDocument();
    expect(screen.getByTestId('preference-same-gender')).toBeInTheDocument();
  });

  it('handles delete action with confirmation dialog', async () => {
    const user = userEvent.setup();

    render(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={mockOnDelete} />);

    // Test delete functionality
    const cancelButton = screen.getByTestId('cancel-request-button');
    await user.click(cancelButton);

    // Should show confirmation dialog
    expect(screen.getByTestId('confirm-dialog')).toBeInTheDocument();
    expect(screen.getByTestId('confirm-dialog-title')).toHaveTextContent('Cancel Match Request');

    const confirmButton = screen.getByTestId('confirm-delete-button');
    await user.click(confirmButton);

    expect(mockOnDelete).toHaveBeenCalledWith('123');
  });

  it('closes confirmation dialog when close button is clicked', async () => {
    const user = userEvent.setup();

    render(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={mockOnDelete} />);

    // Open confirmation dialog
    const cancelButton = screen.getByTestId('cancel-request-button');
    await user.click(cancelButton);

    // Close dialog
    const closeButton = screen.getByTestId('close-dialog-button');
    await user.click(closeButton);

    // Dialog should be closed (wait for it to disappear)
    await waitFor(() => {
      expect(screen.queryByTestId('confirm-dialog-title')).not.toBeInTheDocument();
    });
    expect(mockOnDelete).not.toHaveBeenCalled();
  });

  it('displays different status colors correctly', () => {
    const matchedRequest = { ...mockMatchRequest, status: 'MATCHED' as const };
    const { rerender } = render(<MatchRequestCard matchRequest={matchedRequest} onDelete={mockOnDelete} />);

    expect(screen.getByTestId('match-status')).toHaveTextContent('MATCHED');

    const unmatchedRequest = { ...mockMatchRequest, status: 'UNMATCHABLE' as const };
    rerender(<MatchRequestCard matchRequest={unmatchedRequest} onDelete={mockOnDelete} />);

    expect(screen.getByTestId('match-status')).toHaveTextContent('UNMATCHABLE');
  });

  it('displays different location correctly', () => {
    const arcisstrRequest = { ...mockMatchRequest, location: 'ARCISSTR' };
    render(<MatchRequestCard matchRequest={arcisstrRequest} onDelete={mockOnDelete} />);

    expect(screen.getByTestId('match-location')).toHaveTextContent('Mensa Arcisstraße');
  });
});
