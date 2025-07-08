// Mock the API module and mock data at the top
jest.mock('../../services/api', () => ({
  useAuthenticatedApi: jest.fn(() => ({
    get: jest.fn(),
    delete: jest.fn(),
    post: jest.fn(),
  })),
}));

// Mock the mock data with inline data to avoid circular dependency
jest.mock('../../mocks/matchRequests.json', () => ({
  matches: [
    {
      requestID: '123',
      userID: 'user1',
      groupID: 'group1',
      date: '2024-01-15',
      location: 'garching',
      preferences: {
        degreePref: true,
        agePref: false,
        genderPref: true,
      },
      timeslots: [9, 10, 11],
      status: 'PENDING',
    },
    {
      requestID: '124',
      userID: 'user1',
      groupID: 'group1',
      date: '2024-01-16',
      location: 'arcisstr',
      preferences: {
        degreePref: false,
        agePref: true,
        genderPref: false,
      },
      timeslots: [12, 13, 14],
      status: 'MATCHED',
    },
  ],
}));

import { renderHook, waitFor } from '@testing-library/react';
import { useMatchRequestService } from '../../services/matchRequestService';

describe('useMatchRequestService', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('fetches match requests successfully using mock data', async () => {
    const { result } = renderHook(() => useMatchRequestService());
    
    const requests = await result.current.getMatchRequests('user1');
    
    expect(requests).toHaveLength(2);
    expect(requests[0]).toEqual({
      requestID: '123',
      userID: 'user1',
      groupID: 'group1',
      date: '2024-01-15',
      location: 'garching',
      preferences: {
        degreePref: true,
        agePref: false,
        genderPref: true,
      },
      timeslots: [9, 10, 11],
      status: 'PENDING',
    });
    expect(requests[1].location).toBe('arcisstr');
    expect(requests[1].status).toBe('MATCHED');
  });
}); 