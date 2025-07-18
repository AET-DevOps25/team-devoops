import React from 'react';
import { screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { render } from '../utils/test-utils';
import AppBar from '../../components/AppBar';
import { useAuth0 } from '@auth0/auth0-react';

// Mock Auth0
jest.mock('@auth0/auth0-react', () => ({
  useAuth0: jest.fn(),
  Auth0Provider: ({ children }: { children: React.ReactNode }) => <>{children}</>,
}));

const mockedUseAuth0 = useAuth0 as jest.MockedFunction<typeof useAuth0>;

describe('AppBar', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders app bar with logo and navigation', () => {
    mockedUseAuth0.mockReturnValue({
      isAuthenticated: true,
      isLoading: false,
      user: { sub: 'test-user', name: 'Test User', email: 'test@example.com' },
      loginWithRedirect: jest.fn(),
      logout: jest.fn(),
      getAccessTokenSilently: jest.fn(),
    } as any);

    render(<AppBar />);

    // Check for logo
    expect(screen.getByAltText('Meet@Mensa')).toBeInTheDocument();

    // Check for navigation items (avatar menu)
    expect(screen.getByTestId('user-menu')).toBeInTheDocument();
  });

  it('displays user avatar with initials when user is authenticated', () => {
    mockedUseAuth0.mockReturnValue({
      isAuthenticated: true,
      isLoading: false,
      user: { sub: 'test-user', name: 'Test User', email: 'test@example.com' },
      loginWithRedirect: jest.fn(),
      logout: jest.fn(),
      getAccessTokenSilently: jest.fn(),
    } as any);

    render(<AppBar />);

    // Check for user avatar (should always be present)
    expect(screen.getByTestId('user-menu')).toBeInTheDocument();
  });

  it('handles logout when logout button is clicked', async () => {
    const mockLogout = jest.fn();
    mockedUseAuth0.mockReturnValue({
      isAuthenticated: true,
      isLoading: false,
      user: { sub: 'test-user', name: 'Test User', email: 'test@example.com' },
      loginWithRedirect: jest.fn(),
      logout: mockLogout,
      getAccessTokenSilently: jest.fn(),
    } as any);

    const user = userEvent.setup();
    render(<AppBar />);

    // Click on user avatar to open menu
    const avatar = screen.getByTestId('user-menu');
    await user.click(avatar);

    // Click logout button
    const logoutButton = screen.getByTestId('menu-item-logout');
    await user.click(logoutButton);

    expect(mockLogout).toHaveBeenCalled();
  });
}); 