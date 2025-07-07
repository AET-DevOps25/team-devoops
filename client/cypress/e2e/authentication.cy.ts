describe('Authentication', () => {
  beforeEach(() => {
    cy.visit('/login');
  });

  it('should display login page for unauthenticated users', () => {
    cy.get('[data-testid="login-button"]').should('be.visible');
    cy.get('[data-testid="login-button"]').should('contain', 'Sign in with Auth0');
  });

  it('should protect routes from unauthenticated access', () => {
    cy.visit('/dashboard');
    cy.url().should('include', '/login');
    
    cy.visit('/profile');
    cy.url().should('include', '/login');
  });

  // Note: The following tests require Auth0 test credentials to be configured
  // You'll need to set up test users in your Auth0 tenant and update cypress.env.json
  it.skip('should redirect to dashboard after successful login', () => {
    // This test is skipped until Auth0 test credentials are configured
    cy.visit('/login');
    cy.get('[data-testid="login-button"]').click();
    
    // Auth0 login flow would happen here
    // cy.origin('your-auth0-domain', () => {
    //   cy.get('#email').type(Cypress.env('TEST_USER_EMAIL'));
    //   cy.get('#password').type(Cypress.env('TEST_USER_PASSWORD'));
    //   cy.get('button[type="submit"]').click();
    // });
    
    // cy.url().should('include', '/dashboard');
    // cy.get('[data-testid="user-menu"]').should('be.visible');
  });

  it.skip('should display user profile information after login', () => {
    // This test is skipped until Auth0 test credentials are configured
    cy.visit('/login');
    cy.get('[data-testid="login-button"]').click();
    
    // After successful login
    // cy.get('[data-testid="user-menu"]').click();
    // cy.get('[data-testid="menu-item-profile"]').should('be.visible');
    // cy.get('[data-testid="menu-item-logout"]').should('be.visible');
  });

  it.skip('should logout successfully', () => {
    // This test is skipped until Auth0 test credentials are configured
    cy.visit('/login');
    cy.get('[data-testid="login-button"]').click();
    
    // After successful login
    // cy.get('[data-testid="user-menu"]').click();
    // cy.get('[data-testid="menu-item-logout"]').click();
    // cy.url().should('include', '/login');
    // cy.get('[data-testid="login-button"]').should('be.visible');
  });
}); 