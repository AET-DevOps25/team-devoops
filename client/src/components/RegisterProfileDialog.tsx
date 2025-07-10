import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  MenuItem,
  Box,
  Chip,
  Typography,
} from '@mui/material';
import { useAuthenticatedApi } from '../services/api';

interface RegisterProfileDialogProps {
  open: boolean;
  email: string;
  authID: string;
  onSuccess: (userID: string) => void;
}

const DEGREE_OPTIONS = ['bsc_informatics', 'msc_informatics', 'bsc_math', 'msc_math', 'other'];
const GENDER_OPTIONS = ['male', 'female', 'other'];

const RegisterProfileDialog: React.FC<RegisterProfileDialogProps> = ({
  open,
  email,
  authID,
  onSuccess,
}) => {
  const api = useAuthenticatedApi();
  const [form, setForm] = useState({
    firstname: '',
    lastname: '',
    birthday: '',
    gender: 'other',
    degree: '',
    degreeStart: 2024,
    interests: [] as string[],
    bio: '',
  });
  const [emailInput, setEmailInput] = useState(email || '');
  const [emailError, setEmailError] = useState('');
  const [interestInput, setInterestInput] = useState('');
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');

  const validateEmail = (email: string) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setEmailInput(value);
    if (value && !validateEmail(value)) {
      setEmailError('Please enter a valid email address');
    } else {
      setEmailError('');
    }
  };

  const handleChange = (field: string) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm((prev) => ({ ...prev, [field]: e.target.value }));
  };
  const handleAddInterest = () => {
    const value = interestInput.trim();
    if (value && !form.interests.includes(value)) {
      setForm((prev) => ({ ...prev, interests: [...prev.interests, value] }));
      setInterestInput('');
    }
  };
  const handleRemoveInterest = (interest: string) => {
    setForm((prev) => ({ ...prev, interests: prev.interests.filter((i) => i !== interest) }));
  };
  const handleSubmit = async () => {
    if (!emailInput.trim()) {
      setEmailError('Email is required');
      return;
    }
    if (!validateEmail(emailInput)) {
      setEmailError('Please enter a valid email address');
      return;
    }

    setSaving(true);
    setError('');
    try {
      const payload = {
        authID,
        email: emailInput,
        ...form,
      };
      const res = await api.post('/api/v2/user/register', payload);
      onSuccess(res.data.userID);
    } catch (e: any) {
      setError(e?.response?.data?.message || 'Registration failed');
    } finally {
      setSaving(false);
    }
  };

  return (
    <Dialog open={open} maxWidth="sm" fullWidth>
      <DialogTitle>Complete Your Profile</DialogTitle>
      <DialogContent>
        <Box display="flex" flexDirection="column" gap={2} mt={1}>
          <TextField
            label="Email"
            type="email"
            value={emailInput}
            onChange={handleEmailChange}
            error={!!emailError}
            helperText={emailError}
            required
          />
          <TextField
            label="First Name"
            value={form.firstname}
            onChange={handleChange('firstname')}
            required
          />
          <TextField
            label="Last Name"
            value={form.lastname}
            onChange={handleChange('lastname')}
            required
          />
          <TextField
            label="Birthday"
            type="date"
            value={form.birthday}
            onChange={handleChange('birthday')}
            InputLabelProps={{ shrink: true }}
            required
          />
          <TextField
            select
            label="Gender"
            value={form.gender}
            onChange={handleChange('gender')}
            required
          >
            {GENDER_OPTIONS.map((g) => (
              <MenuItem key={g} value={g}>
                {g}
              </MenuItem>
            ))}
          </TextField>
          <TextField
            select
            label="Degree"
            value={form.degree}
            onChange={handleChange('degree')}
            required
          >
            {DEGREE_OPTIONS.map((d) => (
              <MenuItem key={d} value={d}>
                {d}
              </MenuItem>
            ))}
          </TextField>
          <TextField
            label="Degree Start Year"
            type="number"
            value={form.degreeStart}
            onChange={handleChange('degreeStart')}
            inputProps={{ min: 2000, max: 2100 }}
            required
          />
          <Box>
            <Typography variant="subtitle2">Interests</Typography>
            <Box display="flex" gap={1} mt={1}>
              <TextField
                size="small"
                value={interestInput}
                onChange={(e) => setInterestInput(e.target.value)}
                placeholder="Add interest"
                onKeyDown={(e) => {
                  if (e.key === 'Enter') handleAddInterest();
                }}
              />
              <Button onClick={handleAddInterest} disabled={!interestInput.trim()}>
                Add
              </Button>
            </Box>
            <Box display="flex" flexWrap="wrap" gap={1} mt={1}>
              {form.interests.map((interest, idx) => (
                <Chip key={idx} label={interest} onDelete={() => handleRemoveInterest(interest)} />
              ))}
            </Box>
          </Box>
          <TextField
            label="Bio"
            value={form.bio}
            onChange={handleChange('bio')}
            multiline
            minRows={2}
            required
          />
          {error && <Typography color="error">{error}</Typography>}
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleSubmit} variant="contained" disabled={saving}>
          Register
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default RegisterProfileDialog;
