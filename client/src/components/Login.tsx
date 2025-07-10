import React from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { Button, Box, useTheme } from '@mui/material';
import mensaLogo from '../assets/meet@mensa_transparent.svg';

const Login: React.FC = () => {
  const { loginWithRedirect } = useAuth0();
  const theme = useTheme();

  return (
    <div className="min-h-screen w-full flex items-center justify-center bg-gray-800">
      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        gap={4}
        sx={{ maxWidth: 400, width: '100%', px: 2 }}
      >
        {/* Logo */}
        <img
          src={mensaLogo}
          alt="Meet@Mensa"
          style={{
            height: '120px',
            width: 'auto',
          }}
        />

        {/* Login Button */}
        <Button
          data-testid="login-button"
          onClick={() => loginWithRedirect()}
          variant="contained"
          size="large"
          sx={{
            py: 2,
            px: 4,
            fontSize: '18px',
            fontWeight: 600,
            textTransform: 'none',
            borderRadius: '8px',
            backgroundColor: theme.palette.mode === 'dark' ? '#374151' : '#f3f4f6',
            color: theme.palette.mode === 'dark' ? '#ffffff' : '#374151',
            '&:hover': {
              backgroundColor: theme.palette.mode === 'dark' ? '#4b5563' : '#e5e7eb',
            },
          }}
        >
          Sign in with Auth0
        </Button>
      </Box>
    </div>
  );
};

export default Login;
