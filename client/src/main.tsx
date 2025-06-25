import { StrictMode, useEffect, useMemo, useState } from 'react';
import { createRoot } from 'react-dom/client';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import useMediaQuery from '@mui/material/useMediaQuery';
import { Auth0Provider } from '@auth0/auth0-react';
import './index.css';
import App from './App.tsx';
import authConfig from './auth_config.json';

const COLOR_MODE_KEY = 'colorMode';

function Main() {
  // Detect system preference
  const prefersDarkMode = useMediaQuery('(prefers-color-scheme: dark)');
  const [mode, setMode] = useState<'light' | 'dark' | undefined>(undefined);

  // On mount, check localStorage for saved mode
  useEffect(() => {
    const savedMode = localStorage.getItem(COLOR_MODE_KEY) as 'light' | 'dark' | null;
    if (savedMode === 'light' || savedMode === 'dark') {
      setMode(savedMode);
    } else {
      setMode(prefersDarkMode ? 'dark' : 'light');
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [prefersDarkMode]);

  // Save mode to localStorage when it changes
  useEffect(() => {
    if (mode) {
      localStorage.setItem(COLOR_MODE_KEY, mode);
    }
  }, [mode]);

  const theme = useMemo(
    () =>
      createTheme({
        palette: {
          mode: mode || (prefersDarkMode ? 'dark' : 'light'),
        },
      }),
    [mode, prefersDarkMode]
  );

  const toggleColorMode = () => {
    setMode((prev) => (prev === 'light' ? 'dark' : 'light'));
  };

  // Only render after mode is set to avoid hydration mismatch
  if (mode === undefined) return null;

  return (
    <Auth0Provider
      domain={authConfig.domain}
      clientId={authConfig.clientId}
      authorizationParams={{
        redirect_uri: window.location.origin,
      }}
    >
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <App toggleColorMode={toggleColorMode} mode={mode} />
      </ThemeProvider>
    </Auth0Provider>
  );
}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Main />
  </StrictMode>
);
