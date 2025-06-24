import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import Layout from './components/Layout';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import MatchRequests from './components/MatchRequests';
import Invitations from './components/Invitations';
import LunchMeetings from './components/LunchMeetings';
import Chat from './components/Chat';
import { useAuth } from './contexts/AuthContext';
import Profile from './components/Profile';

interface AppProps {
  toggleColorMode: () => void;
  mode: 'light' | 'dark';
}

// Protected Route wrapper component
const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" />;
};

const App = ({ toggleColorMode, mode }: AppProps) => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          {/* Public routes */}
          <Route path="/login" element={<Login />} />

          {/* Dashboard routes (unprotected for testing) */}
          <Route
            path="/"
            element={
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <Dashboard />
              </Layout>
            }
          />
          <Route
            path="/dashboard"
            element={
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <Dashboard />
              </Layout>
            }
          />
          <Route
            path="/preferences"
            element={
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <MatchRequests />
              </Layout>
            }
          />
          <Route
            path="/invitations"
            element={
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <Invitations />
              </Layout>
            }
          />
          <Route
            path="/meetings"
            element={
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <LunchMeetings />
              </Layout>
            }
          />
          <Route
            path="/chat"
            element={
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <Chat />
              </Layout>
            }
          />
          <Route
            path="/profile"
            element={
              <Layout toggleColorMode={toggleColorMode} mode={mode}>
                <Profile />
              </Layout>
            }
          />

          {/* Protected routes */}
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
    </AuthProvider>
  );
};

export default App;
