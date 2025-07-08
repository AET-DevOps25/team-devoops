# Cypress Testing Setup

This directory contains the end-to-end (E2E) and component tests for the Meet@Mensa React application using Cypress.

## Structure

```
cypress/
├── e2e/                    # End-to-end tests
│   ├── authentication.cy.ts
│   ├── match-requests.cy.ts
│   └── navigation.cy.ts
├── component/              # Component tests
│   └── MatchRequestCard.cy.tsx
├── support/                # Support files
│   ├── commands.ts         # Custom Cypress commands
│   ├── e2e.ts             # E2E test configuration
│   ├── component.ts       # Component test configuration
│   ├── component-index.html
│   └── index.d.ts         # TypeScript declarations
├── fixtures/               # Test data (if needed)
└── downloads/             # Downloaded files (if any)
```

## Getting Started

### Prerequisites

1. Make sure your development server is running on `http://localhost:3000`
2. Ensure your backend API is running on `http://localhost:8080`
3. Set up test credentials in `cypress.env.json`

### Running Tests

#### E2E Tests

```bash
# Open Cypress Test Runner (interactive mode)
npm run cypress:open

# Run E2E tests in headless mode
npm run cypress:run:e2e

# Run E2E tests with dev server (recommended)
npm run test:e2e

# Run E2E tests with dev server in interactive mode
npm run test:e2e:open
```

#### Component Tests

```bash
# Run component tests in headless mode
npm run cypress:run:component

# Open Cypress Test Runner for component tests
npm run cypress:open --component
```

#### All Tests

```bash
# Run all Cypress tests
npm run cypress:run
```

## Configuration

### Environment Variables

Create a `cypress.env.json` file in the client directory with your test credentials:

```json
{
  "TEST_USER_EMAIL": "your-test-user@example.com",
  "TEST_USER_PASSWORD": "your-test-password",
  "API_BASE_URL": "http://localhost:8080",
  "AUTH0_DOMAIN": "your-auth0-domain",
  "AUTH0_CLIENT_ID": "your-auth0-client-id"
}
```

### Test Data

For consistent test data, you can create fixtures in `cypress/fixtures/`:

```json
// cypress/fixtures/match-requests.json
{
  "validMatchRequest": {
    "title": "Test Match Request",
    "description": "This is a test match request",
    "location": "Mensa Arcisstraße",
    "date": "2024-12-25",
    "time": "12:00",
    "maxParticipants": 4
  }
}
```

## Custom Commands

The following custom commands are available in your tests:

### Authentication

- `cy.login(email, password)` - Login with Auth0
- `cy.logout()` - Logout from the application
- `cy.isAuthenticated()` - Check if user is authenticated

### Match Requests

- `cy.createMatchRequest(data)` - Create a new match request
- `cy.joinMatchRequest(matchId)` - Join an existing match request

### Navigation

- `cy.navigateTo(page)` - Navigate to a specific page

### Forms

- `cy.fillForm(formData)` - Fill form fields with data
- `cy.selectFromDropdown(dropdownId, option)` - Select from dropdown

### Utilities

- `cy.waitForApi(method, url)` - Wait for API requests to complete
- `cy.checkToast(message, type)` - Check for toast notifications
- `cy.uploadFile(inputId, filePath)` - Upload a file

## Writing Tests

### E2E Test Example

```typescript
describe('Match Requests', () => {
  beforeEach(() => {
    cy.login(Cypress.env('TEST_USER_EMAIL'), Cypress.env('TEST_USER_PASSWORD'));
    cy.visit('/dashboard');
  });

  it('should create a new match request', () => {
    const testRequest = {
      title: 'Test Match Request',
      description: 'This is a test match request',
      location: 'Mensa Arcisstraße',
      date: '2024-12-25',
      time: '12:00',
      maxParticipants: 4,
    };

    cy.get('[data-testid="create-match-button"]').click();
    cy.fillForm({
      'match-title': testRequest.title,
      'match-description': testRequest.description,
      'match-location': testRequest.location,
      'match-date': testRequest.date,
      'match-time': testRequest.time,
      'match-max-participants': testRequest.maxParticipants.toString(),
    });

    cy.get('[data-testid="submit-match-request"]').click();
    cy.checkToast('Match request created successfully');
  });
});
```

### Component Test Example

```typescript
import React from 'react'
import { MatchRequestCard } from '../../src/components/MatchRequestCard'

describe('MatchRequestCard.cy.tsx', () => {
  const mockMatchRequest = {
    id: '1',
    title: 'Test Match Request',
    description: 'This is a test match request',
    location: 'Mensa Arcisstraße',
    date: '2024-12-25',
    time: '12:00',
    maxParticipants: 4,
    currentParticipants: 2,
    creator: { id: 'user1', name: 'John Doe', email: 'john@example.com' },
    participants: [
      { id: 'user1', name: 'John Doe' },
      { id: 'user2', name: 'Jane Smith' }
    ],
    createdAt: '2024-12-20T10:00:00Z'
  }

  it('should render match request information correctly', () => {
    cy.mount(<MatchRequestCard matchRequest={mockMatchRequest} onJoin={() => {}} onDelete={() => {}} />)

    cy.get('[data-testid="match-title"]').should('contain', mockMatchRequest.title)
    cy.get('[data-testid="match-description"]').should('contain', mockMatchRequest.description)
  });
});
```

## Best Practices

### Data Attributes

Use `data-testid` attributes for reliable element selection:

```tsx
<button data-testid="create-match-button">Create Match</button>
```

### Test Isolation

- Clear localStorage and cookies before each test
- Use unique test data for each test
- Avoid dependencies between tests

### API Mocking

For faster tests, you can mock API responses:

```typescript
cy.intercept('GET', '/api/match-requests', {
  statusCode: 200,
  body: mockMatchRequests,
}).as('getMatchRequests');
```

### Error Handling

Handle uncaught exceptions gracefully:

```typescript
Cypress.on('uncaught:exception', (err, runnable) => {
  console.log('Uncaught exception:', err.message);
  return false;
});
```

## CI/CD Integration

### GitHub Actions Example

```yaml
name: E2E Tests
on: [push, pull_request]
jobs:
  cypress-run:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-node@v3
        with:
          node-version: '18'
      - run: npm ci
      - run: npm run test:e2e
```

### Environment Variables in CI

Set these environment variables in your CI/CD platform:

- `TEST_USER_EMAIL`
- `TEST_USER_PASSWORD`
- `API_BASE_URL`
- `AUTH0_DOMAIN`
- `AUTH0_CLIENT_ID`

## Troubleshooting

### Common Issues

1. **Tests failing due to Auth0 redirects**

   - Ensure test credentials are correct
   - Check Auth0 domain configuration

2. **Element not found errors**

   - Verify `data-testid` attributes are present
   - Check if elements are conditionally rendered

3. **API timeout errors**

   - Ensure backend server is running
   - Check API base URL configuration

4. **Component test failures**
   - Verify component imports are correct
   - Check for missing dependencies

### Debug Mode

Run tests in debug mode for more detailed output:

```bash
DEBUG=cypress:* npm run cypress:run
```

## Resources

- [Cypress Documentation](https://docs.cypress.io/)
- [Cypress Best Practices](https://docs.cypress.io/guides/references/best-practices)
- [Cypress Component Testing](https://docs.cypress.io/guides/component-testing/introduction)
- [Cypress Custom Commands](https://docs.cypress.io/api/cypress-api/custom-commands)
