import { renderHook, waitFor } from '@testing-library/react';
import { useAuthenticatedApi } from '../../services/api';

// Mock the API module
jest.mock('../../services/api', () => ({
  useAuthenticatedApi: jest.fn(),
}));

const mockUseAuthenticatedApi = useAuthenticatedApi as jest.MockedFunction<typeof useAuthenticatedApi>;

describe('Match Request Service', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should handle match request creation', async () => {
    const mockApi = {
      post: jest.fn().mockResolvedValue({
        data: {
          id: 'request-123',
          userID: 'user-123',
          date: '2024-01-15',
          timeslot: [1, 2, 3],
          location: 'Mensa',
          preferences: {
            degreePref: true,
            agePref: false,
            genderPref: true,
          },
        },
      }),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi);

    const requestData = {
      userID: 'user-123',
      date: '2024-01-15',
      timeslot: [1, 2, 3],
      location: 'Mensa',
      preferences: {
        degreePref: true,
        agePref: false,
        genderPref: true,
      },
    };

    const result = await mockApi.post('/api/matchrequests', requestData);
    
    expect(mockApi.post).toHaveBeenCalledWith('/api/matchrequests', requestData);
    expect(result.data).toEqual({
      id: 'request-123',
      userID: 'user-123',
      date: '2024-01-15',
      timeslot: [1, 2, 3],
      location: 'Mensa',
      preferences: {
        degreePref: true,
        agePref: false,
        genderPref: true,
      },
    });
  });

  it('should handle match request fetching', async () => {
    const mockApi = {
      get: jest.fn().mockResolvedValue({
        data: [
          {
            id: 'request-123',
            userID: 'user-123',
            date: '2024-01-15',
            timeslot: [1, 2, 3],
            location: 'Mensa',
            preferences: {
              degreePref: true,
              agePref: false,
              genderPref: true,
            },
          },
        ],
      }),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi);

    const result = await mockApi.get('/api/matchrequests');
    
    expect(mockApi.get).toHaveBeenCalledWith('/api/matchrequests');
    expect(result.data).toHaveLength(1);
    expect(result.data[0]).toEqual({
      id: 'request-123',
      userID: 'user-123',
      date: '2024-01-15',
      timeslot: [1, 2, 3],
      location: 'Mensa',
      preferences: {
        degreePref: true,
        agePref: false,
        genderPref: true,
      },
    });
  });

  it('should handle match request deletion', async () => {
    const mockApi = {
      delete: jest.fn().mockResolvedValue({ data: { message: 'Request deleted' } }),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi);

    const result = await mockApi.delete('/api/matchrequests/request-123');
    
    expect(mockApi.delete).toHaveBeenCalledWith('/api/matchrequests/request-123');
    expect(result.data).toEqual({ message: 'Request deleted' });
  });

  it('should handle API errors', async () => {
    const mockApi = {
      post: jest.fn().mockRejectedValue(new Error('API Error')),
    };

    mockUseAuthenticatedApi.mockReturnValue(mockApi);

    const requestData = {
      userID: 'user-123',
      date: '2024-01-15',
      timeslot: [1, 2, 3],
      location: 'Mensa',
      preferences: {},
    };

    await expect(mockApi.post('/api/matchrequests', requestData)).rejects.toThrow('API Error');
    expect(mockApi.post).toHaveBeenCalledWith('/api/matchrequests', requestData);
  });
}); 