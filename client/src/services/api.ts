import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';

// API version to be used for all endpoint construction
export const API_VERSION = '/api/v2'; // Update this when bumping API version

// Custom hook for authenticated API calls
export const useAuthenticatedApi = () => {
  const { getAccessTokenSilently } = useAuth0();

  const apiBaseUrl =
    window.RUNTIME_CONFIG?.API_BASE_URL ||
    import.meta.env.VITE_API_BASE_URL ||
    'http://localhost:8080';

  // Create a new axios instance for authenticated requests
  const authenticatedApi = axios.create({
    baseURL: apiBaseUrl,
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

declare global {
  interface Window {
    RUNTIME_CONFIG?: {
      API_BASE_URL?: string;
    };
  }
} 