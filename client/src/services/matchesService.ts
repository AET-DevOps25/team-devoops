import { useAuthenticatedApi } from './api';
import { API_VERSION } from './api';
import mockMatches from '../mocks/matches.json';
import type { Match, MatchesResponse } from '../types/matches';

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

export const useMatchesService = () => {
  const api = useAuthenticatedApi();

  const getMatches = async (userId: string): Promise<MatchesResponse> => {
    if (getUseMockDataEnv()) {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 500));
      // Map mock data to Match[] as defined in types/matches.ts
      const matches: Match[] = (mockMatches.matches as any[]).map((m) => ({
        matchID: m.matchID,
        userID: m.userID ?? userId, // fallback if missing
        status: m.status,
        group: {
          groupID: m.group?.groupID,
          date: m.group?.date,
          time: m.group?.time ?? (Array.isArray(m.group?.timeslot) ? m.group.timeslot[0] : undefined),
          location: m.group?.location,
          userStatus: m.group?.userStatus ?? [],
          conversationStarters: m.group?.conversationStarters ?? { conversationsStarters: [] },
        },
      }));
      return { matches };
    }
    try {
      const response = await api.get(`${API_VERSION}/matching/matches/${userId}`);
      // Map API data to Match[] as defined in types/matches.ts
      const matches: Match[] = (response.data.matches as any[]).map((m) => ({
        matchID: m.matchID,
        userID: m.userID ?? userId,
        status: m.status,
        group: {
          groupID: m.group?.groupID,
          date: m.group?.date,
          time: m.group?.time ?? (Array.isArray(m.group?.timeslot) ? m.group.timeslot[0] : undefined),
          location: m.group?.location,
          userStatus: m.group?.userStatus ?? [],
          conversationStarters: m.group?.conversationStarters ?? { conversationsStarters: [] },
        },
      }));
      return { matches };
    } catch (error) {
      console.error('Error fetching matches:', error);
      throw error;
    }
  };

  const acceptMatch = async (matchId: string): Promise<void> => {
    try {
      await api.get(`${API_VERSION}/matching/rsvp/${matchId}/accept`);
    } catch (error) {
      console.error('Error accepting match:', error);
      throw error;
    }
  };

  const rejectMatch = async (matchId: string): Promise<void> => {
    try {
      await api.get(`${API_VERSION}/matching/rsvp/${matchId}/reject`);
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