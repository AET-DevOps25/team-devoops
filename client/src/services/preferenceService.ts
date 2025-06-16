import axios from 'axios';
import mockData from '../mocks/preferences.json';

// This will be replaced with the actual server URL when available
//const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:3000
const API_BASE_URL = 'http://localhost:3000';

// Use mock data during development
const USE_MOCK_DATA = true;

export interface TimeSlot {
  start: string;
  end: string;
}

export interface ProfileAttributes {
  name: string;
  studyProgram: string;
  semester: number;
  age: number;
}

export interface User {
  userID: string;
  name: string;
  profileAttributes: ProfileAttributes;
  preferences: string[];
  lunchEvents: any[]; // TODO: Define proper type when needed
  invitations: any[]; // TODO: Define proper type when needed
}

export interface Mensa {
  mensaID: string;
  name: string;
  location: string;
}

export interface Filters {
  sameFaculty: boolean;
  sameStudyProgram: boolean;
  otherFilters: Record<string, any>;
}

export interface MeetingPreference {
  preferenceID: string;
  owner: User;
  timeSlot: TimeSlot;
  filters: Filters;
  mensa: Mensa;
  status: string;
}

export const getPreferences = async (): Promise<MeetingPreference[]> => {
  if (USE_MOCK_DATA) {
    // Simulate network delay
    await new Promise(resolve => setTimeout(resolve, 500));
    return mockData.preferences;
  }

  try {
    const response = await axios.get(`${API_BASE_URL}/preference`);
    return response.data;
  } catch (error) {
    console.error('Error fetching preferences:', error);
    throw error;
  }
};

export const deletePreference = async (preferenceId: string): Promise<void> => {
  if (USE_MOCK_DATA) {
    // Simulate network delay
    await new Promise(resolve => setTimeout(resolve, 500));
    return;
  }

  try {
    await axios.delete(`${API_BASE_URL}/preference/${preferenceId}`);
  } catch (error) {
    console.error('Error deleting preference:', error);
    throw error;
  }
}; 