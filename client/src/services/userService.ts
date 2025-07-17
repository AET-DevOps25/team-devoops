import { useAuthenticatedApi } from './api';
import { API_VERSION } from './api';
import mockUsers from '../mocks/users.json';

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

export interface UserProfile {
  userID: string;
  email: string;
  firstname: string;
  lastname: string;
  birthday: string;
  gender: string;
  degree: string;
  degreeStart: number;
  interests: string[];
  bio: string;
}

export interface UpdateUserProfile {
  email: string;
  firstname: string;
  lastname: string;
  birthday: string;
  gender: string;
  degree: string;
  degreeStart: number;
  interests: string[];
  bio: string;
}

// Hook for authenticated user operations
export const useUserService = () => {
  const api = useAuthenticatedApi();

  const getCurrentUser = async (authId: string): Promise<{ userID: string }> => {
    if (getUseMockDataEnv()) {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 300));
      return mockUsers.currentUser as { userID: string };
    }

    try {
      const response = await api.get(`${API_VERSION}/user/me/${authId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching current user:', error);
      throw error;
    }
  };

  const getUserProfile = async (userId: string): Promise<UserProfile> => {
    if (getUseMockDataEnv()) {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 300));
      const mockProfile = mockUsers.userProfiles.find(profile => profile.userID === userId);
      if (!mockProfile) {
        throw new Error('User profile not found');
      }
      return mockProfile as UserProfile;
    }

    try {
      const response = await api.get(`${API_VERSION}/user/${userId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching user profile:', error);
      throw error;
    }
  };

  const updateUserProfile = async (userId: string, data: UpdateUserProfile): Promise<UserProfile> => {
    if (getUseMockDataEnv()) {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 300));
      const mockProfile = mockUsers.userProfiles.find(profile => profile.userID === userId);
      if (!mockProfile) {
        throw new Error('User profile not found');
      }
      // Update the mock profile with new data
      const updatedProfile = { ...mockProfile, ...data };
      return updatedProfile as UserProfile;
    }

    try {
      const response = await api.put(`${API_VERSION}/user/${userId}`, data);
      return response.data;
    } catch (error) {
      console.error('Error updating user profile:', error);
      throw error;
    }
  };

  return {
    getCurrentUser,
    getUserProfile,
    updateUserProfile,
  };
}; 