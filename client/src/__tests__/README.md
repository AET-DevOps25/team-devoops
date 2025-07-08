# Testing Structure

This directory contains the unit test structure for the Meet@Mensa client application.

## Directory Structure

```
__tests__/
├── components/           # Component tests
│   ├── MatchRequestCard.test.tsx
│   ├── CreateMatchRequestDialog.test.tsx
│   └── ProtectedRoute.test.tsx
├── services/            # Service layer tests
│   └── matchRequestService.test.ts
├── contexts/            # Context tests
│   └── AuthContext.test.tsx
├── utils/               # Test utilities and mocks
│   ├── test-utils.tsx   # Custom render function with providers
│   ├── mocks.ts         # Common mock data and functions
│   └── formatting.test.ts # Utility function tests
└── README.md           # This file
```

## Test Utilities

### `test-utils.tsx`

Provides a custom `render` function that wraps components with all necessary providers:

- Auth0Provider
- BrowserRouter
- Any other context providers

### `mocks.ts`

Contains common mock data and functions:

- `mockMatchRequest` - Sample match request data
- `mockUser` - Sample user data
- `mockOnDelete`, `mockOnSubmit`, `mockOnClose` - Mock callback functions
- `mockUseAuth0` - Mock Auth0 hook
- `mockApiResponse`, `mockApiError` - Mock API responses

## Running Tests

```bash
# Run all tests
npm test

# Run tests in watch mode
npm run test:watch

# Run tests with coverage
npm run test:coverage

# Run tests in CI mode
npm run test:ci
```

## Writing New Tests

### Component Tests

1. Import the component and test utilities
2. Mock any external dependencies (images, APIs, etc.)
3. Use the custom `render` function from `test-utils.tsx`
4. Test user interactions with `userEvent`
5. Assert expected behavior

### Service Tests

1. Mock the API module
2. Use `renderHook` to test custom hooks
3. Test both success and error scenarios
4. Verify API calls and data transformations

### Context Tests

1. Create a test component that uses the context
2. Mock external dependencies (axios, localStorage, etc.)
3. Test state changes and side effects
4. Verify context values are updated correctly

## Best Practices

1. **One test case per file initially** - Focus on the most critical functionality
2. **Use descriptive test names** - Make it clear what is being tested
3. **Mock external dependencies** - Don't rely on real APIs or external services
4. **Test user interactions** - Use `userEvent` for realistic user behavior
5. **Clean up after tests** - Use `beforeEach` to reset mocks and state
6. **Test error scenarios** - Don't just test the happy path

## Coverage Goals

- **Components**: 70% line coverage
- **Services**: 80% line coverage
- **Contexts**: 80% line coverage
- **Utilities**: 90% line coverage

## Next Steps

1. Add more test cases to existing files
2. Create tests for remaining components
3. Add integration tests for user flows
4. Add E2E tests for critical paths
5. Set up continuous integration testing
