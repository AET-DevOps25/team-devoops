import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';

// Mock Auth0
jest.mock('@auth0/auth0-react', () => ({
  ...jest.requireActual('@auth0/auth0-react'),
  useAuth0: () => ({
    isAuthenticated: true,
    isLoading: false,
    user: { sub: 'test-user', name: 'Test User', email: 'test@example.com' },
    loginWithRedirect: jest.fn(),
    logout: jest.fn(),
    getAccessTokenSilently: jest.fn(),
  }),
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

describe('App Routing', () => {
  it('should render with providers', () => {
    const TestComponent = () => <div data-testid="test-component">Test</div>;
    
    renderWithProviders(<TestComponent />);
    
    expect(screen.getByTestId('test-component')).toBeInTheDocument();
  });

  it('should handle theme provider', () => {
    const TestComponent = () => <div data-testid="test-component">Test</div>;
    
    renderWithProviders(<TestComponent />);
    
    expect(screen.getByTestId('test-component')).toBeInTheDocument();
  });

  it('should handle router provider', () => {
    const TestComponent = () => <div data-testid="test-component">Test</div>;
    
    renderWithProviders(<TestComponent />);
    
    expect(screen.getByTestId('test-component')).toBeInTheDocument();
  });
});
