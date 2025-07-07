describe('Match Requests', () => {
  beforeEach(() => {
    cy.visit('/login');
  });

  it('should redirect to login when accessing match requests page', () => {
    cy.visit('/preferences');
    cy.url().should('include', '/login');
  });

  // Note: The following tests require authentication to be configured
  it.skip('should display match requests list when authenticated', () => {
    // This test is skipped until authentication is properly configured
    // cy.visit('/preferences');
    // cy.get('[data-testid="match-requests-list"]').should('be.visible');
  });

  it.skip('should create a new match request when authenticated', () => {
    // This test is skipped until authentication is properly configured
    // const testRequest = {
    //   date: '2024-12-25',
    //   location: 'arcisstr',
    //   timeslots: [9, 10, 11, 12],
    //   preferences: {
    //     degreePref: true,
    //     agePref: false,
    //     genderPref: true
    //   }
    // };

    // cy.visit('/preferences');
    // cy.get('[data-testid="create-match-button"]').click();
    // cy.get('[data-testid="create-match-dialog"]').should('be.visible');
    
    // cy.get('[data-testid="match-date"]').type(testRequest.date);
    // cy.get('[data-testid="match-location"]').select(testRequest.location);
    // cy.get('[data-testid="match-timeslots"]').select(testRequest.timeslots);
    // cy.get('[data-testid="degree-preference"]').check();
    // cy.get('[data-testid="gender-preference"]').check();
    
    // cy.get('[data-testid="submit-match-request"]').click();
    
    // cy.get('[data-testid="match-requests-list"]')
    //   .should('contain', 'Mensa Arcisstraße');
  });


}); 