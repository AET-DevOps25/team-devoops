import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';

// Create axios instance with base configuration
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Custom hook for authenticated API calls
export const useAuthenticatedApi = () => {
  const { getAccessTokenSilently } = useAuth0();

  // Create a new axios instance for authenticated requests
  const authenticatedApi = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL ,
    headers: {
      'Content-Type': 'application/json',
    },
  });

  // Add request interceptor to include auth token
  authenticatedApi.interceptors.request.use(
    async (config) => {
      try {
        const token = await getAccessTokenSilently();
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
      } catch (error) {
        console.error('Error getting access token:', error);
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  // Add response interceptor for error handling
  authenticatedApi.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response?.status === 401) {
        console.error('Unauthorized access - token may be invalid');
        // You might want to redirect to login or refresh token
      }
      return Promise.reject(error);
    }
  );

  return authenticatedApi;
};

// Global axios instance with auth interceptor (for use outside of React components)
let globalApi: typeof api | null = null;

export const getAuthenticatedApi = async () => {
  if (!globalApi) {
    globalApi = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    // Add request interceptor
    globalApi.interceptors.request.use(
      async (config) => {
        // This will need to be called from within a React component context
        // where useAuth0 is available
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );
  }
  return globalApi;
};

// Helper function to add auth token to any axios instance
export const addAuthToken = async (axiosInstance: typeof api, getToken: () => Promise<string>) => {
  axiosInstance.interceptors.request.use(
    async (config) => {
      try {
        const token = await getToken();
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
      } catch (error) {
        console.error('Error getting access token:', error);
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );
};

export default api; 