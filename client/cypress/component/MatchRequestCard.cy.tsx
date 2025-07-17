/// <reference types="cypress" />
import { mount } from 'cypress/react';
import React from 'react';
import MatchRequestCard from '../../src/components/MatchRequestCard';
import { MatchRequest } from '../../src/services/matchRequestService';

describe('MatchRequestCard.cy.tsx', () => {
  const mockMatchRequest: MatchRequest = {
    requestID: '1',
    userID: 'user1',
    groupID: 'group1',
    date: '2024-12-25',
    location: 'ARCISSTR',
    preferences: {
      degreePref: true,
      agePref: false,
      genderPref: true,
    },
    timeslot: [9, 10, 11, 12],
    status: 'PENDING',
  };

  it('should render match request information correctly', () => {
    mount(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={() => {}} />);
    cy.get('[data-testid="match-location"]').should('contain', 'Mensa Arcisstraße');
    cy.get('[data-testid="match-date"]').should('contain', 'Wed, Dec 25');
    cy.get('[data-testid="match-timeslots"]').should('contain', '12:00-13:00');
    cy.get('[data-testid="match-status"]').should('contain', 'PENDING');
  });

  it('should call onDelete when cancel button is clicked and confirmed', () => {
    const onDeleteSpy = cy.spy().as('onDeleteSpy');
    mount(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={onDeleteSpy} />);
    cy.get('[data-testid="cancel-request-button"]').click();
    cy.get('[data-testid="confirm-delete-button"]').click();
    cy.get('@onDeleteSpy').should('have.been.calledWith', mockMatchRequest.requestID);
  });

  it('should show confirmation dialog when cancel button is clicked', () => {
    mount(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={() => {}} />);
    cy.get('[data-testid="cancel-request-button"]').click();
    cy.get('[data-testid="confirm-dialog"]').should('be.visible');
    cy.get('[data-testid="confirm-dialog-title"]').should('contain', 'Cancel Match Request');
  });

  it('should close confirmation dialog when close button is clicked', () => {
    mount(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={() => {}} />);
    cy.get('[data-testid="cancel-request-button"]').click();
    cy.get('[data-testid="confirm-dialog"]').should('be.visible');
    cy.get('[data-testid="close-dialog-button"]').click();
    cy.get('[data-testid="confirm-dialog"]').should('not.exist');
  });

  it('should display preferences correctly', () => {
    mount(<MatchRequestCard matchRequest={mockMatchRequest} onDelete={() => {}} />);
    cy.get('[data-testid="preference-same-degree"]').should('contain', 'Same Degree');
    cy.get('[data-testid="preference-any-age"]').should('contain', 'Any Age');
    cy.get('[data-testid="preference-same-gender"]').should('contain', 'Same Gender');
  });

  it('should display different status colors', () => {
    const matchedRequest = { ...mockMatchRequest, status: 'MATCHED' as const };
    mount(<MatchRequestCard matchRequest={matchedRequest} onDelete={() => {}} />);
    cy.get('[data-testid="match-status"]').should('contain', 'MATCHED');
  });
});
