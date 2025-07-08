import React from 'react';
import { screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { render } from '@testing-library/react';

// Mock the entire service module
const mockGetMatchRequests = jest.fn();
const mockSubmitMatchRequest = jest.fn();
const mockDeleteMatchRequest = jest.fn();

jest.mock('../../services/matchRequestService', () => ({
  useMatchRequestService: jest.fn(() => ({
    getMatchRequests: mockGetMatchRequests,
    submitMatchRequest: mockSubmitMatchRequest,
    deleteMatchRequest: mockDeleteMatchRequest,
  })),
}));

// Mock Auth0
jest.mock('@auth0/auth0-react', () => ({
  useAuth0: () => ({
    isAuthenticated: true,
    isLoading: false,
    user: { sub: 'test-user', name: 'Test User', email: 'test@example.com' },
    loginWithRedirect: jest.fn(),
    logout: jest.fn(),
    getAccessTokenSilently: jest.fn(() => Promise.resolve('mock-token')),
  }),
  Auth0Provider: ({ children }: { children: React.ReactNode }) => <>{children}</>,
}));

// Define types for the test
interface TestMatchRequest {
  requestID: string;
  userID: string;
  groupID: string;
  date: string;
  location: string;
  preferences: { degreePref: boolean; agePref: boolean; genderPref: boolean };
  timeslots: number[];
  status: string;
}

interface TestSubmitRequest {
  location: string;
  date: string;
  timeslots: number[];
  preferences: { degreePref: boolean; agePref: boolean; genderPref: boolean };
}

// Test component that uses the service
const TestComponent = () => {
  const { useMatchRequestService } = require('../../services/matchRequestService');
  const { getMatchRequests, submitMatchRequest, deleteMatchRequest } = useMatchRequestService();
  const [requests, setRequests] = React.useState<TestMatchRequest[]>([]);
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState<string | null>(null);

  const loadRequests = async () => {
    setLoading(true);
    try {
      const data = await getMatchRequests('test-user');
      setRequests(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
    } finally {
      setLoading(false);
    }
  };

  const createRequest = async (requestData: TestSubmitRequest) => {
    try {
      await submitMatchRequest(requestData);
      await loadRequests(); // Refresh list
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
    }
  };

  const deleteRequest = async (requestId: string) => {
    try {
      await deleteMatchRequest(requestId);
      await loadRequests(); // Refresh list
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
    }
  };

  return (
    <div>
      <button onClick={loadRequests} data-testid="load-btn">
        Load Requests
      </button>
      <button
        onClick={() =>
          createRequest({
            location: 'garching',
            date: '2024-02-15',
            timeslots: [9, 10, 11],
            preferences: { degreePref: true, agePref: false, genderPref: true },
          })
        }
        data-testid="create-btn"
      >
        Create Request
      </button>
      <button onClick={() => deleteRequest('123')} data-testid="delete-btn">
        Delete Request
      </button>

      {loading && <div data-testid="loading">Loading...</div>}
      {error && <div data-testid="error">{error}</div>}

      <div data-testid="requests-count">Requests: {requests.length}</div>
      {requests.map((req) => (
        <div key={req.requestID} data-testid={`request-${req.requestID}`}>
          {req.location} - {req.date}
        </div>
      ))}
    </div>
  );
};

describe('Service Integration Tests', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('loads match requests from service successfully', async () => {
    const mockRequests: TestMatchRequest[] = [
      {
        requestID: '123',
        userID: 'test-user',
        groupID: 'group1',
        date: '2024-02-15',
        location: 'garching',
        preferences: { degreePref: true, agePref: false, genderPref: true },
        timeslots: [9, 10, 11],
        status: 'PENDING',
      },
    ];

    mockGetMatchRequests.mockImplementation(
      () => new Promise((resolve) => setTimeout(() => resolve(mockRequests), 100))
    );

    const user = userEvent.setup();
    render(<TestComponent />);

    // Click load button
    await user.click(screen.getByTestId('load-btn'));

    // Verify loading state
    expect(screen.getByTestId('loading')).toBeInTheDocument();

    // Wait for service call to complete
    await waitFor(() => {
      expect(mockGetMatchRequests).toHaveBeenCalledWith('test-user');
    });

    // Verify data is displayed
    await waitFor(() => {
      expect(screen.getByTestId('requests-count')).toHaveTextContent('Requests: 1');
      expect(screen.getByTestId('request-123')).toHaveTextContent('garching - 2024-02-15');
    });

    // Verify loading is gone
    expect(screen.queryByTestId('loading')).not.toBeInTheDocument();
  });

  it('creates a new match request via service', async () => {
    const newRequest: TestMatchRequest = {
      requestID: '456',
      userID: 'test-user',
      groupID: 'group2',
      date: '2024-02-15',
      location: 'garching',
      preferences: { degreePref: true, agePref: false, genderPref: true },
      timeslots: [9, 10, 11],
      status: 'PENDING',
    };

    mockGetMatchRequests.mockResolvedValue([]);
    mockSubmitMatchRequest.mockResolvedValue(newRequest);

    const user = userEvent.setup();
    render(<TestComponent />);

    // Create a new request
    await user.click(screen.getByTestId('create-btn'));

    // Verify service was called
    await waitFor(() => {
      expect(mockSubmitMatchRequest).toHaveBeenCalledWith({
        location: 'garching',
        date: '2024-02-15',
        timeslots: [9, 10, 11],
        preferences: { degreePref: true, agePref: false, genderPref: true },
      });
    });

    // Verify getMatchRequests was called to refresh the list
    await waitFor(() => {
      expect(mockGetMatchRequests).toHaveBeenCalled();
    });
  });

  it('deletes a match request via service', async () => {
    mockGetMatchRequests.mockResolvedValue([]);
    mockDeleteMatchRequest.mockResolvedValue(undefined);

    const user = userEvent.setup();
    render(<TestComponent />);

    // Delete a request
    await user.click(screen.getByTestId('delete-btn'));

    // Verify service was called
    await waitFor(() => {
      expect(mockDeleteMatchRequest).toHaveBeenCalledWith('123');
    });

    // Verify getMatchRequests was called to refresh the list
    await waitFor(() => {
      expect(mockGetMatchRequests).toHaveBeenCalled();
    });
  });

  it('handles service errors gracefully', async () => {
    mockGetMatchRequests.mockRejectedValue(new Error('Network error'));

    const user = userEvent.setup();
    render(<TestComponent />);

    // Click load button
    await user.click(screen.getByTestId('load-btn'));

    // Verify error is displayed
    await waitFor(() => {
      expect(screen.getByTestId('error')).toHaveTextContent('Network error');
    });

    // Verify loading is gone
    expect(screen.queryByTestId('loading')).not.toBeInTheDocument();
  });

  it('handles 404 errors when deleting non-existent request', async () => {
    mockGetMatchRequests.mockResolvedValue([]);
    mockDeleteMatchRequest.mockRejectedValue(new Error('Request not found'));

    const user = userEvent.setup();
    render(<TestComponent />);

    // Delete a request
    await user.click(screen.getByTestId('delete-btn'));

    // Verify error is displayed
    await waitFor(() => {
      expect(screen.getByTestId('error')).toHaveTextContent('Request not found');
    });
  });

  it('shows loading states during async operations', async () => {
    // Make the service call take some time
    mockGetMatchRequests.mockImplementation(
      () => new Promise<TestMatchRequest[]>((resolve) => setTimeout(() => resolve([]), 100))
    );

    const user = userEvent.setup();
    render(<TestComponent />);

    // Click load button
    await user.click(screen.getByTestId('load-btn'));

    // Verify loading state appears immediately
    expect(screen.getByTestId('loading')).toBeInTheDocument();

    // Wait for operation to complete
    await waitFor(() => {
      expect(screen.queryByTestId('loading')).not.toBeInTheDocument();
    });
  });
});
