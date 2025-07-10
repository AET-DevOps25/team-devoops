import { useAuthenticatedApi } from './api';
import { API_VERSION } from './api';
import { MatchesResponse } from '../types/matches';
import mockMatches from '../mocks/matches.json';

// Temporary testing configuration - REMOVE when using central gateway
const MATCHING_SERVICE_BASE_URL = 'http://localhost:8081';

export interface MatchesServiceError {
  message: string;
  status?: number;
}

// Hook for authenticated matches operations
export const useMatchesService = () => {
  const api = useAuthenticatedApi();

  /**
   * Fetch all matches for a specific user
   * @param userId - The unique ID of the user
   * @returns Promise<MatchesResponse> - The matches data
   * @throws MatchesServiceError - If the request fails
   */
  const getMatches = async (userId: string): Promise<MatchesResponse> => {
    // Check if mock data should be used
    if (import.meta.env.VITE_USE_MOCK_DATA === 'true') {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 500));
      return mockMatches as MatchesResponse;
    }

    try {
      const response = await api.get(`${MATCHING_SERVICE_BASE_URL}${API_VERSION}/matching/matches/${userId}`);
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        throw {
          message: `Failed to fetch matches: ${error.message}`,
          status: error instanceof Response ? error.status : undefined,
        } as MatchesServiceError;
      }
      throw {
        message: 'An unexpected error occurred while fetching matches',
      } as MatchesServiceError;
    }
  };

  /**
   * Reject a match
   * @param matchId - The unique ID of the match to reject
   * @returns Promise<void> - Success response
   * @throws MatchesServiceError - If the request fails
   */
  const rejectMatch = async (matchId: string): Promise<void> => {
    try {
      await api.get(`${MATCHING_SERVICE_BASE_URL}${API_VERSION}/matching/rsvp/${matchId}/reject`);
    } catch (error) {
      if (error instanceof Error) {
        throw {
          message: `Failed to reject match: ${error.message}`,
          status: error instanceof Response ? error.status : undefined,
        } as MatchesServiceError;
      }
      throw {
        message: 'An unexpected error occurred while rejecting the match',
      } as MatchesServiceError;
    }
  };

  /**
   * Accept a match
   * @param matchId - The unique ID of the match to accept
   * @returns Promise<void> - Success response
   * @throws MatchesServiceError - If the request fails
   */
  const acceptMatch = async (matchId: string): Promise<void> => {
    try {
      await api.get(`${MATCHING_SERVICE_BASE_URL}${API_VERSION}/matching/rsvp/${matchId}/accept`);
    } catch (error) {
      if (error instanceof Error) {
        throw {
          message: `Failed to accept match: ${error.message}`,
          status: error instanceof Response ? error.status : undefined,
        } as MatchesServiceError;
      }
      throw {
        message: 'An unexpected error occurred while accepting the match',
      } as MatchesServiceError;
    }
  };

  return {
    getMatches,
    rejectMatch,
    acceptMatch,
  };
};