import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import Layout from '../../components/Layout';

// Mock Auth0
jest.mock('@auth0/auth0-react', () => ({
  useAuth0: () => ({
    isAuthenticated: true,
    isLoading: false,
    user: { name: 'Test User', email: 'test@example.com' },
    loginWithRedirect: jest.fn(),
    logout: jest.fn(),
    getAccessTokenSilently: jest.fn(),
  }),
}));

// Mock useNavigate
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
  useLocation: () => ({ pathname: '/dashboard' }),
}));

const renderWithProviders = (component: React.ReactElement) => {
  const theme = createTheme();
  return render(
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        {component}
      </ThemeProvider>
    </BrowserRouter>
  );
};

describe('Layout', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders layout with app bar and navigation', () => {
    const mockToggleColorMode = jest.fn();
    
    renderWithProviders(
      <Layout toggleColorMode={mockToggleColorMode} mode="light">
        <div data-testid="test-content">Test Content</div>
      </Layout>
    );

    // Should show app bar
    expect(screen.getByAltText('Meet@Mensa')).toBeInTheDocument();
    
    // Should show navigation items
    expect(screen.getByText('Dashboard')).toBeInTheDocument();
    expect(screen.getByText('Match Requests')).toBeInTheDocument();
    expect(screen.getByText('Matches')).toBeInTheDocument();
    
    // Should show content
    expect(screen.getByTestId('test-content')).toBeInTheDocument();
  });

  it('renders layout with loading state when Auth0 is initializing', () => {
    // Mock loading state
    jest.doMock('@auth0/auth0-react', () => ({
      useAuth0: () => ({
        isAuthenticated: false,
        isLoading: true,
        user: null,
        loginWithRedirect: jest.fn(),
        logout: jest.fn(),
        getAccessTokenSilently: jest.fn(),
      }),
    }));

    const mockToggleColorMode = jest.fn();
    
    renderWithProviders(
      <Layout toggleColorMode={mockToggleColorMode} mode="light">
        <div data-testid="test-content">Test Content</div>
      </Layout>
    );

    // Should still show app bar and content even in loading state
    expect(screen.getByAltText('Meet@Mensa')).toBeInTheDocument();
    expect(screen.getByTestId('test-content')).toBeInTheDocument();
  });

  it('handles navigation when menu items are clicked', async () => {
    const mockToggleColorMode = jest.fn();
    
    renderWithProviders(
      <Layout toggleColorMode={mockToggleColorMode} mode="light">
        <div data-testid="test-content">Test Content</div>
      </Layout>
    );

    // Navigation should be present
    expect(screen.getByText('Dashboard')).toBeInTheDocument();
    expect(screen.getByText('Match Requests')).toBeInTheDocument();
    expect(screen.getByText('Matches')).toBeInTheDocument();
  });
}); 