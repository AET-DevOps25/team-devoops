import React, { useState } from 'react';
import {
  AppBar as MuiAppBar,
  Box,
  Toolbar,
  IconButton,
  Typography,
  Menu,
  Container,
  Avatar,
  Tooltip,
  MenuItem,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import mensaLogo from '../assets/meet@mensa_transparent.svg';
import { useTheme, useMediaQuery } from '@mui/material';

const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];

interface AppBarProps {
  onHamburgerClick?: () => void;
}

const AppBar: React.FC<AppBarProps> = ({ onHamburgerClick }) => {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const { logout } = useAuth();
  const navigate = useNavigate();
  const theme = useTheme();
  const smDown = useMediaQuery(theme.breakpoints.down('sm'));

  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleMenuClick = (setting: string) => {
    handleCloseUserMenu();
    switch (setting) {
      case 'Profile':
        navigate('/profile');
        break;
      case 'Account':
        navigate('/account');
        break;
      case 'Dashboard':
        navigate('/dashboard');
        break;
      case 'Logout':
        logout();
        navigate('/login');
        break;
      default:
        break;
    }
  };

  return (
    <MuiAppBar
      position="fixed"
      sx={{
        backgroundColor: 'white',
        boxShadow: 'none',
        borderBottom: '1px solid #e0e0e0',
        zIndex: (theme) => theme.zIndex.drawer + 1,
      }}
    >
      <Toolbar disableGutters sx={{ px: 0, minHeight: 64 }}>
        {/* Hamburger Button (nur mobil) */}
        {smDown && (
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={onHamburgerClick}
            sx={{ ml: 1, mr: 1 }}
          >
            <MenuIcon sx={{ color: 'black' }} />
          </IconButton>
        )}
        {/* Logo ganz links */}
        <Box sx={{ flexGrow: 0, display: 'flex', alignItems: 'center', pl: 2 }}>
          <img
            src={mensaLogo}
            alt="Meet@Mensa"
            style={{ height: '40px', cursor: 'pointer' }}
            onClick={() => navigate('/')}
          />
        </Box>

        {/* Spacer to push user menu to the right */}
        <Box sx={{ flexGrow: 1 }} />

        {/* User menu ganz rechts */}
        <Box sx={{ flexGrow: 0, pr: 2 }}>
          <Tooltip title="Open settings">
            <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
              <Avatar alt="User Avatar" src="/static/images/avatar/2.jpg" />
            </IconButton>
          </Tooltip>
          <Menu
            sx={{ mt: '45px' }}
            id="menu-appbar"
            anchorEl={anchorElUser}
            anchorOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            keepMounted
            transformOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            open={Boolean(anchorElUser)}
            onClose={handleCloseUserMenu}
          >
            {settings.map((setting) => (
              <MenuItem key={setting} onClick={() => handleMenuClick(setting)}>
                <Typography textAlign="center">{setting}</Typography>
              </MenuItem>
            ))}
          </Menu>
        </Box>
      </Toolbar>
    </MuiAppBar>
  );
};

export default AppBar;
