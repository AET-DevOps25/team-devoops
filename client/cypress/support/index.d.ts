/// <reference types="cypress" />

declare namespace Cypress {
  interface Chainable {
    /**
     * Custom command to login with Auth0
     * @example cy.login('user@example.com', 'password')
     */
    login(email: string, password: string): Chainable<void>;

    /**
     * Custom command to create a match request
     * @example cy.createMatchRequest({title: 'Test', description: 'Test desc', ...})
     */
    createMatchRequest(requestData: {
      title: string;
      description: string;
      location: string;
      date: string;
      time: string;
      maxParticipants: number;
    }): Chainable<void>;

    /**
     * Custom command to join a match request
     * @example cy.joinMatchRequest('match-id')
     */
    joinMatchRequest(matchId: string): Chainable<void>;

    /**
     * Custom command to wait for API requests to complete
     * @example cy.waitForApi('GET', '/api/match-requests')
     */
    waitForApi(method: string, url: string): Chainable<void>;

    /**
     * Custom command to check if user is authenticated
     * @example cy.isAuthenticated()
     */
    isAuthenticated(): Chainable<void>;

    /**
     * Custom command to logout
     * @example cy.logout()
     */
    logout(): Chainable<void>;

    /**
     * Custom command to navigate to a specific page
     * @example cy.navigateTo('dashboard')
     */
    navigateTo(page: string): Chainable<void>;

    /**
     * Custom command to check for toast notifications
     * @example cy.checkToast('Success message', 'success')
     */
    checkToast(message: string, type?: 'success' | 'error' | 'warning'): Chainable<void>;

    /**
     * Custom command to fill form fields
     * @example cy.fillForm({'field-name': 'value'})
     */
    fillForm(formData: Record<string, string>): Chainable<void>;

    /**
     * Custom command to select from dropdown
     * @example cy.selectFromDropdown('location-filter', 'Mensa Arcisstraße')
     */
    selectFromDropdown(dropdownId: string, option: string): Chainable<void>;

    /**
     * Custom command to upload file
     * @example cy.uploadFile('file-input', 'test-image.jpg')
     */
    uploadFile(inputId: string, filePath: string): Chainable<void>;
  }
} 