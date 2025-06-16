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
  TextField,
} from '@mui/material';
import { DatePicker, TimePicker } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import deLocale from 'date-fns/locale/de';

// Mock data for mensas
const MOCK_MENSAS = [
  { id: '1', name: 'Mensa am Adenauerring', location: 'Adenauerring 7, 76131 Karlsruhe' },
  { id: '2', name: 'Mensa Moltke', location: 'Moltkestr. 30, 76133 Karlsruhe' },
  { id: '3', name: 'Mensa Garching', location: 'Lichtenbergstr. 2, 85748 Garching' },
  { id: '4', name: 'Mensa Arcisstraße', location: 'Arcisstr. 17, 80333 München' },
];

const DURATION_OPTIONS = [
  { value: 30, label: '30 minutes' },
  { value: 60, label: '1 hour' },
  { value: 90, label: '90 minutes' },
  { value: 120, label: '2 hours' },
];

interface CreatePreferenceDialogProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (preferenceData: any) => void; // TODO: Define proper type
}

const CreatePreferenceDialog: React.FC<CreatePreferenceDialogProps> = ({
  open,
  onClose,
  onSubmit,
}) => {
  const [selectedMensa, setSelectedMensa] = useState('');
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [selectedTime, setSelectedTime] = useState<Date | null>(null);
  const [selectedDuration, setSelectedDuration] = useState(60);
  const [filters, setFilters] = useState({
    sameFaculty: false,
    sameStudyProgram: false,
  });

  const handleSubmit = () => {
    if (!selectedMensa || !selectedDate || !selectedTime) {
      // TODO: Add proper validation
      return;
    }

    const startTime = new Date(selectedDate);
    startTime.setHours(selectedTime.getHours());
    startTime.setMinutes(selectedTime.getMinutes());

    const endTime = new Date(startTime);
    endTime.setMinutes(endTime.getMinutes() + selectedDuration);

    onSubmit({
      mensa: MOCK_MENSAS.find((m) => m.id === selectedMensa),
      timeSlot: {
        start: startTime.toISOString(),
        end: endTime.toISOString(),
      },
      filters,
    });
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>Create new Meeting Preference</DialogTitle>
      <DialogContent>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, pt: 1 }}>
          <FormControl fullWidth>
            <InputLabel>Mensa</InputLabel>
            <Select
              value={selectedMensa}
              label="Mensa"
              onChange={(e) => setSelectedMensa(e.target.value)}
            >
              {MOCK_MENSAS.map((mensa) => (
                <MenuItem key={mensa.id} value={mensa.id}>
                  {mensa.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={deLocale}>
            <DatePicker
              label="Date"
              value={selectedDate}
              onChange={(newValue) => setSelectedDate(newValue)}
              slotProps={{ textField: { fullWidth: true } }}
            />

            <TimePicker
              label="Start Time"
              value={selectedTime}
              onChange={(newValue) => setSelectedTime(newValue)}
              slotProps={{ textField: { fullWidth: true } }}
            />
          </LocalizationProvider>

          <FormControl fullWidth>
            <InputLabel>Duration</InputLabel>
            <Select
              value={selectedDuration}
              label="Duration"
              onChange={(e) => setSelectedDuration(Number(e.target.value))}
            >
              {DURATION_OPTIONS.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <Box sx={{ mt: 2 }}>
            <FormControlLabel
              control={
                <Checkbox
                  checked={filters.sameFaculty}
                  onChange={(e) => setFilters({ ...filters, sameFaculty: e.target.checked })}
                />
              }
              label="Same Faculty"
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={filters.sameStudyProgram}
                  onChange={(e) => setFilters({ ...filters, sameStudyProgram: e.target.checked })}
                />
              }
              label="Same Study Program"
            />
          </Box>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSubmit} variant="contained">
          Create
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default CreatePreferenceDialog;
