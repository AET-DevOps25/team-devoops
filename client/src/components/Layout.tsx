import React, { useState } from 'react';
import {
  Box,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  ListItemButton,
  Divider,
  useTheme,
  useMediaQuery,
} from '@mui/material';
import { useNavigate, useLocation } from 'react-router-dom';
import AppBar from './AppBar';
import DashboardIcon from '@mui/icons-material/Dashboard';
import EventNoteIcon from '@mui/icons-material/EventNote';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import Brightness7Icon from '@mui/icons-material/Brightness7';
import Brightness2Icon from '@mui/icons-material/Brightness2';
import devoopsLogo from '../assets/devoops_logo.png';

export const drawerWidth = 240;

interface LayoutProps {
  children: React.ReactNode;
  toggleColorMode: () => void;
  mode: 'light' | 'dark';
}

export const menuItems = [
  { text: 'Dashboard', icon: <DashboardIcon />, path: '/dashboard' },
  { text: 'Match Requests', icon: <EventNoteIcon />, path: '/matchrequests' },
  { text: 'Matches', icon: <RestaurantIcon />, path: '/matches' },
];

const Layout = ({ children, toggleColorMode, mode }: LayoutProps) => {
  const navigate = useNavigate();
  const location = useLocation();
  const theme = useTheme();
  const smDown = useMediaQuery(theme.breakpoints.down('sm'));
  const [mobileOpen, setMobileOpen] = useState(false);

  const handleDrawerToggle = () => setMobileOpen((prev) => !prev);

  const drawerContent = (
    <Box sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
      <List sx={{ flex: '1 1 auto' }}>
        {menuItems.map((item) => (
          <ListItem key={item.text} disablePadding>
            <ListItemButton
              selected={location.pathname === item.path}
              onClick={() => {
                navigate(item.path);
                if (smDown) setMobileOpen(false);
              }}
              sx={{
                '&.Mui-selected': {
                  backgroundColor: 'rgba(0, 0, 0, 0.08)',
                  '&:hover': {
                    backgroundColor: 'rgba(0, 0, 0, 0.12)',
                  },
                },
              }}
            >
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
      <img
        src={devoopsLogo}
        alt="devoops"
        style={{ width: '30%', margin: 'auto' }}
        onClick={() => navigate('/')}
      />
      <Divider />
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', p: 2 }}>
        <Box
          component="button"
          onClick={toggleColorMode}
          sx={{
            border: 'none',
            outline: 'none',
            borderRadius: '50%',
            width: smDown ? 36 : 48,
            height: smDown ? 36 : 48,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            backgroundColor: 'background.paper',
            boxShadow: 1,
            cursor: 'pointer',
            transition: 'background 0.2s',
            '&:hover': {
              backgroundColor: 'action.hover',
            },
            padding: 0,
          }}
        >
          {mode === 'dark' ? (
            <Box sx={{ color: 'white' }}>
              <Brightness7Icon color="inherit" fontSize={smDown ? 'small' : 'medium'} />
            </Box>
          ) : (
            <Brightness2Icon fontSize={smDown ? 'small' : 'medium'} />
          )}
        </Box>
      </Box>
    </Box>
  );

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar onHamburgerClick={handleDrawerToggle} />
      {/* Responsive Drawer */}
      {smDown ? (
        <Drawer
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{ keepMounted: true }}
          sx={{
            '& .MuiDrawer-paper': {
              width: drawerWidth,
              boxSizing: 'border-box',
              marginTop: '64px',
              borderRight: '1px solid rgba(0, 0, 0, 0.12)',
              height: 'calc(100% - 64px)',
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'space-between',
            },
          }}
        >
          {drawerContent}
        </Drawer>
      ) : (
        <Drawer
          variant="permanent"
          sx={{
            width: drawerWidth,
            flexShrink: 0,
            '& .MuiDrawer-paper': {
              width: drawerWidth,
              boxSizing: 'border-box',
              marginTop: '64px',
              borderRight: '1px solid rgba(0, 0, 0, 0.12)',
              height: 'calc(100% - 64px)',
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'space-between',
            },
          }}
          open
        >
          {drawerContent}
        </Drawer>
      )}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 2,
          width: { sm: `calc(100% - ${drawerWidth}px)` },
          marginTop: '64px',
        }}
      >
        {children}
      </Box>
    </Box>
  );
};

export default Layout;
