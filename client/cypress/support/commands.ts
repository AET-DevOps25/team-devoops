/// <reference types="cypress" />
// ***********************************************
// This example commands.ts shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
//
// declare global {
//   namespace Cypress {
//     interface Chainable {
//       login(email: string, password: string): Chainable<void>
//       drag(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       dismiss(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       visit(originalFn: CommandOriginalFn, url: string, options: Partial<VisitOptions>): Chainable<Element>
//     }
//   }
// }

import { mount } from 'cypress/react';
import 'cypress-file-upload';

// Custom command to login with Auth0
Cypress.Commands.add('login', (email: string, password: string) => {
  cy.visit('/login');
  cy.get('[data-testid="login-button"]').click();
  
  // Handle Auth0 login flow - you'll need to update this with your actual Auth0 domain
  cy.origin(Cypress.env('AUTH0_DOMAIN') || 'https://dev-xyz.us.auth0.com', () => {
    cy.get('#email').type(email);
    cy.get('#password').type(password);
    cy.get('button[type="submit"]').click();
  });
  
  // Wait for redirect back to app
  cy.url().should('include', '/dashboard');
});

// Custom command to create a match request
Cypress.Commands.add('createMatchRequest', (requestData: {
  title: string;
  description: string;
  location: string;
  date: string;
  time: string;
  maxParticipants: number;
}) => {
  cy.get('[data-testid="create-match-button"]').click();
  cy.get('[data-testid="match-title"]').type(requestData.title);
  cy.get('[data-testid="match-description"]').type(requestData.description);
  cy.get('[data-testid="match-location"]').type(requestData.location);
  cy.get('[data-testid="match-date"]').type(requestData.date);
  cy.get('[data-testid="match-time"]').type(requestData.time);
  cy.get('[data-testid="match-max-participants"]').type(requestData.maxParticipants.toString());
  cy.get('[data-testid="submit-match-request"]').click();
});

// Custom command to join a match request
Cypress.Commands.add('joinMatchRequest', (matchId: string) => {
  cy.get(`[data-testid="join-match-${matchId}"]`).click();
  cy.get('[data-testid="confirm-join"]').click();
});

// Custom command to wait for API requests to complete
Cypress.Commands.add('waitForApi', (method: string, url: string) => {
  cy.intercept(method, url).as('apiRequest');
  cy.wait('@apiRequest');
});

// Custom command to check if user is authenticated
Cypress.Commands.add('isAuthenticated', () => {
  cy.window().its('localStorage').invoke('getItem', 'auth0.is.authenticated')
    .should('eq', 'true');
});

// Custom command to logout
Cypress.Commands.add('logout', () => {
  cy.get('[data-testid="user-menu"]').click();
  cy.get('[data-testid="menu-item-logout"]').click();
  cy.url().should('include', '/login');
});

// Custom command to navigate to a specific page
Cypress.Commands.add('navigateTo', (page: string) => {
  cy.visit(`/${page}`);
});

// Custom command to check for toast notifications
Cypress.Commands.add('checkToast', (message: string, type: 'success' | 'error' | 'warning' = 'success') => {
  cy.get(`[data-testid="toast-${type}"]`).should('contain', message);
});

// Custom command to fill form fields
Cypress.Commands.add('fillForm', (formData: Record<string, string>) => {
  Object.entries(formData).forEach(([field, value]) => {
    cy.get(`[data-testid="${field}"]`).type(value);
  });
});

// Custom command to select from dropdown
Cypress.Commands.add('selectFromDropdown', (dropdownId: string, option: string) => {
  cy.get(`[data-testid="${dropdownId}"]`).click();
  cy.get(`[data-value="${option}"]`).click();
});

// Custom command to upload file
Cypress.Commands.add('uploadFile', (inputId: string, filePath: string) => {
  cy.get(`[data-testid="${inputId}"]`).attachFile(filePath);
});

// Add mount command for component testing
Cypress.Commands.add('mount', mount);

// Extend Cypress namespace
declare global {
  namespace Cypress {
    interface Chainable {
      login(email: string, password: string): Chainable<void>;
      createMatchRequest(requestData: {
        title: string;
        description: string;
        location: string;
        date: string;
        time: string;
        maxParticipants: number;
      }): Chainable<void>;
      joinMatchRequest(matchId: string): Chainable<void>;
      waitForApi(method: string, url: string): Chainable<void>;
      isAuthenticated(): Chainable<void>;
      logout(): Chainable<void>;
      navigateTo(page: string): Chainable<void>;
      checkToast(message: string, type?: 'success' | 'error' | 'warning'): Chainable<void>;
      fillForm(formData: Record<string, string>): Chainable<void>;
      selectFromDropdown(dropdownId: string, option: string): Chainable<void>;
      uploadFile(inputId: string, filePath: string): Chainable<void>;
      mount: typeof mount;
    }
  }
}