import React from 'react';
import { screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { render } from '../utils/test-utils';
import CreateMatchRequestDialog from '../../components/CreateMatchRequestDialog';
import { mockOnSubmit, mockOnClose } from '../utils/mocks';
import { useUserID } from '../../contexts/UserIDContext';

// Mock date-fns to control date behavior in tests
jest.mock('date-fns', () => ({
  isSameDay: jest.fn(() => false),
  isBefore: jest.fn(() => false),
}));

jest.mock('../../contexts/UserIDContext', () => ({
  useUserID: () => 'test-user-id',
}));

describe('CreateMatchRequestDialog', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('submits form with valid data when all required fields are filled', async () => {
    const user = userEvent.setup();

    render(<CreateMatchRequestDialog open={true} onClose={mockOnClose} onSubmit={mockOnSubmit} />);

    // Fill in location - use the combobox directly since it has no accessible name
    const locationSelect = screen.getByRole('combobox');
    await user.click(locationSelect);
    await user.click(screen.getByText('Mensa Garching'));

    // For the date, let's try clicking the date picker and then pressing Enter to accept
    const datePickerButton = screen.getByRole('button', { name: /choose date/i });
    await user.click(datePickerButton);

    // Press Enter to accept the current date (today)
    await user.keyboard('{Enter}');

    // Select timeslots (at least 3 as required) - use the actual timeslot values
    const timeslotButtons = screen
      .getAllByRole('button')
      .filter(
        (button) =>
          button.textContent?.includes('12:00-12:15') ||
          button.textContent?.includes('12:15-12:30') ||
          button.textContent?.includes('12:30-12:45')
      );

    // Click each timeslot to select them (these correspond to values 9, 10, 11)
    for (const button of timeslotButtons.slice(0, 3)) {
      await user.click(button);
    }

    // Set preferences
    const degreeCheckbox = screen.getByLabelText(/same degree/i);
    await user.click(degreeCheckbox);

    // Wait for form to be valid (submit button to be enabled)
    await waitFor(
      () => {
        const submitButton = screen.getByRole('button', { name: /create/i });
        expect(submitButton).not.toBeDisabled();
      },
      { timeout: 5000 }
    );

    // Submit the form
    const submitButton = screen.getByRole('button', { name: /create/i });
    await user.click(submitButton);

    await waitFor(() => {
      expect(mockOnSubmit).toHaveBeenCalledWith({
        userID: 'test-user-id',
        location: 'GARCHING',
        date: expect.stringMatching(/^[\d]{4}-[\d]{2}-[\d]{2}$/), // Any valid date format
        timeslot: [9, 10, 11], // These correspond to the selected timeslots
        preferences: {
          degreePref: true,
          agePref: false,
          genderPref: false,
        },
      });
    });
  });

  it('validates required fields correctly', async () => {
    const user = userEvent.setup();

    render(<CreateMatchRequestDialog open={true} onClose={mockOnClose} onSubmit={mockOnSubmit} />);

    // Initially the submit button should be disabled
    const submitButton = screen.getByRole('button', { name: /create/i });
    expect(submitButton).toBeDisabled();

    // Fill in location only - button should still be disabled
    const locationSelect = screen.getByRole('combobox');
    await user.click(locationSelect);
    await user.click(screen.getByText('Mensa Garching'));

    expect(submitButton).toBeDisabled();

    // Select only 2 timeslots - button should still be disabled
    const timeslotButtons = screen
      .getAllByRole('button')
      .filter(
        (button) =>
          button.textContent?.includes('12:00-12:15') || button.textContent?.includes('12:15-12:30')
      );

    for (const button of timeslotButtons.slice(0, 2)) {
      await user.click(button);
    }

    expect(submitButton).toBeDisabled();
  });

  it('closes dialog when close button is clicked', async () => {
    const user = userEvent.setup();
    render(<CreateMatchRequestDialog open={true} onClose={mockOnClose} onSubmit={mockOnSubmit} />);

    const closeButton = screen.getByText('Cancel');
    await user.click(closeButton);

    expect(mockOnClose).toHaveBeenCalled();
  });

  it('submits demo form with valid data when demo button is clicked', async () => {
    const user = userEvent.setup();
    const mockOnDemoSubmit = jest.fn();

    render(
      <CreateMatchRequestDialog 
        open={true} 
        onClose={mockOnClose} 
        onSubmit={mockOnSubmit} 
        onDemoSubmit={mockOnDemoSubmit}
      />
    );

    // Fill in location
    const locationSelect = screen.getByRole('combobox');
    await user.click(locationSelect);
    await user.click(screen.getByText('Mensa Garching'));

    // Set date
    const datePickerButton = screen.getByRole('button', { name: /choose date/i });
    await user.click(datePickerButton);
    await user.keyboard('{Enter}');

    // Select timeslots
    const timeslotButtons = screen
      .getAllByRole('button')
      .filter(
        (button) =>
          button.textContent?.includes('12:00-12:15') ||
          button.textContent?.includes('12:15-12:30') ||
          button.textContent?.includes('12:30-12:45')
      );

    for (const button of timeslotButtons.slice(0, 3)) {
      await user.click(button);
    }

    // Wait for demo button to be enabled
    await waitFor(
      () => {
        const demoButton = screen.getByRole('button', { name: /demo/i });
        expect(demoButton).not.toBeDisabled();
      },
      { timeout: 5000 }
    );

    // Click demo button
    const demoButton = screen.getByRole('button', { name: /demo/i });
    await user.click(demoButton);

    await waitFor(() => {
      expect(mockOnDemoSubmit).toHaveBeenCalledWith({
        userID: 'test-user-id',
        location: 'GARCHING',
        date: expect.stringMatching(/^[\d]{4}-[\d]{2}-[\d]{2}$/),
        timeslot: [9, 10, 11],
        preferences: {
          degreePref: false,
          agePref: false,
          genderPref: false,
        },
      });
    });
  });

  it('falls back to regular submit when demo handler is not provided', async () => {
    const user = userEvent.setup();

    render(<CreateMatchRequestDialog open={true} onClose={mockOnClose} onSubmit={mockOnSubmit} />);

    // Fill in location
    const locationSelect = screen.getByRole('combobox');
    await user.click(locationSelect);
    await user.click(screen.getByText('Mensa Garching'));

    // Set date
    const datePickerButton = screen.getByRole('button', { name: /choose date/i });
    await user.click(datePickerButton);
    await user.keyboard('{Enter}');

    // Select timeslots
    const timeslotButtons = screen
      .getAllByRole('button')
      .filter(
        (button) =>
          button.textContent?.includes('12:00-12:15') ||
          button.textContent?.includes('12:15-12:30') ||
          button.textContent?.includes('12:30-12:45')
      );

    for (const button of timeslotButtons.slice(0, 3)) {
      await user.click(button);
    }

    // Wait for demo button to be enabled
    await waitFor(
      () => {
        const demoButton = screen.getByRole('button', { name: /demo/i });
        expect(demoButton).not.toBeDisabled();
      },
      { timeout: 5000 }
    );

    // Click demo button (should fall back to regular submit)
    const demoButton = screen.getByRole('button', { name: /demo/i });
    await user.click(demoButton);

    await waitFor(() => {
      expect(mockOnSubmit).toHaveBeenCalledWith({
        userID: 'test-user-id',
        location: 'GARCHING',
        date: expect.stringMatching(/^[\d]{4}-[\d]{2}-[\d]{2}$/),
        timeslot: [9, 10, 11],
        preferences: {
          degreePref: false,
          agePref: false,
          genderPref: false,
        },
      });
    });
  });
});
