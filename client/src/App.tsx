import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import MatchRequests from './components/MatchRequests';
import Matches from './components/Matches';
import { useAuth0 } from '@auth0/auth0-react';
import Profile from './components/Profile';
import { UserIDProvider } from './contexts/UserIDContext';

interface AppProps {
  toggleColorMode: () => void;
  mode: 'light' | 'dark';
}

// Protected Route wrapper component using Auth0
const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { isAuthenticated, isLoading } = useAuth0();

  // Show loading state while Auth0 is initializing
  if (isLoading) {
    return (
      <div
        style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          height: '100vh',
          fontSize: '18px',
        }}
      >
        Loading...
      </div>
    );
  }

  // Redirect to login if not authenticated
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" />;
};

const App = ({ toggleColorMode, mode }: AppProps) => {
  return (
    <UserIDProvider>
      <Router>
        <Routes>
          {/* Public routes */}
          <Route path="/login" element={<Login />} />

          {/* Protected routes - all require authentication */}
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Layout toggleColorMode={toggleColorMode} mode={mode}>
                  <Dashboard />
                </Layout>
              </ProtectedRoute>
            }
          />
          <Route
            path="/dashboard"
            element={
              <ProtectedRoute>
                <Layout toggleColorMode={toggleColorMode} mode={mode}>
                  <Dashboard />
                </Layout>
              </ProtectedRoute>
            }
          />
          <Route
            path="/matchrequests"
            element={
              <ProtectedRoute>
                <Layout toggleColorMode={toggleColorMode} mode={mode}>
                  <MatchRequests />
                </Layout>
              </ProtectedRoute>
            }
          />
          <Route
            path="/matches"
            element={
              <ProtectedRoute>
                <Layout toggleColorMode={toggleColorMode} mode={mode}>
                  <Matches />
                </Layout>
              </ProtectedRoute>
            }
          />
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <Layout toggleColorMode={toggleColorMode} mode={mode}>
                  <Profile />
                </Layout>
              </ProtectedRoute>
            }
          />

          {/* Account settings route */}
          <Route
            path="/account"
            element={
              <ProtectedRoute>
                <Layout toggleColorMode={toggleColorMode} mode={mode}>
                  <div>Account Settings (Coming Soon)</div>
                </Layout>
              </ProtectedRoute>
            }
          />

          {/* Catch all route */}
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </Router>
    </UserIDProvider>
  );
};

export default App;
