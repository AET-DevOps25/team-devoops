import React, { useEffect, useState, useMemo } from 'react';
import {
  Typography,
  Paper,
  Box,
  Button,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  CircularProgress,
  SelectChangeEvent,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import { getPreferences, MeetingPreference, deletePreference } from '../services/preferenceService';
import PreferenceCard from './PreferenceCard';
import CreatePreferenceDialog from './CreatePreferenceDialog';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';

type SortOption = 'date' | 'status' | 'location';

const MeetingPreferences = () => {
  const [sortBy, setSortBy] = React.useState<SortOption>('date');
  const [preferences, setPreferences] = useState<MeetingPreference[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false);

  const handleSortChange = (event: SelectChangeEvent<SortOption>) => {
    setSortBy(event.target.value as SortOption);
  };

  const fetchPreferences = async () => {
    try {
      setLoading(true);
      const data = await getPreferences();
      setPreferences(data);
      setError(null);
    } catch (err) {
      setError('Failed to load preferences. Please try again later.');
      console.error('Error loading preferences:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPreferences();
  }, []);

  const handleDelete = async (preferenceId: string) => {
    try {
      await deletePreference(preferenceId);
      // Refresh the list after successful deletion
      await fetchPreferences();
    } catch (err) {
      setError('Failed to delete preference. Please try again later.');
      console.error('Error deleting preference:', err);
    }
  };

  const handleCreatePreference = async (preferenceData: any) => {
    try {
      // TODO: Implement API call to create preference
      console.log('Creating preference:', preferenceData);
      setIsCreateDialogOpen(false);
      await fetchPreferences();
    } catch (err) {
      setError('Failed to create preference. Please try again later.');
      console.error('Error creating preference:', err);
    }
  };

  const sortedPreferences = useMemo(() => {
    return [...preferences].sort((a, b) => {
      switch (sortBy) {
        case 'date':
          // Sort by start date, earliest first
          return new Date(a.timeSlot.start).getTime() - new Date(b.timeSlot.start).getTime();

        case 'status':
          // Sort by status: MATCHED first, then OPEN
          if (a.status === b.status) return 0;
          return a.status === 'MATCHED' ? -1 : 1;

        case 'location':
          // Sort alphabetically by mensa name
          return a.mensa.name.localeCompare(b.mensa.name);

        default:
          return 0;
      }
    });
  }, [preferences, sortBy]);

  return (
    <Box>
      <Paper sx={{ p: 3 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
          <Box sx={{ display: 'flex', gap: 2 }}>
            <FormControl size="small" sx={{ minWidth: 120 }}>
              <InputLabel id="sort-select-label">Sort by</InputLabel>
              <Select
                labelId="sort-select-label"
                value={sortBy}
                label="Sort by"
                onChange={handleSortChange}
              >
                <MenuItem value="date">Date</MenuItem>
                <MenuItem value="status">Status</MenuItem>
                <MenuItem value="location">Location</MenuItem>
              </Select>
            </FormControl>
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={() => setIsCreateDialogOpen(true)}
            >
              Create Meeting Preference
            </Button>
          </Box>
        </Box>

        {loading ? (
          <Box sx={{ display: 'flex', justifyContent: 'center', p: 3 }}>
            <CircularProgress />
          </Box>
        ) : error ? (
          <Typography color="error" sx={{ p: 2 }}>
            {error}
          </Typography>
        ) : preferences.length === 0 ? (
          <Typography sx={{ p: 2, textAlign: 'center' }}>
            No meeting preferences found. Create your first preference to get started!
          </Typography>
        ) : (
          <Box>
            {sortedPreferences.map((preference) => (
              <PreferenceCard
                key={preference.preferenceID}
                preference={preference}
                onDelete={handleDelete}
              />
            ))}
          </Box>
        )}
      </Paper>

      <CreatePreferenceDialog
        open={isCreateDialogOpen}
        onClose={() => setIsCreateDialogOpen(false)}
        onSubmit={handleCreatePreference}
      />
    </Box>
  );
};

export default MeetingPreferences;
