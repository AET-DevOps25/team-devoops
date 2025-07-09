// Test utility functions that are used across components
// These functions are extracted from components for better testability

const TIMESLOT_MAP: { [key: number]: { start: string; end: string } } = {
  1: { start: '10:00', end: '10:15' },
  2: { start: '10:15', end: '10:30' },
  3: { start: '10:30', end: '10:45' },
  4: { start: '10:45', end: '11:00' },
  5: { start: '11:00', end: '11:15' },
  6: { start: '11:15', end: '11:30' },
  7: { start: '11:30', end: '11:45' },
  8: { start: '11:45', end: '12:00' },
  9: { start: '12:00', end: '12:15' },
  10: { start: '12:15', end: '12:30' },
  11: { start: '12:30', end: '12:45' },
  12: { start: '12:45', end: '13:00' },
  13: { start: '13:00', end: '13:15' },
  14: { start: '13:15', end: '13:30' },
  15: { start: '13:30', end: '13:45' },
  16: { start: '13:45', end: '14:00' },
};

// Utility functions extracted from components
const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', {
    weekday: 'short',
    month: 'short',
    day: 'numeric',
  });
};

const formatLocation = (location: string) => {
  return location === 'garching' ? 'Mensa Garching' : 'Mensa Arcisstraße';
};

const formatTimeslots = (timeslots: number[]) => {
  if (!timeslots.length) return '';
  const sorted = [...timeslots].sort((a, b) => a - b);
  const ranges: { start: number; end: number }[] = [];
  let rangeStart = sorted[0];
  let prev = sorted[0];

  for (let i = 1; i < sorted.length; i++) {
    if (sorted[i] === prev + 1) {
      prev = sorted[i];
    } else {
      ranges.push({ start: rangeStart, end: prev });
      rangeStart = sorted[i];
      prev = sorted[i];
    }
  }
  ranges.push({ start: rangeStart, end: prev });

  return ranges
    .map(({ start, end }) => `${TIMESLOT_MAP[start].start}-${TIMESLOT_MAP[end].end}`)
    .join(', ');
};

const getStatusColor = (status: string) => {
  switch (status) {
    case 'PENDING':
      return 'warning';
    case 'MATCHED':
      return 'success';
    case 'UNMATCHABLE':
      return 'error';
    case 'REMATCH':
      return 'info';
    case 'EXPIRED':
      return 'default';
    default:
      return 'default';
  }
};

describe('Utility Functions', () => {
  describe('formatDate', () => {
    it('formats date string correctly', () => {
      expect(formatDate('2024-01-15')).toBe('Mon, Jan 15');
      expect(formatDate('2024-12-25')).toBe('Wed, Dec 25');
    });
  });

  describe('formatLocation', () => {
    it('formats location strings correctly', () => {
      expect(formatLocation('garching')).toBe('Mensa Garching');
      expect(formatLocation('arcisstr')).toBe('Mensa Arcisstraße');
      expect(formatLocation('unknown')).toBe('Mensa Arcisstraße'); // Default case
    });
  });

  describe('formatTimeslots', () => {
    it('formats consecutive timeslots as ranges', () => {
      expect(formatTimeslots([9, 10, 11])).toBe('12:00-12:45');
      expect(formatTimeslots([1, 2, 3, 4])).toBe('10:00-11:00');
    });

    it('formats non-consecutive timeslots separately', () => {
      expect(formatTimeslots([1, 3, 5])).toBe('10:00-10:15, 10:30-10:45, 11:00-11:15');
    });

    it('handles empty timeslots array', () => {
      expect(formatTimeslots([])).toBe('');
    });

    it('handles single timeslot', () => {
      expect(formatTimeslots([9])).toBe('12:00-12:15');
    });
  });

  describe('getStatusColor', () => {
    it('returns correct color for each status', () => {
      expect(getStatusColor('PENDING')).toBe('warning');
      expect(getStatusColor('MATCHED')).toBe('success');
      expect(getStatusColor('UNMATCHABLE')).toBe('error');
      expect(getStatusColor('REMATCH')).toBe('info');
      expect(getStatusColor('EXPIRED')).toBe('default');
      expect(getStatusColor('UNKNOWN')).toBe('default');
    });
  });
}); 