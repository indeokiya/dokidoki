import * as React from 'react';
import Divider from '@mui/material/Divider';

import List from '@mui/material/List';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import HomeIcon from '@mui/icons-material/Home';
import Drawer, { DrawerProps } from '@mui/material/Drawer';
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';
import NotificationsIcon from '@mui/icons-material/Notifications';
import SellIcon from '@mui/icons-material/Sell';
import BookmarkBorderOutlinedIcon from '@mui/icons-material/BookmarkBorderOutlined';
import SellOutlinedIcon from '@mui/icons-material/SellOutlined';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import Avatar from '@mui/material/Avatar';
import ProfileImgSrc from '../../assets/image/profile.png';
import Typography from '@mui/material/Typography';

import { useState } from 'react';

const item = {
  py: '2px',
  px: 3,
  color: 'rgba(255, 255, 255, 0.7)',
  '&:hover, &:focus': {
    bgcolor: 'rgba(255, 255, 255, 0.08)',
  },
};

const itemCategory = {
  boxShadow: '0 -1px 0 rgb(255,255,255,0.1) inset',
  py: 1.5,
  px: 3,
};

const MypageNavigator: React.FC<{
  setSelectedMenu: (data: string) => void;
  PaperProps: {};
  sx: {};
}> = (props: any) => {
  const [categories, setCategories] = useState([
    { id: '입찰 중', icon: <ShoppingCartOutlinedIcon />, active: true },
    { id: '구매 내역', icon: <ShoppingCartIcon />, active: false },
    { id: '판매 중', icon: <SellOutlinedIcon />, active: false },
    { id: '판매 내역', icon: <SellIcon />, active: false },
    { id: '관심 내역', icon: <BookmarkBorderOutlinedIcon />, active: false },
    { id: '알림 내역', icon: <NotificationsIcon />, active: false },
  ]);

  const activeHandler = (_id: string) => {
    props.setSelectedMenu(_id);
    setCategories((pre) =>
      pre.map((child) => {
        if (child.id === _id) {
          return { ...child, active: true };
        } else {
          return { ...child, active: false };
        }
      }),
    );
  };

  const { ...other } = props;

  return (
    <Drawer variant="permanent" {...other}>
      <List disablePadding>
        <ListItem sx={{ ...item, ...itemCategory, fontSize: 22, color: '#fff' }}>DOKIDOKI</ListItem>
        <ListItem >
          <Avatar
            src={ProfileImgSrc}
            sx={{ width: '150px', height: '150px', marginTop:"2rem"}}
          >
          </Avatar>
        </ListItem>
        <ListItem>
          <ListItemText sx={{ color: 'white', textAlign: 'center'}}>
            <Typography variant="subtitle1">김범식</Typography>
            
          </ListItemText>
              
          <ListItemText>

          </ListItemText>
        </ListItem>

        <Box sx={{ bgcolor: '#101F33', marginTop:"2rem" }}>
          <ListItem sx={{ py: 2, px: 3 }}>
            <ListItemText sx={{ color: '#fff' }}>Menu</ListItemText>
          </ListItem>

          {categories.map(({ id: childId, icon, active }) => (
            <ListItem
              disablePadding
              key={childId}
              onClick={() => {
                activeHandler(childId);
              }}
            >
              <ListItemButton selected={active} sx={item}>
                <ListItemIcon>{icon}</ListItemIcon>
                <ListItemText>{childId}</ListItemText>
              </ListItemButton>
            </ListItem>
          ))}
          <Divider sx={{ mt: 2 }} />
        </Box>
      </List>
    </Drawer>
  );
};

export default MypageNavigator;
