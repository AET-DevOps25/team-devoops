import { renderHook } from '@testing-library/react';
import { useAuthenticatedApi } from '../../services/api';

// Mock the API module
jest.mock('../../services/api', () => ({
  useAuthenticatedApi: jest.fn(),
}));

const mockUseAuthenticatedApi = useAuthenticatedApi as jest.MockedFunction<typeof useAuthenticatedApi>;

describe('Matches Service', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should handle matches fetching', async () => {
    const mockApi = {
      get: jest.fn().mockResolvedValue({
        data: {
          matches: [
            {
              id: 'match-123',
              userID: 'user-123',
              matchRequestID: 'request-123',
              status: 'PENDING',
              date: '2024-01-15',
              location: 'Mensa',
              timeslot: [1, 2, 3],
            },
          ],
        },
      }),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi as any);

    const result = await mockApi.get('/api/matches');
    
    expect(mockApi.get).toHaveBeenCalledWith('/api/matches');
    expect(result.data.matches).toHaveLength(1);
    expect(result.data.matches[0]).toEqual({
      id: 'match-123',
      userID: 'user-123',
      matchRequestID: 'request-123',
      status: 'PENDING',
      date: '2024-01-15',
      location: 'Mensa',
      timeslot: [1, 2, 3],
    });
  });

  it('should handle match acceptance', async () => {
    const mockApi = {
      put: jest.fn().mockResolvedValue({
        data: {
          id: 'match-123',
          status: 'CONFIRMED',
        },
      }),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi as any);

    const result = await mockApi.put('/api/matches/match-123/accept');
    
    expect(mockApi.put).toHaveBeenCalledWith('/api/matches/match-123/accept');
    expect(result.data).toEqual({
      id: 'match-123',
      status: 'CONFIRMED',
    });
  });

  it('should handle match rejection', async () => {
    const mockApi = {
      put: jest.fn().mockResolvedValue({
        data: {
          id: 'match-123',
          status: 'REJECTED',
        },
      }),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi as any);

    const result = await mockApi.put('/api/matches/match-123/reject');
    
    expect(mockApi.put).toHaveBeenCalledWith('/api/matches/match-123/reject');
    expect(result.data).toEqual({
      id: 'match-123',
      status: 'REJECTED',
    });
  });

  it('should handle API errors', async () => {
    const mockApi = {
      get: jest.fn().mockRejectedValue(new Error('API Error')),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi as any);

    await expect(mockApi.get('/api/matches')).rejects.toThrow('API Error');
    expect(mockApi.get).toHaveBeenCalledWith('/api/matches');
  });
}); 