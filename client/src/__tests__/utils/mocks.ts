// Common mock data for testing
export const mockMatchRequest = {
  requestID: '123',
  userID: 'user1',
  groupID: 'group1',
  date: '2024-01-15',
  location: 'garching',
  preferences: {
    degreePref: true,
    agePref: false,
    genderPref: true,
  },
  timeslots: [9, 10, 11],
  status: 'PENDING' as const,
};

export const mockUser = {
  id: 'user1',
  email: 'test@example.com',
  name: 'Test User',
};

export const mockMatchRequests = [
  mockMatchRequest,
  {
    ...mockMatchRequest,
    requestID: '124',
    location: 'arcisstr',
    status: 'MATCHED' as const,
  },
];

// Mock functions
export const mockOnDelete = jest.fn();
export const mockOnSubmit = jest.fn();
export const mockOnClose = jest.fn();

// Mock Auth0 hook
export const mockUseAuth0 = {
  isAuthenticated: true,
  isLoading: false,
  user: mockUser,
  loginWithRedirect: jest.fn(),
  logout: jest.fn(),
  getAccessTokenSilently: jest.fn().mockResolvedValue('mock-token'),
};

// Mock API responses
export const mockApiResponse = {
  data: mockMatchRequests,
  status: 200,
  statusText: 'OK',
};

export const mockApiError = {
  response: {
    data: { message: 'API Error' },
    status: 500,
    statusText: 'Internal Server Error',
  },
}; 