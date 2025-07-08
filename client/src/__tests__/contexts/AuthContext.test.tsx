import React from 'react';
import { screen, waitFor } from '@testing-library/react';
import { render } from '../utils/test-utils';
import { AuthProvider, useAuth } from '../../contexts/AuthContext';
import axios from 'axios';

// Mock axios
jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

// Test component that uses the auth context
const TestComponent = () => {
  const { user, isAuthenticated, login, logout } = useAuth();

  return (
    <div>
      <span data-testid="user-name">{user?.name || 'No user'}</span>
      <span data-testid="user-email">{user?.email || 'No email'}</span>
      <span data-testid="authenticated">{isAuthenticated.toString()}</span>
      <button onClick={() => login('test@example.com', 'password')} data-testid="login-btn">
        Login
      </button>
      <button onClick={() => logout()} data-testid="logout-btn">
        Logout
      </button>
    </div>
  );
};

describe('AuthContext', () => {
  let setItemSpy: jest.SpyInstance;

  beforeEach(() => {
    localStorage.clear();
    jest.clearAllMocks();
    // Set up localStorage spy
    setItemSpy = jest.spyOn(Storage.prototype, 'setItem');
  });

  afterEach(() => {
    setItemSpy.mockRestore();
  });

  it('handles login flow and updates authentication state', async () => {
    // Mock successful login response
    mockedAxios.post.mockResolvedValueOnce({
      data: {
        token: 'fake-jwt-token',
        user: {
          id: 'user1',
          name: 'Test User',
          email: 'test@example.com',
        },
      },
    });

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );

    // Initially should show no user
    expect(screen.getByTestId('user-name')).toHaveTextContent('No user');
    expect(screen.getByTestId('authenticated')).toHaveTextContent('false');

    // Trigger login
    const loginButton = screen.getByTestId('login-btn');
    loginButton.click();

    // Wait for login to complete
    await waitFor(() => {
      expect(screen.getByTestId('user-name')).toHaveTextContent('Test User');
      expect(screen.getByTestId('user-email')).toHaveTextContent('test@example.com');
      expect(screen.getByTestId('authenticated')).toHaveTextContent('true');
    });

    // Verify axios was called with correct parameters
    expect(mockedAxios.post).toHaveBeenCalledWith('/api/auth/login', {
      email: 'test@example.com',
      password: 'password',
    });

    // Verify token was stored
    expect(setItemSpy).toHaveBeenCalledWith('token', 'fake-jwt-token');
  });
});
