// Utility functions for formatting data across components

export const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', {
    weekday: 'short',
    month: 'short',
    day: 'numeric',
  });
};

export const formatTime = (timeSlot: number) => {
  const times = [
    '8:00 AM',
    '8:30 AM',
    '9:00 AM',
    '9:30 AM',
    '10:00 AM',
    '10:30 AM',
    '11:00 AM',
    '11:30 AM',
    '12:00 PM',
    '12:30 PM',
    '1:00 PM',
    '1:30 PM',
    '2:00 PM',
    '2:30 PM',
    '3:00 PM',
    '3:30 PM',
  ];
  return times[timeSlot - 1] || 'Unknown time';
};

export const formatLocation = (location: string) => {
  switch (location) {
    case 'GARCHING':
      return 'Mensa Garching';
    case 'ARCISSTR':
      return 'Mensa Arcisstraße';
    default:
      return 'Unknown Location';
  }
};

export const TIMESLOT_MAP: { [key: number]: { start: string; end: string } } = {
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

export const formatTimeslots = (timeslots: number[]) => {
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

export const getStatusColor = (status: string) => {
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