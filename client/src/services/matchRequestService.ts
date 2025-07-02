import { useAuthenticatedApi } from './api';
import mockData from '../mocks/matchRequests.json';

// Use mock data during development
const USE_MOCK_DATA = true;

export interface MatchPreferences {
  degreePref: boolean;
  agePref: boolean;
  genderPref: boolean;
}

export interface MatchRequest {
  requestID: string;
  userID: string;
  groupID: string;
  date: string;
  location: string;
  preferences: MatchPreferences;
  timeslots: number[];
  status: 'PENDING' | 'UNMATCHABLE' | 'MATCHED' | 'REMATCH' | 'EXPIRED';
}

export interface SubmitMatchRequest {
  date: string;
  location: string;
  timeslots: number[];
  preferences: MatchPreferences;
}

const API_BASE_URL = '/api/v1/matching/requests';

// Hook for authenticated match request operations
export const useMatchRequestService = () => {
  const api = useAuthenticatedApi();

  const getMatchRequests = async (userId: string): Promise<MatchRequest[]> => {
    if (USE_MOCK_DATA) {
      // Simulate network delay
      await new Promise(resolve => setTimeout(resolve, 500));
      // Cast status to the correct union type
      return mockData.matches.map((m: any) => ({
        ...m,
        status: m.status as MatchRequest['status'],
      }));
    }

    try {
      const response = await api.get(`${API_BASE_URL}/${userId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching match requests:', error);
      throw error;
    }
  };

  const deleteMatchRequest = async (requestId: string): Promise<void> => {
    if (USE_MOCK_DATA) {
      // Simulate network delay
      await new Promise(resolve => setTimeout(resolve, 500));
      return;
    }

    try {
      await api.delete(`/api/v1/matching/request/${requestId}`);
    } catch (error) {
      console.error('Error deleting match request:', error);
      throw error;
    }
  };

  const submitMatchRequest = async (data: SubmitMatchRequest): Promise<void> => {
    if (USE_MOCK_DATA) {
      // Simulate network delay and success
      await new Promise(resolve => setTimeout(resolve, 500));
      return;
    }
    try {
      await api.post('/api/v1/matching/request/submit', data);
    } catch (error) {
      console.error('Error submitting match request:', error);
      throw error;
    }
  };

  return {
    getMatchRequests,
    deleteMatchRequest,
    submitMatchRequest,
  };
};

// Legacy functions for backward compatibility (without authentication)
export const getMatchRequests = async (userId: string): Promise<MatchRequest[]> => {
  if (USE_MOCK_DATA) {
    // Simulate network delay
    await new Promise(resolve => setTimeout(resolve, 500));
    // Cast status to the correct union type
    return mockData.matches.map((m: any) => ({
      ...m,
      status: m.status as MatchRequest['status'],
    }));
  }

  try {
    const response = await fetch(`${API_BASE_URL}/${userId}`);
    return response.json();
  } catch (error) {
    console.error('Error fetching match requests:', error);
    throw error;
  }
};

export const deleteMatchRequest = async (requestId: string): Promise<void> => {
  if (USE_MOCK_DATA) {
    // Simulate network delay
    await new Promise(resolve => setTimeout(resolve, 500));
    return;
  }

  try {
    await fetch(`/api/v1/matching/request/${requestId}`, {
      method: 'DELETE',
    });
  } catch (error) {
    console.error('Error deleting match request:', error);
    throw error;
  }
};

export const submitMatchRequest = async (data: SubmitMatchRequest): Promise<void> => {
  if (USE_MOCK_DATA) {
    // Simulate network delay and success
    await new Promise(resolve => setTimeout(resolve, 500));
    return;
  }
  try {
    await fetch('/api/v1/matching/request/submit', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
  } catch (error) {
    console.error('Error submitting match request:', error);
    throw error;
  }
}; 