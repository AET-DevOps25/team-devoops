import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Box,
  FormControlLabel,
  Checkbox,
  Chip,
  Typography,
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { de as deLocale } from 'date-fns/locale';
import { isSameDay, isBefore } from 'date-fns';

// Location options
const LOCATION_OPTIONS = [
  { value: 'garching', label: 'Mensa Garching' },
  { value: 'arcisstr', label: 'Mensa Arcisstraße' },
];

// Timeslot options with their corresponding time ranges
const TIMESLOT_OPTIONS = [
  { value: 1, label: '10:00-10:15' },
  { value: 2, label: '10:15-10:30' },
  { value: 3, label: '10:30-10:45' },
  { value: 4, label: '10:45-11:00' },
  { value: 5, label: '11:00-11:15' },
  { value: 6, label: '11:15-11:30' },
  { value: 7, label: '11:30-11:45' },
  { value: 8, label: '11:45-12:00' },
  { value: 9, label: '12:00-12:15' },
  { value: 10, label: '12:15-12:30' },
  { value: 11, label: '12:30-12:45' },
  { value: 12, label: '12:45-13:00' },
  { value: 13, label: '13:00-13:15' },
  { value: 14, label: '13:15-13:30' },
  { value: 15, label: '13:30-13:45' },
  { value: 16, label: '13:45-14:00' },
];

interface CreateMatchRequestDialogProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (matchRequestData: any) => void; // TODO: Define proper type
}

const CreateMatchRequestDialog: React.FC<CreateMatchRequestDialogProps> = ({
  open,
  onClose,
  onSubmit,
}) => {
  const [selectedLocation, setSelectedLocation] = useState('');
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [selectedTimeslots, setSelectedTimeslots] = useState<number[]>([]);
  const [preferences, setPreferences] = useState({
    degreePref: false,
    agePref: false,
    genderPref: false,
  });

  const handleTimeslotToggle = (timeslot: number) => {
    setSelectedTimeslots((prev) =>
      prev.includes(timeslot)
        ? prev.filter((t) => t !== timeslot)
        : [...prev, timeslot].sort((a, b) => a - b)
    );
  };

  const getDisabledTimeslots = (selectedDate: Date | null) => {
    if (!selectedDate) return [];
    const now = new Date();
    if (!isSameDay(selectedDate, now)) return [];
    // Map of timeslot end times
    const slotEndTimes = [
      '10:15',
      '10:30',
      '10:45',
      '11:00',
      '11:15',
      '11:30',
      '11:45',
      '12:00',
      '12:15',
      '12:30',
      '12:45',
      '13:00',
      '13:15',
      '13:30',
      '13:45',
      '14:00',
    ];
    return TIMESLOT_OPTIONS.filter((_, idx) => {
      const [h, m] = slotEndTimes[idx].split(':').map(Number);
      const slotEnd = new Date(selectedDate);
      slotEnd.setHours(h, m, 0, 0);
      return isBefore(slotEnd, now);
    }).map((timeslot) => timeslot.value);
  };

  const handleSubmit = () => {
    if (!selectedLocation || !selectedDate || selectedTimeslots.length === 0) {
      // TODO: Add proper validation
      return;
    }

    const formattedDate = selectedDate.toISOString().split('T')[0]; // YYYY-MM-DD format

    onSubmit({
      location: selectedLocation,
      date: formattedDate,
      timeslots: selectedTimeslots,
      preferences,
    });
  };

  const disabledTimeslots = getDisabledTimeslots(selectedDate);

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>Create new Match Request</DialogTitle>
      <DialogContent>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, pt: 1 }}>
          <FormControl fullWidth>
            <InputLabel>Location</InputLabel>
            <Select
              value={selectedLocation}
              label="Location"
              onChange={(e) => setSelectedLocation(e.target.value)}
            >
              {LOCATION_OPTIONS.map((location) => (
                <MenuItem key={location.value} value={location.value}>
                  {location.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={deLocale}>
            <DatePicker
              label="Date"
              value={selectedDate}
              onChange={(newValue) => setSelectedDate(newValue)}
              disablePast
            />
          </LocalizationProvider>

          <Box>
            <Typography variant="subtitle2" sx={{ mb: 1 }}>
              Available Timeslots (select at least 3)
            </Typography>
            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 1 }}>
              {TIMESLOT_OPTIONS.map((timeslot) => (
                <Chip
                  key={timeslot.value}
                  label={timeslot.label}
                  onClick={() =>
                    !disabledTimeslots.includes(timeslot.value) &&
                    handleTimeslotToggle(timeslot.value)
                  }
                  color={selectedTimeslots.includes(timeslot.value) ? 'primary' : 'default'}
                  variant={selectedTimeslots.includes(timeslot.value) ? 'filled' : 'outlined'}
                  size="small"
                  disabled={disabledTimeslots.includes(timeslot.value)}
                />
              ))}
            </Box>
          </Box>

          <Box sx={{ mt: 2 }}>
            <Typography variant="subtitle2" sx={{ mb: 1 }}>
              Matching Preferences
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
              Check the preferences you want to prioritize when matching with others
            </Typography>
            <FormControlLabel
              control={
                <Checkbox
                  checked={preferences.degreePref}
                  onChange={(e) => setPreferences({ ...preferences, degreePref: e.target.checked })}
                />
              }
              label="Same Degree"
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={preferences.agePref}
                  onChange={(e) => setPreferences({ ...preferences, agePref: e.target.checked })}
                />
              }
              label="Similar Age"
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={preferences.genderPref}
                  onChange={(e) => setPreferences({ ...preferences, genderPref: e.target.checked })}
                />
              }
              label="Same Gender"
            />
          </Box>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button
          onClick={handleSubmit}
          variant="contained"
          disabled={!selectedLocation || !selectedDate || selectedTimeslots.length < 3}
        >
          Create
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default CreateMatchRequestDialog;
