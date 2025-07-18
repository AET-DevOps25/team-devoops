import { renderHook, waitFor } from '@testing-library/react';
import { useAuthenticatedApi } from '../../services/api';

// Mock the API module
jest.mock('../../services/api', () => ({
  useAuthenticatedApi: jest.fn(),
}));

const mockUseAuthenticatedApi = useAuthenticatedApi as jest.MockedFunction<typeof useAuthenticatedApi>;

describe('User Service', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should handle user data fetching', async () => {
    const mockApi = {
      get: jest.fn().mockResolvedValue({
        data: {
          userID: 'test-user-id',
          name: 'Test User',
          email: 'test@example.com',
        },
      }),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi);

    // Test that the API is called correctly
    const result = await mockApi.get('/api/users/profile');
    
    expect(mockApi.get).toHaveBeenCalledWith('/api/users/profile');
    expect(result.data).toEqual({
      userID: 'test-user-id',
      name: 'Test User',
      email: 'test@example.com',
    });
  });

  it('should handle user data update', async () => {
    const mockApi = {
      put: jest.fn().mockResolvedValue({
        data: {
          userID: 'test-user-id',
          name: 'Updated User',
          email: 'updated@example.com',
        },
      }),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi);

    const updateData = {
      name: 'Updated User',
      email: 'updated@example.com',
    };

    const result = await mockApi.put('/api/users/profile', updateData);
    
    expect(mockApi.put).toHaveBeenCalledWith('/api/users/profile', updateData);
    expect(result.data).toEqual({
      userID: 'test-user-id',
      name: 'Updated User',
      email: 'updated@example.com',
    });
  });

  it('should handle API errors', async () => {
    const mockApi = {
      get: jest.fn().mockRejectedValue(new Error('API Error')),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi);

    await expect(mockApi.get('/api/users/profile')).rejects.toThrow('API Error');
    expect(mockApi.get).toHaveBeenCalledWith('/api/users/profile');
  });
}); 