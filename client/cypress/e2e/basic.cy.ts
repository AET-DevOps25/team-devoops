/// <reference types="cypress" />
// Basic end-to-end tests for simple client functionality

describe('Basic Client Functionality', () => {
  it('should load the landing page and redirect or show login', () => {
    cy.visit('/');
    // Should either redirect to /login or show login content
    cy.location('pathname').should('match', /\/login$|\/$/);
    cy.get('[data-testid="login-button"]').should('be.visible');
  });

  it('should redirect to login for non-existent route', () => {
    cy.visit('/some-non-existent-route', { failOnStatusCode: false });
    // App redirects to login for unknown routes
    cy.location('pathname').should('eq', '/login');
    cy.get('[data-testid="login-button"]').should('be.visible');
  });

  it('should trigger Auth0 login on login button click', () => {
    cy.visit('/login');
    cy.get('[data-testid="login-button"]').should('be.visible').click();
    // Only check that we are redirected to Auth0 domain
    cy.origin('https://meetatmensa.eu.auth0.com', () => {
      cy.url().should('include', 'meetatmensa.eu.auth0.com');
    });
  });

  it('should display the app logo on the login page', () => {
    cy.visit('/login');
    cy.get('img').should('be.visible'); // Refine selector if you have a data-testid for the logo
  });
}); 