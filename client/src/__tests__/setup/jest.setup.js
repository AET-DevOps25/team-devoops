// Mock Vite's import.meta
global.import = {
  meta: {
    env: {
      VITE_API_BASE_URL: 'http://localhost:8080',
      VITE_AUTH0_DOMAIN: 'test-domain.auth0.com',
      VITE_AUTH0_CLIENT_ID: 'test-client-id',
      VITE_AUTH0_AUDIENCE: 'test-audience',
      VITE_USE_MOCK_DATA: 'true',
      MODE: 'test',
      DEV: false,
      PROD: false,
    },
  },
};

// Also mock import.meta directly for modules that use it
Object.defineProperty(global, 'import', {
  value: {
    meta: {
      env: {
        VITE_API_BASE_URL: 'http://localhost:8080',
        VITE_AUTH0_DOMAIN: 'test-domain.auth0.com',
        VITE_AUTH0_CLIENT_ID: 'test-client-id',
        VITE_AUTH0_AUDIENCE: 'test-audience',
        VITE_USE_MOCK_DATA: 'true',
        MODE: 'test',
        DEV: false,
        PROD: false,
      },
    },
  },
  writable: true,
});

// Mock window.location for Auth0
Object.defineProperty(window, 'location', {
  value: {
    origin: 'https://localhost:3000', // Use HTTPS to satisfy Auth0
    href: 'https://localhost:3000',
    protocol: 'https:',
    host: 'localhost:3000',
    hostname: 'localhost',
    port: '3000',
    pathname: '/',
    search: '',
    hash: '',
  },
  writable: true,
});

// Mock localStorage
const localStorageMock = {
  getItem: jest.fn(),
  setItem: jest.fn(),
  removeItem: jest.fn(),
  clear: jest.fn(),
};
global.localStorage = localStorageMock;

// Mock sessionStorage
const sessionStorageMock = {
  getItem: jest.fn(),
  setItem: jest.fn(),
  removeItem: jest.fn(),
  clear: jest.fn(),
};
global.sessionStorage = sessionStorageMock;

// Suppress console errors for Auth0 warnings
const originalError = console.error;
console.error = (...args) => {
  if (
    typeof args[0] === 'string' &&
    (args[0].includes('auth0-spa-js must run on a secure origin') ||
     args[0].includes('Auth0Provider') ||
     args[0].includes('auth0-spa-js'))
  ) {
    return;
  }
  originalError.call(console, ...args);
};

// Suppress React Router deprecation warnings
const originalWarn = console.warn;
console.warn = (...args) => {
  if (
    typeof args[0] === 'string' &&
    (args[0].includes('React Router Future Flag Warning') ||
     args[0].includes('auth0-spa-js'))
  ) {
    return;
  }
  originalWarn.call(console, ...args);
}; 