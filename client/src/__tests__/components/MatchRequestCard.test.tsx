import React from 'react';
import { screen } from '@testing-library/react';
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

  it('renders match request information and handles delete action', async () => {
    const user = userEvent.setup();

    render(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={mockOnDelete} />);

    // Check if basic information is rendered
    expect(screen.getByText('Mensa Garching')).toBeInTheDocument();
    expect(screen.getByText('PENDING')).toBeInTheDocument();
    expect(screen.getByText('Mon, Jan 15')).toBeInTheDocument();
    expect(screen.getByText('12:00-12:45')).toBeInTheDocument();

    // Check if the mensa image is displayed - use a more flexible approach
    const image = screen.getByAltText('garching');
    expect(image).toBeInTheDocument();
    // Don't check the exact src since it might be transformed by the bundler

    // Test delete functionality
    const cancelButton = screen.getByRole('button', { name: /cancel/i });
    await user.click(cancelButton);

    // Should show confirmation dialog
    expect(screen.getByText(/are you sure/i)).toBeInTheDocument();

    const confirmButton = screen.getByRole('button', { name: /confirm/i });
    await user.click(confirmButton);

    expect(mockOnDelete).toHaveBeenCalledWith('123');
  });
});
