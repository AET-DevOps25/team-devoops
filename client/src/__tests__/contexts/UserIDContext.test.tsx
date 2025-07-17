import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { useAuth0 } from '@auth0/auth0-react';
import { UserIDProvider, useUserID } from '../../contexts/UserIDContext';
import { useUserService } from '../../services/userService';

// Mock Auth0
jest.mock('@auth0/auth0-react', () => ({
  useAuth0: jest.fn(),
  Auth0Provider: ({ children }: { children: React.ReactNode }) => <>{children}</>,
}));

// Mock user service
jest.mock('../../services/userService', () => ({
  useUserService: jest.fn(),
}));

const mockedUseAuth0 = useAuth0 as jest.MockedFunction<typeof useAuth0>;
const mockedUseUserService = useUserService as jest.MockedFunction<typeof useUserService>;

// Test component to access context
const TestComponent = () => {
  const userID = useUserID();
  return <div data-testid="user-id">{userID || 'no-user-id'}</div>;
};

describe('UserIDContext', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('provides userID when user is authenticated and service returns userID', async () => {
    const mockGetCurrentUser = jest.fn().mockResolvedValue({ userID: 'test-user-123' });
    
    mockedUseAuth0.mockReturnValue({
      user: { sub: 'auth0|123' },
      isAuthenticated: true,
      isLoading: false,
    } as any);

    mockedUseUserService.mockReturnValue({
      getCurrentUser: mockGetCurrentUser,
      getUserProfile: jest.fn(),
      updateUserProfile: jest.fn(),
    });

    render(
      <UserIDProvider>
        <TestComponent />
      </UserIDProvider>
    );

    await waitFor(() => {
      expect(screen.getByTestId('user-id')).toHaveTextContent('test-user-123');
    });

    expect(mockGetCurrentUser).toHaveBeenCalledWith('auth0|123');
  });

  it('provides null when user is not authenticated', () => {
    mockedUseAuth0.mockReturnValue({
      user: null,
      isAuthenticated: false,
      isLoading: false,
    } as any);

    mockedUseUserService.mockReturnValue({
      getCurrentUser: jest.fn(),
      getUserProfile: jest.fn(),
      updateUserProfile: jest.fn(),
    });

    render(
      <UserIDProvider>
        <TestComponent />
      </UserIDProvider>
    );

    expect(screen.getByTestId('user-id')).toHaveTextContent('no-user-id');
  });

  it('provides null when service call fails', async () => {
    const mockGetCurrentUser = jest.fn().mockRejectedValue(new Error('Service error'));
    
    mockedUseAuth0.mockReturnValue({
      user: { sub: 'auth0|123' },
      isAuthenticated: true,
      isLoading: false,
    } as any);

    mockedUseUserService.mockReturnValue({
      getCurrentUser: mockGetCurrentUser,
      getUserProfile: jest.fn(),
      updateUserProfile: jest.fn(),
    });

    render(
      <UserIDProvider>
        <TestComponent />
      </UserIDProvider>
    );

    await waitFor(() => {
      expect(screen.getByTestId('user-id')).toHaveTextContent('no-user-id');
    });
  });
}); 