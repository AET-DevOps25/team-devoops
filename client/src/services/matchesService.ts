import { useAuthenticatedApi } from './api';
import { API_VERSION } from './api';
import mockMatches from '../mocks/matches.json';

function getUseMockDataEnv() {
  // Use process.env for testability, fallback to import.meta.env for Vite
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

export interface Match {
  matchID: string;
  status: 'SENT' | 'CONFIRMED' | 'REJECTED';
  group: {
    groupID: string;
    date: string;
    location: string;
    timeslot: number[];
  };
  users: Array<{
    userID: string;
    firstname: string;
    lastname: string;
    email: string;
  }>;
}

export interface MatchesResponse {
  matches: Match[];
}

export const useMatchesService = () => {
  const api = useAuthenticatedApi();

  const getMatches = async (userId: string): Promise<MatchesResponse> => {
    if (getUseMockDataEnv()) {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 500));
      return { matches: mockMatches.matches as Match[] };
    }
    try {
      const response = await api.get(`${API_VERSION}/matching/matches/${userId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching matches:', error);
      throw error;
    }
  };

  const acceptMatch = async (matchId: string): Promise<void> => {
    try {
      await api.post(`${API_VERSION}/matching/match/${matchId}/accept`);
    } catch (error) {
      console.error('Error accepting match:', error);
      throw error;
    }
  };

  const rejectMatch = async (matchId: string): Promise<void> => {
    try {
      await api.post(`${API_VERSION}/matching/match/${matchId}/reject`);
    } catch (error) {
      console.error('Error rejecting match:', error);
      throw error;
    }
  };

  return {
    getMatches,
    acceptMatch,
    rejectMatch,
  };
};