import { formatDate, formatTime, formatLocation } from '../../utils/formatting';

describe('formatting utilities', () => {
  describe('formatDate', () => {
    it('formats date correctly', () => {
      const dateString = '2024-01-15';
      const formatted = formatDate(dateString);
      expect(formatted).toBe('Mon, Jan 15');
    });

    it('handles different date formats', () => {
      const dateString = '2024-12-25';
      const formatted = formatDate(dateString);
      expect(formatted).toBe('Wed, Dec 25');
    });
  });

  describe('formatTime', () => {
    it('formats time slot correctly', () => {
      const timeSlot = 9; // 12:00 PM
      const formatted = formatTime(timeSlot);
      expect(formatted).toBe('12:00 PM');
    });

    it('handles different time slots', () => {
      const timeSlot = 1; // 8:00 AM
      const formatted = formatTime(timeSlot);
      expect(formatted).toBe('8:00 AM');
    });

    it('returns unknown for invalid time slot', () => {
      const timeSlot = 100;
      const formatted = formatTime(timeSlot);
      expect(formatted).toBe('Unknown time');
    });
  });

  describe('formatLocation', () => {
    it('formats GARCHING location correctly', () => {
      const location = 'GARCHING';
      const formatted = formatLocation(location);
      expect(formatted).toBe('Mensa Garching');
    });

    it('formats ARCISSTR location correctly', () => {
      const location = 'ARCISSTR';
      const formatted = formatLocation(location);
      expect(formatted).toBe('Mensa Arcisstraße');
    });

    it('handles unknown location', () => {
      const location = 'UNKNOWN';
      const formatted = formatLocation(location);
      expect(formatted).toBe('Unknown Location');
    });
  });
}); 