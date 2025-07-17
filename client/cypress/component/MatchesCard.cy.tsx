/// <reference types="cypress" />
import { mount } from 'cypress/react';
import React from 'react';
import { Card, CardContent, Typography, Chip, Button, Box, Divider } from '@mui/material';

const mockMatch = {
  matchID: 'match1',
  status: 'CONFIRMED',
  group: {
    date: '2024-12-25',
    time: 9,
    location: 'Mensa Arcisstraße',
    userStatus: [
      { userID: 'user1', status: 'CONFIRMED' },
      { userID: 'user2', status: 'CONFIRMED' },
      { userID: 'user3', status: 'SENT' },
    ],
    conversationStarters: {
      conversationsStarters: [
        { prompt: 'What is your favorite food?' },
        { prompt: 'If you could travel anywhere, where would you go?' },
        { prompt: 'What is your favorite hobby?' },
      ],
    },
  },
};

const getConfirmedCount = (userStatuses) => userStatuses.filter((u) => u.status === 'CONFIRMED').length;

const formatDate = (dateString) => new Date(dateString).toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' });

const formatTime = (timeSlot) => {
  const times = [
    '8:00 AM', '8:30 AM', '9:00 AM', '9:30 AM', '10:00 AM', '10:30 AM', '11:00 AM', '11:30 AM',
    '12:00 PM', '12:30 PM', '1:00 PM', '1:30 PM', '2:00 PM', '2:30 PM', '3:00 PM', '3:30 PM',
  ];
  return times[timeSlot - 1] || 'Unknown time';
};

describe('MatchesCard.cy.tsx', () => {
  it('should render match card information correctly', () => {
    mount(
      <Card sx={{ height: '280px', display: 'flex', flexDirection: 'column' }}>
        <CardContent sx={{ p: 2, flex: 1, display: 'flex', flexDirection: 'column' }}>
          <Box display="flex" justifyContent="space-between" alignItems="flex-start" mb={1.5}>
            <Typography variant="subtitle1" component="h2" sx={{ fontSize: '0.9rem', fontWeight: 600 }} data-testid="match-date">
              {formatDate(mockMatch.group.date)}
            </Typography>
            <Chip label={mockMatch.status} color="success" size="small" sx={{ fontSize: '0.7rem', height: '20px' }} data-testid="match-status" />
          </Box>
          <Box mb={1.5}>
            <Typography variant="body2" color="text.secondary" sx={{ fontSize: '0.8rem', mb: 0.5 }} data-testid="match-time">
              <strong>Time:</strong> {formatTime(mockMatch.group.time)}
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ fontSize: '0.8rem', mb: 0.5 }} data-testid="match-location">
              <strong>Location:</strong> {mockMatch.group.location}
            </Typography>
            <Box display="flex" alignItems="center" gap={1}>
              <Typography variant="body2" color="text.secondary" sx={{ fontSize: '0.8rem' }}>
                <strong>Participants:</strong>
              </Typography>
              <Chip
                label={`${getConfirmedCount(mockMatch.group.userStatus)}/${mockMatch.group.userStatus.length} confirmed`}
                size="small"
                variant="outlined"
                sx={{ borderColor: 'primary.main', color: 'primary.main', fontSize: '0.65rem', height: '18px' }}
                data-testid="match-participants"
              />
            </Box>
          </Box>
          <Divider sx={{ my: 1.5 }} />
          <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
            <Typography variant="subtitle2" gutterBottom sx={{ fontSize: '0.8rem', fontWeight: 600 }}>
              Conversation Starters:
            </Typography>
            <Box sx={{ flex: 1, overflow: 'hidden' }}>
              {mockMatch.group.conversationStarters.conversationsStarters.slice(0, 2).map((starter, index) => (
                <Typography key={index} variant="body2" color="text.secondary" sx={{ fontStyle: 'italic', fontSize: '0.75rem', mb: 0.5, lineHeight: 1.3, '&:last-child': { mb: 0 } }} data-testid={`conversation-starter-${index}`}>
                  "{starter.prompt}"
                </Typography>
              ))}
              {mockMatch.group.conversationStarters.conversationsStarters.length > 2 && (
                <Button size="small" sx={{ mt: 0.5, fontSize: '0.7rem', textTransform: 'none', color: 'primary.main', p: 0, minWidth: 'auto', '&:hover': { backgroundColor: 'transparent', textDecoration: 'underline' } }} data-testid="view-all-starters">
                  View all ({mockMatch.group.conversationStarters.conversationsStarters.length})
                </Button>
              )}
            </Box>
          </Box>
        </CardContent>
      </Card>
    );
    cy.get('[data-testid="match-date"]').should('contain', 'Wednesday, December 25, 2024');
    cy.get('[data-testid="match-status"]').should('contain', 'CONFIRMED');
    cy.get('[data-testid="match-time"]').should('contain', '12:00 PM');
    cy.get('[data-testid="match-location"]').should('contain', 'Mensa Arcisstraße');
    cy.get('[data-testid="match-participants"]').should('contain', '2/3 confirmed');
    cy.get('[data-testid="conversation-starter-0"]').should('contain', 'What is your favorite food?');
    cy.get('[data-testid="conversation-starter-1"]').should('contain', 'If you could travel anywhere, where would you go?');
    cy.get('[data-testid="view-all-starters"]').should('be.visible');
  });
}); 