import * as React from 'react';
import Divider from '@mui/material/Divider';

import List from '@mui/material/List';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Drawer, { DrawerProps } from '@mui/material/Drawer';
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';
import NotificationsIcon from '@mui/icons-material/Notifications';
import SellIcon from '@mui/icons-material/Sell';
import BookmarkBorderOutlinedIcon from '@mui/icons-material/BookmarkBorderOutlined';
import SellOutlinedIcon from '@mui/icons-material/SellOutlined';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import Avatar from '@mui/material/Avatar';
import ProfileImgSrc from '../../assets/image/profile.png';
import { Typography, Badge } from '@mui/material';
import styled from 'styled-components';
import { useState } from 'react';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import { useNavigate } from 'react-router';
import Tooltip from '@mui/material/Tooltip';

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
  const navigate = useNavigate();

  const [categories, setCategories] = useState([
    { id: '입찰 중', icon: <ShoppingCartOutlinedIcon />, active: true, path: 'action-item' },
    { id: '구매 내역', icon: <ShoppingCartIcon />, active: false, path: 'action-history' },
    { id: '판매 중', icon: <SellOutlinedIcon />, active: false, path: 'sale-item' },
    { id: '판매 내역', icon: <SellIcon />, active: false, path: 'sale-history' },
    { id: '관심 내역', icon: <BookmarkBorderOutlinedIcon />, active: false, path: 'bookmark-list' },
    { id: '알림 내역', icon: <NotificationsIcon />, active: false, path: 'alert-history' },
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

  const StyledDiv = styled.div`
    text-align: center;
  `;

  const StyledEditIcon = styled.div`
    color: white;
    background-color: gray;
    border: 1px solid white;
    border-radius: 100px;
    width: 40px;
    height: 40px;
    line-height: 55px;
    transition: 0.5s;
    &:hover {
      background-color: silver;
    }
  `;

  const ImageInput = styled.input`
    visibility: hidden;
  `;

  return (
    <Drawer variant="permanent" {...other}>
      <List disablePadding>
        <ListItem
          sx={{ ...item, ...itemCategory, fontSize: 22, color: '#fff' }}
          onClick={() => {
            navigate('/');
          }}
        >
          DOKIDOKI
        </ListItem>
        <StyledDiv>
          <Badge
            overlap="circular"
            anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
            badgeContent={
              <StyledEditIcon>
                <Tooltip title="수정">
                  <label htmlFor="profileImgChange">
                    <EditOutlinedIcon />
                  </label>
                </Tooltip>
                <ImageInput type="file" id="profileImgChange"></ImageInput>
              </StyledEditIcon>
            }
          >
            <Avatar
              src={ProfileImgSrc}
              sx={{ width: '150px', height: '150px', margin: '1rem auto' }}
            ></Avatar>
          </Badge>

          {/* <label htmlFor="imageInput">
              <ImageInputLabel>
                <AddAPhotoIcon />
                {imageCnt}/5
              </ImageInputLabel>
            </label> */}

          <Typography color="white" variant="subtitle1">
            김범식
          </Typography>
        </StyledDiv>
        <ListItem>
          <ListItemText></ListItemText>
        </ListItem>

        <Box sx={{ bgcolor: '#101F33', marginTop: '2rem' }}>
          <ListItem sx={{ py: 2, px: 3 }}>
            <ListItemText sx={{ color: '#fff' }}>Menu</ListItemText>
          </ListItem>

          {categories.map(({ id: childId, icon, active, path }) => (
            <ListItem
              disablePadding
              key={childId}
              onClick={() => {
                activeHandler(childId);
              }}
            >
              <ListItemButton
                selected={active}
                sx={item}
                onClick={() => {
                  navigate(path);
                }}
              >
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
