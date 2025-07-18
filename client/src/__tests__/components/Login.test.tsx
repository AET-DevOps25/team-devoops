import React from 'react';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Auth0Provider } from '@auth0/auth0-react';
import Login from '../../components/Login';

// Mock Auth0
const mockLoginWithRedirect = jest.fn();
jest.mock('@auth0/auth0-react', () => ({
  ...jest.requireActual('@auth0/auth0-react'),
  useAuth0: () => ({
    loginWithRedirect: mockLoginWithRedirect,
    isAuthenticated: false,
    isLoading: false,
  }),
}));

const renderWithAuth0 = (component: React.ReactElement) => {
  return render(
    <Auth0Provider
      domain="test-domain"
      clientId="test-client-id"
      authorizationParams={{ redirect_uri: window.location.origin }}
    >
      {component}
    </Auth0Provider>
  );
};

describe('Login', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders login page with logo and button', () => {
    render(<Login />);

    expect(screen.getByAltText('Meet@Mensa')).toBeInTheDocument();
    expect(screen.getByTestId('login-button')).toBeInTheDocument();
  });

  it('displays login button when user is not authenticated', () => {
    render(<Login />);

    expect(screen.getByText('Sign in with Auth0')).toBeInTheDocument();
  });

  it('handles login when login button is clicked', async () => {
    const user = userEvent.setup();
    render(<Login />);

    const loginButton = screen.getByText('Sign in with Auth0');
    await user.click(loginButton);

    expect(mockLoginWithRedirect).toHaveBeenCalled();
  });

  it('shows loading state when Auth0 is initializing', () => {
    // Mock loading state
    jest.doMock('@auth0/auth0-react', () => ({
      ...jest.requireActual('@auth0/auth0-react'),
      useAuth0: () => ({
        loginWithRedirect: mockLoginWithRedirect,
        isAuthenticated: false,
        isLoading: true,
      }),
    }));

    render(<Login />);
    
    // In loading state, the button should still be visible but might be disabled
    expect(screen.getByTestId('login-button')).toBeInTheDocument();
  });
}); 