import * as React from 'react';
import Divider from '@mui/material/Divider';

import List from '@mui/material/List';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Drawer from '@mui/material/Drawer';
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';
import NotificationsIcon from '@mui/icons-material/Notifications';
import SellIcon from '@mui/icons-material/Sell';
import BookmarkBorderOutlinedIcon from '@mui/icons-material/BookmarkBorderOutlined';
import SellOutlinedIcon from '@mui/icons-material/SellOutlined';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import Avatar from '@mui/material/Avatar';
import { Typography, Badge } from '@mui/material';
import styled from 'styled-components';
import { useState } from 'react';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import { useNavigate } from 'react-router';
import Tooltip from '@mui/material/Tooltip';
import { userAPI } from 'src/api/axios';
import { useRecoilState } from 'recoil';
import { userInfoState, myPageMenuState } from 'src/store/userInfoState';

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
  py: 0.82,
  px: 3,
};

const MypageNavigator: React.FC<{
  setSelectedMenu: (data: string) => void;
  PaperProps: {};
  sx: {};
}> = (props: any) => {
  const navigate = useNavigate();

  const [menu, setMenu] = useRecoilState(myPageMenuState);

  const [categories, setCategories] = useState([
    {
      id: '입찰 중',
      icon: <ShoppingCartOutlinedIcon />,
      active: menu.menu === '입찰 중',
      path: '',
    },
    {
      id: '구매 내역',
      icon: <ShoppingCartIcon />,
      active: menu.menu === '구매 내역',
      path: 'action-history',
    },
    {
      id: '판매 중',
      icon: <SellOutlinedIcon />,
      active: menu.menu === '판매 중',
      path: 'sale-item',
    },
    {
      id: '판매 내역',
      icon: <SellIcon />,
      active: menu.menu === '판매 내역',
      path: 'sale-history',
    },
    {
      id: '관심 내역',
      icon: <BookmarkBorderOutlinedIcon />,
      active: menu.menu === '관심 내역',
      path: 'bookmark-list',
    },
    {
      id: '알림 내역',
      icon: <NotificationsIcon />,
      active: menu.menu === '알림 내역',
      path: 'alert-history',
    },
  ]);

  //이거 true세팅하기

  const [loginUser, setLoginUser] = useRecoilState(userInfoState);

  const handleUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log(e);
    const target = e.currentTarget;
    const files = (target.files as FileList)[0];
    const formData = new FormData();
    formData.append('file', files);

    userAPI
      .put('/profiles', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((res) => {
        alert('성공');
        setLoginUser({ ...loginUser, picture: res.data.data });
        console.log('res >> ', res);
      });
  };

  const activeHandler = (_id: string) => {
    setMenu((prevState) => ({ ...prevState, menu: _id }));
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
        <ListItem
          sx={{ ...item, ...itemCategory, fontSize: 22 }}
          onClick={() => {
            navigate('/');
          }}
        >
          <StyledLogo>DOKIDOKI</StyledLogo>
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
                <ImageInput
                  type="file"
                  accept="image/*"
                  id="profileImgChange"
                  onChange={handleUpload}
                ></ImageInput>
              </StyledEditIcon>
            }
          >
            <Avatar
              src={loginUser.picture}
              sx={{ width: '150px', height: '150px', margin: '1rem auto' }}
            ></Avatar>
          </Badge>

          <Typography color="white" variant="subtitle1">
            {loginUser.name}
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

const StyledLogo = styled.span`
  cursor: pointer;
  padding: 10px;
  font-weight: bold;
  font-size: 40px;
  background-image: linear-gradient(135deg, #e570e7 0%, #79f1fc 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
`;
