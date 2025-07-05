import React from 'react';
import { screen, render } from '@testing-library/react';

// Mock the problematic modules that use import.meta
jest.mock('../services/api', () => ({
  useAuthenticatedApi: jest.fn(() => ({
    get: jest.fn(),
    post: jest.fn(),
    delete: jest.fn(),
  })),
}));

jest.mock('../services/matchRequestService', () => ({
  useMatchRequestService: jest.fn(() => ({
    getMatchRequests: jest.fn(),
    createMatchRequest: jest.fn(),
    deleteMatchRequest: jest.fn(),
  })),
}));

// Mock Auth0
jest.mock('@auth0/auth0-react', () => ({
  useAuth0: () => ({
    isAuthenticated: true,
    isLoading: false,
    user: { sub: 'test-user', name: 'Test User', email: 'test@example.com' },
    loginWithRedirect: jest.fn(),
    logout: jest.fn(),
    getAccessTokenSilently: jest.fn(),
  }),
  Auth0Provider: ({ children }: { children: React.ReactNode }) => <>{children}</>,
}));

// Import App after mocking
const App = require('../App').default;

describe('App', () => {
  it('renders the main application', () => {
    render(<App />);

    // Check for main app elements - the logo image with alt text
    expect(screen.getByAltText('Meet@Mensa')).toBeInTheDocument();

    // Check for navigation elements using getAllByText
    expect(screen.getAllByText('Dashboard').length).toBeGreaterThan(0);
    expect(screen.getAllByText('Match Requests').length).toBeGreaterThan(0);

    // Check that the app is rendering by looking for the avatar with user initials
    expect(screen.getByText('TU')).toBeInTheDocument();
  });
});
