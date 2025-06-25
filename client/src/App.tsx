import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import MatchRequests from './components/MatchRequests';
import Invitations from './components/Invitations';
import LunchMeetings from './components/LunchMeetings';
import Chat from './components/Chat';
import { useAuth0 } from '@auth0/auth0-react';
import Profile from './components/Profile';

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
          path="/preferences"
          element={
            <ProtectedRoute>
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <MatchRequests />
              </Layout>
            </ProtectedRoute>
          }
        />
        <Route
          path="/invitations"
          element={
            <ProtectedRoute>
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <Invitations />
              </Layout>
            </ProtectedRoute>
          }
        />
        <Route
          path="/meetings"
          element={
            <ProtectedRoute>
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <LunchMeetings />
              </Layout>
            </ProtectedRoute>
          }
        />
        <Route
          path="/chat"
          element={
            <ProtectedRoute>
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <Chat />
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
  );
};

export default App;
