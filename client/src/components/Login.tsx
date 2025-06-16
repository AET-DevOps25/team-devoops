import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { TextField, Button, Alert, Typography, Box } from '@mui/material';
import LinkedInIcon from '@mui/icons-material/LinkedIn';

const Login: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      await login(email, password);
      navigate('/dashboard');
    } catch (err) {
      setError('Invalid email or password');
    }
  };

  return (
    <div className="min-h-screen w-full flex items-center justify-center bg-[#f3f2ef]">
      <div className="w-[400px]">
        <div className="bg-white rounded-lg shadow-sm p-8">
          <div className="text-center mb-6">
            <LinkedInIcon sx={{ fontSize: 40, color: '#0a66c2' }} />
            <Typography variant="h4" className="mt-2 font-light text-[#1a1a1a]">
              Sign in
            </Typography>
            <Typography variant="body2" className="text-[#1a1a1a] mt-1">
              Stay updated on your professional world
            </Typography>
          </div>

          <form className="space-y-4" onSubmit={handleSubmit}>
            {error && (
              <Alert severity="error" className="mb-4">
                {error}
              </Alert>
            )}

            <div className="space-y-4">
              <TextField
                fullWidth
                id="email"
                name="email"
                type="email"
                autoComplete="email"
                required
                placeholder="Email or Phone"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                variant="outlined"
                sx={{
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': {
                      borderColor: '#e0e0e0',
                    },
                    '&:hover fieldset': {
                      borderColor: '#0a66c2',
                    },
                    '&.Mui-focused fieldset': {
                      borderColor: '#0a66c2',
                    },
                  },
                }}
              />

              <TextField
                fullWidth
                id="password"
                name="password"
                type="password"
                autoComplete="current-password"
                required
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                variant="outlined"
                sx={{
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': {
                      borderColor: '#e0e0e0',
                    },
                    '&:hover fieldset': {
                      borderColor: '#0a66c2',
                    },
                    '&.Mui-focused fieldset': {
                      borderColor: '#0a66c2',
                    },
                  },
                }}
              />
            </div>

            <Box className="flex justify-end">
              <Button
                color="primary"
                className="text-[#0a66c2] hover:underline"
                sx={{ textTransform: 'none' }}
              >
                Forgot password?
              </Button>
            </Box>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              className="bg-[#0a66c2] hover:bg-[#004182] rounded-full"
              sx={{
                py: 1.5,
                textTransform: 'none',
                fontSize: '16px',
                fontWeight: 600,
              }}
            >
              Sign in
            </Button>
          </form>

          <Box className="mt-4 text-center">
            <Typography variant="body2" className="text-[#1a1a1a]">
              New to LinkedIn?{' '}
              <Link to="/register" className="text-[#0a66c2] font-semibold hover:underline">
                Join now
              </Link>
            </Typography>
          </Box>
        </div>
      </div>
    </div>
  );
};

export default Login;
