import React, { useState } from 'react';
import {
  AppBar as MuiAppBar,
  Box,
  Toolbar,
  IconButton,
  Typography,
  Menu,
  Avatar,
  Tooltip,
  MenuItem,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import mensaLogo from '../assets/meet@mensa_transparent.svg';
import { useTheme, useMediaQuery } from '@mui/material';

const settings = ['Profile', 'Logout'];

interface AppBarProps {
  onHamburgerClick?: () => void;
}

const AppBar: React.FC<AppBarProps> = ({ onHamburgerClick }) => {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const { logout, user } = useAuth0();
  const navigate = useNavigate();
  const theme = useTheme();
  const smDown = useMediaQuery(theme.breakpoints.down('sm'));

  // menu for clicking on the user avatar
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
      case 'Logout':
        logout({ logoutParams: { returnTo: `${window.location.origin}` } });
        break;
      default:
        break;
    }
  };

  return (
    <MuiAppBar
      position="fixed"
      sx={{
        backgroundColor: theme.palette.background.paper,
        color: theme.palette.text.primary,
        boxShadow: 'none',
        borderBottom: `1px solid ${theme.palette.divider}`,
        zIndex: (theme) => theme.zIndex.drawer + 1,
      }}
    >
      <Toolbar disableGutters sx={{ px: 0, minHeight: 64 }}>
        {/* Display hamburger button only on mobile */}
        {smDown && (
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={onHamburgerClick}
            sx={{ ml: 1, mr: 1 }}
          >
            <MenuIcon
              sx={{ color: (theme) => (theme.palette.mode === 'dark' ? 'white' : 'black') }}
            />
          </IconButton>
        )}
        {/* Meet@Mensa logo on the left side of the AppBar */}
        <Box sx={{ flexGrow: 0, display: 'flex', alignItems: 'center', pl: 2 }}>
          <img
            src={mensaLogo}
            alt="Meet@Mensa"
            style={{
              height: smDown ? '30px' : '40px',
              marginTop: smDown ? '-15px' : 0,
              cursor: 'pointer',
            }}
            onClick={() => navigate('/')}
          />
        </Box>

        {/* Spacer to push user menu to the right */}
        <Box sx={{ flexGrow: 1 }} />

        {/* User menu on the right side of the AppBar */}
        <Box sx={{ flexGrow: 0, pr: 2 }}>
          <Tooltip title="Open settings">
            <IconButton data-testid="user-menu" onClick={handleOpenUserMenu} sx={{ p: 0 }}>
              <Avatar
                src={user?.picture}
                sx={{
                  color: (theme) => (theme.palette.mode === 'dark' ? 'white' : 'black'),
                  bgcolor: (theme) =>
                    theme.palette.mode === 'dark'
                      ? theme.palette.grey[800]
                      : theme.palette.grey[200],
                }}
              ></Avatar>
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
              <MenuItem
                key={setting}
                data-testid={`menu-item-${setting.toLowerCase()}`}
                onClick={() => handleMenuClick(setting)}
              >
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
