import React, { useEffect, useState } from 'react';
import {
  Typography,
  Paper,
  Box,
  Avatar,
  IconButton,
  Button,
  TextField,
  Chip,
  Stack,
  CircularProgress,
  Grid,
  Select,
  MenuItem,
  InputLabel,
  FormControl,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import EmailIcon from '@mui/icons-material/Email';
import CakeIcon from '@mui/icons-material/Cake';
import WcIcon from '@mui/icons-material/Wc';
import SchoolIcon from '@mui/icons-material/School';
import InterestsIcon from '@mui/icons-material/Interests';
import InfoIcon from '@mui/icons-material/Info';
import AddIcon from '@mui/icons-material/Add';
import CloseIcon from '@mui/icons-material/Close';
import { useAuth0 } from '@auth0/auth0-react';
import { useAuthenticatedApi } from '../services/api';

interface UserProfile {
  userID: string;
  email: string;
  firstname: string;
  lastname: string;
  birthday: string;
  gender: string;
  degree: string;
  degreeStart: number;
  interests: string[];
  bio: string;
}

// const USE_PROFILE_MOCK = true;

// const mockProfile: UserProfile = {
//   userID: '123e4567-e89b-12d3-a456-426614174000',
//   email: 'jane.doe@example.com',
//   name: 'Jane Doe',
//   birthday: '1995-06-15',
//   gender: 'Female',
//   degree: 'Computer Science',
//   interests: ['AI', 'Cooking', 'Travel'],
//   bio: 'Hi! I am Jane and I love learning new things and meeting new people at the Mensa.',
// };

const Profile: React.FC = () => {
  const { user } = useAuth0();
  const api = useAuthenticatedApi();
  const [userID, setUserID] = useState<string | null>(null);
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [loading, setLoading] = useState(true);
  const [editMode, setEditMode] = useState(false);
  const [editData, setEditData] = useState<Partial<UserProfile>>({});
  const [saving, setSaving] = useState(false);
  const [interestInput, setInterestInput] = useState('');
  // const [showRegisterDialog, setShowRegisterDialog] = useState(false);

  // First, get the userID from the /me endpoint
  useEffect(() => {
    if (!user?.sub) return;
    api
      .get(`/api/v2/user/me/${user.sub}`)
      .then((res) => {
        setUserID(res.data.userID);
      })
      .catch(() => {
        setUserID(null);
      });
  }, [user?.sub]); // Only depend on user.sub, not the entire user object

  // Then, fetch the profile using the userID
  useEffect(() => {
    if (!userID) {
      setLoading(false);
      return;
    }
    setLoading(true);
    api
      .get(`/api/v2/user/${userID}`)
      .then((res) => {
        setProfile(res.data);
        setEditData(res.data);
      })
      .catch(() => setProfile(null))
      .finally(() => setLoading(false));
  }, [userID]);

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="300px">
        <CircularProgress />
      </Box>
    );
  }

  if (!userID) {
    return (
      <Paper sx={{ p: 3 }}>
        <Typography variant="h6">
          No userID available. Please log in and complete registration.
        </Typography>
      </Paper>
    );
  }

  if (!profile) {
    return (
      <Paper sx={{ p: 3 }}>
        <Typography variant="h6">User profile could not be loaded.</Typography>
      </Paper>
    );
  }

  // Avatar initials: first letter of first and last name
  const getInitials = (firstname: string, lastname: string) => {
    const first = firstname?.charAt(0)?.toUpperCase() || '';
    const last = lastname?.charAt(0)?.toUpperCase() || '';
    return first + last;
  };

  // Handlers for editing
  const handleEdit = () => setEditMode(true);
  const handleCancel = () => {
    setEditData(profile);
    setEditMode(false);
  };
  const handleChange = (field: keyof UserProfile) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setEditData((prev) => ({ ...prev, [field]: e.target.value }));
  };
  const handleSave = async () => {
    setSaving(true);
    try {
      const { email, firstname, lastname, birthday, gender, degree, degreeStart, interests, bio } =
        {
          ...profile,
          ...editData,
        };
      const updated = {
        email,
        firstname,
        lastname,
        birthday,
        gender,
        degree,
        degreeStart,
        interests,
        bio,
      };
      await api.put(`/api/v2/user/${userID}`, updated);
      setProfile((prev) => ({ ...prev!, ...updated }) as UserProfile);
      setEditMode(false);
    } catch (e) {
      // handle error
    } finally {
      setSaving(false);
    }
  };
  // Handler to add interest
  const handleAddInterest = () => {
    const value = interestInput.trim();
    if (value.length > 0 && !editData.interests?.includes(value)) {
      setEditData((prev) => ({
        ...prev,
        interests: [...(prev.interests || []), value],
      }));
      setInterestInput('');
    }
  };
  // Handler to remove interest
  const handleRemoveInterest = (interest: string) => {
    setEditData((prev) => ({
      ...prev,
      interests: (prev.interests || []).filter((i) => i !== interest),
    }));
  };

  return (
    <Box maxWidth={500} mx="auto" mt={4}>
      <Paper sx={{ p: 4, position: 'relative' }}>
        {/* Edit Button */}
        {!editMode && (
          <IconButton
            aria-label="edit profile"
            onClick={handleEdit}
            sx={{ position: 'absolute', top: 16, right: 16 }}
          >
            <EditIcon />
          </IconButton>
        )}
        {/* Avatar */}

        <Box display="flex" flexDirection="column" alignItems="center" mb={2}>
          <Avatar sx={{ width: 80, height: 80, mb: 1, fontSize: 36 }}>
            {getInitials(profile.firstname, profile.lastname)}
          </Avatar>
          <Typography variant="h5" fontWeight={600} align="center">
            {profile.firstname} {profile.lastname}
          </Typography>
        </Box>
        <Stack spacing={2} mt={2}>
          {/* Email */}
          <Box display="flex" alignItems="center" gap={1}>
            <EmailIcon color="action" />
            <Typography>{profile.email}</Typography>
          </Box>
          {/* Birthday */}
          <Box display="flex" alignItems="center" gap={1}>
            <CakeIcon color="action" />
            {editMode ? (
              <TextField
                type="date"
                size="small"
                value={editData.birthday || ''}
                onChange={handleChange('birthday')}
                sx={{ minWidth: 180 }}
              />
            ) : (
              <Typography>{profile.birthday}</Typography>
            )}
          </Box>
          {/* Gender */}
          <Box display="flex" alignItems="center" gap={1}>
            <WcIcon color="action" />
            {editMode ? (
              <FormControl size="small" sx={{ minWidth: 120 }}>
                <InputLabel id="gender-label">Gender</InputLabel>
                <Select
                  labelId="gender-label"
                  value={editData.gender || ''}
                  label="Gender"
                  onChange={(e) => setEditData((prev) => ({ ...prev, gender: e.target.value }))}
                >
                  <MenuItem value="Male">Male</MenuItem>
                  <MenuItem value="Female">Female</MenuItem>
                  <MenuItem value="Divers">Divers</MenuItem>
                </Select>
              </FormControl>
            ) : (
              <Typography>{profile.gender}</Typography>
            )}
          </Box>
          {/* Degree */}
          <Box display="flex" alignItems="center" gap={1}>
            <SchoolIcon color="action" />
            {editMode ? (
              <TextField
                size="small"
                value={editData.degree || ''}
                onChange={handleChange('degree')}
                sx={{ minWidth: 180 }}
              />
            ) : (
              <Typography>{profile.degree}</Typography>
            )}
          </Box>
          {/* Interests */}
          {editMode ? (
            <Box display="flex" flexDirection="column" gap={1} alignItems="flex-start">
              <Box display="flex" alignItems="center" gap={1}>
                <InterestsIcon color="action" sx={{ alignSelf: 'center' }} />
                <TextField
                  size="small"
                  value={interestInput}
                  onChange={(e) => setInterestInput(e.target.value)}
                  placeholder="Add interest"
                  sx={{ minWidth: 120 }}
                  onKeyDown={(e) => {
                    if (e.key === 'Enter') handleAddInterest();
                  }}
                />
                <IconButton
                  size="small"
                  color="primary"
                  onClick={handleAddInterest}
                  disabled={interestInput.trim().length === 0}
                >
                  <AddIcon />
                </IconButton>
              </Box>
              <Box display="flex" flexWrap="wrap" gap={1} mt={1} sx={{ paddingLeft: '30px' }}>
                {(editData.interests || []).map((interest, idx) => (
                  <Chip
                    key={idx}
                    label={interest}
                    size="small"
                    onDelete={() => handleRemoveInterest(interest)}
                    deleteIcon={<CloseIcon />}
                  />
                ))}
              </Box>
            </Box>
          ) : (
            <Box display="flex" alignItems="center" gap={1}>
              <InterestsIcon color="action" />
              <Box display="flex" flexWrap="wrap" gap={1} pl={0}>
                {profile.interests.map((interest, idx) => (
                  <Chip key={idx} label={interest} size="small" />
                ))}
              </Box>
            </Box>
          )}
          {/* Bio */}
          <Box display="flex" alignItems="flex-start" gap={1}>
            <InfoIcon color="action" sx={{ mt: 0.5 }} />
            {editMode ? (
              <TextField
                size="small"
                value={editData.bio || ''}
                onChange={handleChange('bio')}
                multiline
                minRows={2}
                fullWidth
              />
            ) : (
              <Typography>{profile.bio}</Typography>
            )}
          </Box>
        </Stack>
        {/* Save/Cancel Buttons */}
        {editMode && (
          <Grid container spacing={2} mt={3} justifyContent="flex-end">
            <Grid item>
              <Button variant="outlined" onClick={handleCancel} disabled={saving}>
                Cancel
              </Button>
            </Grid>
            <Grid item>
              <Button variant="contained" onClick={handleSave} disabled={saving}>
                Save
              </Button>
            </Grid>
          </Grid>
        )}
      </Paper>
    </Box>
  );
};

export default Profile;
