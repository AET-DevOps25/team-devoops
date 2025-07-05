import React from 'react';
import { render, RenderOptions } from '@testing-library/react';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

// Create a mutable mock for Auth0 that can be updated by tests
const mockAuth0State = {
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
};

// Mock Auth0 for integration tests
jest.mock('@auth0/auth0-react', () => ({
  useAuth0: () => mockAuth0State,
  Auth0Provider: ({ children }: { children: React.ReactNode }) => <>{children}</>,
}));

// Export the mock state so tests can modify it
export const updateAuth0State = (newState: Partial<typeof mockAuth0State>) => {
  Object.assign(mockAuth0State, newState);
};

// Mock the match request service
jest.mock('../../services/matchRequestService', () => ({
  getMatchRequests: jest.fn(),
  submitMatchRequest: jest.fn(),
  deleteMatchRequest: jest.fn(),
}));

// Create theme for Material-UI
const theme = createTheme();

// Test providers wrapper
const IntegrationTestProviders = ({ children }: { children: React.ReactNode }) => (
  <ThemeProvider theme={theme}>
    <CssBaseline />
    {children}
  </ThemeProvider>
);

// Custom render function for integration tests
const customRender = (ui: React.ReactElement, options?: Omit<RenderOptions, 'wrapper'>) =>
  render(ui, { wrapper: IntegrationTestProviders, ...options });

// Re-export everything from testing library
export * from '@testing-library/react';

// Override render to use our custom render
export { customRender as render };
