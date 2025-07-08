describe('Navigation', () => {
  beforeEach(() => {
    cy.visit('/login');
  });

  it('should display login page correctly', () => {
    cy.get('[data-testid="login-button"]').should('be.visible');
    cy.url().should('include', '/login');
  });

  it('should redirect to login for protected routes', () => {
    cy.visit('/dashboard');
    cy.url().should('include', '/login');
    
    cy.visit('/profile');
    cy.url().should('include', '/login');
    
    cy.visit('/preferences');
    cy.url().should('include', '/login');
  });

  // Note: The following tests require authentication to be configured
  it.skip('should navigate between main pages when authenticated', () => {
    // This test is skipped until authentication is properly configured
    // cy.visit('/dashboard');
    // cy.get('[data-testid="dashboard-title"]').should('be.visible');
    
    // cy.visit('/profile');
    // cy.get('[data-testid="profile-title"]').should('be.visible');
    
    // cy.visit('/preferences');
    // cy.get('[data-testid="preferences-title"]').should('be.visible');
  });

  it.skip('should show active navigation state', () => {
    // This test is skipped until authentication is properly configured
    // cy.visit('/dashboard');
    // cy.get('[data-testid="nav-dashboard"]').should('have.class', 'active');
    
    // cy.visit('/profile');
    // cy.get('[data-testid="nav-profile"]').should('have.class', 'active');
  });
}); 