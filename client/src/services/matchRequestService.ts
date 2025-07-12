import { useAuthenticatedApi } from './api';
import { API_VERSION } from './api';
import mockMatchRequests from '../mocks/matchRequests.json';

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

// Hook for authenticated match request operations
export const useMatchRequestService = () => {
  const api = useAuthenticatedApi();

  const getMatchRequests = async (userId: string): Promise<MatchRequest[]> => {
    // Check if mock data should be used
    if (import.meta.env.VITE_USE_MOCK_DATA === 'true') {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 500));
      return mockMatchRequests.matches as MatchRequest[];
    }

    try {
      const response = await api.get(`${API_VERSION}/matching/requests/${userId}`);
      return response.data;
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

  return {
    getMatchRequests,
    deleteMatchRequest,
    submitMatchRequest,
  };
}; 