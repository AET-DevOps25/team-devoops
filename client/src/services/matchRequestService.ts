import { useAuthenticatedApi } from './api';
import { API_VERSION } from './api';
import mockMatchRequests from '../mocks/matchRequests.json';

function getUseMockDataEnv() {
  if (typeof process !== 'undefined' && process.env && process.env.VITE_USE_MOCK_DATA !== undefined) {
    return process.env.VITE_USE_MOCK_DATA === 'true';
  }
  // @ts-ignore
  if (typeof import.meta !== 'undefined' && import.meta.env && import.meta.env.VITE_USE_MOCK_DATA !== undefined) {
    // @ts-ignore
    return import.meta.env.VITE_USE_MOCK_DATA === 'true';
  }
  return false;
}

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
  timeslot: number[];
  status: 'PENDING' | 'UNMATCHABLE' | 'MATCHED' | 'REMATCH' | 'EXPIRED';
}

export interface SubmitMatchRequest {
  userID: string;
  date: string;
  location: string;
  timeslot: number[];
  preferences: MatchPreferences;
}

// Hook for authenticated match request operations
export const useMatchRequestService = () => {
  const api = useAuthenticatedApi();

  const getMatchRequests = async (userId: string): Promise<MatchRequest[]> => {
    if (getUseMockDataEnv()) {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 500));
      return mockMatchRequests.matches as MatchRequest[];
    }

    try {
      const response = await api.get(`${API_VERSION}/matching/requests/${userId}`);
      return response.data.requests;
    } catch (error) {
      console.error('Error fetching match requests:', error);
      throw error;
    }
  };

  const deleteMatchRequest = async (requestId: string): Promise<void> => {
    try {
      await api.delete(`${API_VERSION}/matching/request/${requestId}`);
    } catch (error) {
      console.error('Error deleting match request:', error);
      throw error;
    }
  };

  const submitMatchRequest = async (data: SubmitMatchRequest): Promise<void> => {
    try {
      await api.post(`${API_VERSION}/matching/request/submit`, data);
    } catch (error) {
      console.error('Error submitting match request:', error);
      throw error;
    }
  };

  const submitDemoMatchRequest = async (data: SubmitMatchRequest): Promise<void> => {
    try {
      await api.post(`${API_VERSION}/matching/demo`, data);
    } catch (error) {
      console.error('Error submitting demo match request:', error);
      throw error;
    }
  };

  return {
    getMatchRequests,
    deleteMatchRequest,
    submitMatchRequest,
    submitDemoMatchRequest,
  };
}; 