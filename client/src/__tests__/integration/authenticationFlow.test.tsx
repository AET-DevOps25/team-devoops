import React from 'react';
import { render, screen, waitFor } from '../utils/integration-test-utils';
import { updateAuth0State } from '../utils/integration-test-utils';
import App from '../../App';

describe('Authentication Flow Integration', () => {
  beforeEach(() => {
    // Reset to default authenticated state
    updateAuth0State({
      isAuthenticated: true,
      isLoading: false,
      user: {
        sub: 'auth0|123456789',
        email: 'test@example.com',
        name: 'Test User',
      },
      loginWithRedirect: jest.fn(),
      logout: jest.fn(),
      getAccessTokenSilently: jest.fn(() => Promise.resolve('mock-token')),
    });
  });

  it('shows dashboard when user is authenticated', async () => {
    // Already authenticated by default
    render(<App toggleColorMode={() => {}} mode="light" />);

    // Should show dashboard
    await waitFor(() => {
      expect(screen.getByRole('heading', { name: /dashboard/i })).toBeInTheDocument();
    });

    // Verify navigation elements are present
    expect(screen.getByRole('button', { name: /match requests/i })).toBeInTheDocument();
  });

  it('shows loading state while Auth0 is initializing', async () => {
    // Mock loading state
    updateAuth0State({
      isAuthenticated: false,
      isLoading: true,
      user: undefined,
    });

    render(<App toggleColorMode={() => {}} mode="light" />);

    // Should show loading state
    expect(screen.getByText(/loading/i)).toBeInTheDocument();
  });
});
