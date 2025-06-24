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

// Protected Route wrapper component
const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" />;
};

const App = () => {
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
              <Layout>
                <Dashboard />
              </Layout>
            }
          />
          <Route
            path="/dashboard"
            element={
              <Layout>
                <Dashboard />
              </Layout>
            }
          />
          <Route
            path="/preferences"
            element={
              <Layout>
                <MatchRequests />
              </Layout>
            }
          />
          <Route
            path="/invitations"
            element={
              <Layout>
                <Invitations />
              </Layout>
            }
          />
          <Route
            path="/meetings"
            element={
              <Layout>
                <LunchMeetings />
              </Layout>
            }
          />
          <Route
            path="/chat"
            element={
              <Layout>
                <Chat />
              </Layout>
            }
          />

          {/* Protected routes */}
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <Layout>
                  <div>Profile Page (Coming Soon)</div>
                </Layout>
              </ProtectedRoute>
            }
          />
          <Route
            path="/account"
            element={
              <ProtectedRoute>
                <Layout>
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
