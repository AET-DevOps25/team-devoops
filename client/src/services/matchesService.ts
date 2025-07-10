import { MatchesResponse } from '../types/matches';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export interface MatchesServiceError {
  message: string;
  status?: number;
}

export const matchesService = {
  /**
   * Fetch all matches for a specific user
   * @param userId - The unique ID of the user
   * @returns Promise<MatchesResponse> - The matches data
   * @throws MatchesServiceError - If the request fails
   */
  async getMatches(userId: string): Promise<MatchesResponse> {
    try {
      const response = await fetch(`${API_BASE_URL}/matching/matches/${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          // Add authorization header if needed
          // 'Authorization': `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data: MatchesResponse = await response.json();
      return data;
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
  },

  /**
   * Reject a match
   * @param matchId - The unique ID of the match to reject
   * @returns Promise<void> - Success response
   * @throws MatchesServiceError - If the request fails
   */
  async rejectMatch(matchId: string): Promise<void> {
    try {
      const response = await fetch(`${API_BASE_URL}/matching/rsvp/${matchId}/reject`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          // Add authorization header if needed
          // 'Authorization': `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
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
  },

  /**
   * Accept a match
   * @param matchId - The unique ID of the match to accept
   * @returns Promise<void> - Success response
   * @throws MatchesServiceError - If the request fails
   */
  async acceptMatch(matchId: string): Promise<void> {
    try {
      const response = await fetch(`${API_BASE_URL}/matching/rsvp/${matchId}/accept`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          // Add authorization header if needed
          // 'Authorization': `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
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
  },

  /**
   * Get mock matches data for development/testing
   * @returns Promise<MatchesResponse> - The mock matches data
   */
  async getMockMatches(): Promise<MatchesResponse> {
    // Simulate API delay
    await new Promise(resolve => setTimeout(resolve, 500));
    
    // Dynamic dates for upcoming meetings
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(today.getDate() + 1);
    const dayAfterTomorrow = new Date(today);
    dayAfterTomorrow.setDate(today.getDate() + 2);
    const formatDate = (d: Date) => d.toISOString().split('T')[0];

    const mockData: MatchesResponse = {
      matches: [
        {
          matchID: "6832a21f-4dc8-43fb-b3db-b17455998d91",
          userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa",
          status: "CONFIRMED",
          group: {
            groupID: "ec414289-a6cd-4a76-a6d7-c7f42c7f1517",
            date: "2024-12-20",
            time: 3,
            location: "GARCHING",
            userStatus: [
              {
                userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa",
                status: "CONFIRMED"
              },
              {
                userID: "7a8b9c0d-1e2f-3g4h-5i6j-7k8l9m0n1o2p",
                status: "CONFIRMED"
              },
              {
                userID: "3d4e5f6g-7h8i-9j0k-1l2m-3n4o5p6q7r8s",
                status: "CONFIRMED"
              }
            ],
            conversationStarters: {
              conversationsStarters: [
                {
                  prompt: "What's your favorite study spot on campus?"
                },
                {
                  prompt: "Have you tried the new menu items at the mensa?"
                },
                {
                  prompt: "What's the most interesting course you're taking this semester?"
                }
              ]
            }
          }
        },
        {
          matchID: "9a1b2c3d-4e5f-6g7h-8i9j-0k1l2m3n4o5p",
          userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa",
          status: "SENT",
          group: {
            groupID: "f1g2h3i4-j5k6-l7m8-n9o0-p1q2r3s4t5u6v",
            date: "2024-12-22",
            time: 5,
            location: "ARCISSTR",
            userStatus: [
              {
                userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa",
                status: "SENT"
              },
              {
                userID: "w7x8y9z0-1a2b-3c4d-5e6f-7g8h9i0j1k2l",
                status: "CONFIRMED"
              },
              {
                userID: "m3n4o5p6-7q8r-9s0t-1u2v-3w4x5y6z7a8b",
                status: "SENT"
              }
            ],
            conversationStarters: {
              conversationsStarters: [
                {
                  prompt: "What's your major and why did you choose it?"
                },
                {
                  prompt: "Do you have any tips for surviving exam season?"
                }
              ]
            }
          }
        },
        {
          matchID: "c9d0e1f2-3g4h-5i6j-7k8l-9m0n1o2p3q4r5",
          userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa",
          status: "UNSENT",
          group: {
            groupID: "s6t7u8v9-0w1x-2y3z-4a5b-6c7d8e9f0g1h2",
            date: "2024-12-25",
            time: 2,
            location: "GARCHING",
            userStatus: [
              {
                userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa",
                status: "UNSENT"
              },
              {
                userID: "i3j4k5l6-7m8n-9o0p-1q2r-3s4t5u6v7w8x",
                status: "UNSENT"
              },
              {
                userID: "y9z0a1b2-3c4d-5e6f-7g8h-9i0j1k2l3m4n",
                status: "UNSENT"
              },
              {
                userID: "o5p6q7r8-9s0t-1u2v-3w4x-5y6z7a8b9c0d",
                status: "UNSENT"
              }
            ],
            conversationStarters: {
              conversationsStarters: [
                {
                  prompt: "What are your plans for the upcoming holidays?"
                },
                {
                  prompt: "Have you discovered any hidden gems on campus?"
                },
                {
                  prompt: "What's the best advice you've received as a student?"
                }
              ]
            }
          }
        },
        {
          matchID: "upcoming-1",
          userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa",
          status: "CONFIRMED",
          group: {
            groupID: "upcoming-group-1",
            date: formatDate(tomorrow),
            time: 7,
            location: "ARCISSTR",
            userStatus: [
              { userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa", status: "CONFIRMED" },
              { userID: "other-user-1", status: "CONFIRMED" }
            ],
            conversationStarters: {
              conversationsStarters: [
                { prompt: "What's your favorite lunch dish?" },
                { prompt: "Any fun plans for the weekend?" }
              ]
            }
          }
        },
        {
          matchID: "upcoming-2",
          userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa",
          status: "CONFIRMED",
          group: {
            groupID: "upcoming-group-2",
            date: formatDate(dayAfterTomorrow),
            time: 10,
            location: "GARCHING",
            userStatus: [
              { userID: "2c3821b8-1cdb-4b77-bcd8-a1da701e46aa", status: "CONFIRMED" },
              { userID: "other-user-2", status: "CONFIRMED" }
            ],
            conversationStarters: {
              conversationsStarters: [
                { prompt: "What do you like most about the mensa?" },
                { prompt: "How do you spend your breaks?" }
              ]
            }
          }
        }
      ]
    };
    
    return mockData;
  },
}; 