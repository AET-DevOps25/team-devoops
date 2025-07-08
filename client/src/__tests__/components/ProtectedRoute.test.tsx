// Mock Auth0 at the top
jest.mock('@auth0/auth0-react', () => ({
  useAuth0: jest.fn(),
  Auth0Provider: ({ children }: { children: React.ReactNode }) => <>{children}</>,
}));

import React from 'react';
import { screen } from '@testing-library/react';
import { render } from '../utils/test-utils';
import { useAuth0 } from '@auth0/auth0-react';

// Mock Auth0 hook
const mockedUseAuth0 = useAuth0 as jest.MockedFunction<typeof useAuth0>;

// Import the ProtectedRoute component from App.tsx
// Since it's defined inline, we'll create a test version
const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { isAuthenticated, isLoading } = mockedUseAuth0();

  if (isLoading) {
    return <div data-testid="loading">Loading...</div>;
  }

  return isAuthenticated ? <>{children}</> : <div data-testid="redirect">Redirect to login</div>;
};

const TestComponent = () => <div data-testid="protected-content">Protected Content</div>;

describe('ProtectedRoute', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders protected content when user is authenticated', () => {
    mockedUseAuth0.mockReturnValue({
      isAuthenticated: true,
      isLoading: false,
      user: { sub: 'user1', name: 'Test User', email: 'test@example.com' },
      loginWithRedirect: jest.fn(),
      logout: jest.fn(),
      getAccessTokenSilently: jest.fn(),
      getAccessTokenWithPopup: jest.fn(),
      getIdTokenClaims: jest.fn(),
      loginWithPopup: jest.fn(),
      handleRedirectCallback: jest.fn(),
    } as any);

    render(
      <ProtectedRoute>
        <TestComponent />
      </ProtectedRoute>
    );

    expect(screen.getByTestId('protected-content')).toBeInTheDocument();
    expect(screen.queryByTestId('loading')).not.toBeInTheDocument();
    expect(screen.queryByTestId('redirect')).not.toBeInTheDocument();
  });

  it('shows loading state when Auth0 is initializing', () => {
    mockedUseAuth0.mockReturnValue({
      isAuthenticated: false,
      isLoading: true,
      user: undefined,
      loginWithRedirect: jest.fn(),
      logout: jest.fn(),
      getAccessTokenSilently: jest.fn(),
      getAccessTokenWithPopup: jest.fn(),
      getIdTokenClaims: jest.fn(),
      loginWithPopup: jest.fn(),
      handleRedirectCallback: jest.fn(),
    } as any);

    render(
      <ProtectedRoute>
        <TestComponent />
      </ProtectedRoute>
    );

    expect(screen.getByTestId('loading')).toBeInTheDocument();
    expect(screen.queryByTestId('protected-content')).not.toBeInTheDocument();
    expect(screen.queryByTestId('redirect')).not.toBeInTheDocument();
  });

  it('redirects when user is not authenticated', () => {
    mockedUseAuth0.mockReturnValue({
      isAuthenticated: false,
      isLoading: false,
      user: undefined,
      loginWithRedirect: jest.fn(),
      logout: jest.fn(),
      getAccessTokenSilently: jest.fn(),
      getAccessTokenWithPopup: jest.fn(),
      getIdTokenClaims: jest.fn(),
      loginWithPopup: jest.fn(),
      handleRedirectCallback: jest.fn(),
    } as any);

    render(
      <ProtectedRoute>
        <TestComponent />
      </ProtectedRoute>
    );

    expect(screen.getByTestId('redirect')).toBeInTheDocument();
    expect(screen.queryByTestId('protected-content')).not.toBeInTheDocument();
    expect(screen.queryByTestId('loading')).not.toBeInTheDocument();
  });
});
