import { Grid, Button, Avatar, Badge, Menu, MenuItem } from '@mui/material';
import styled from 'styled-components';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { userInfoState } from 'src/store/userInfoState';
import { useRecoilValue, useResetRecoilState } from 'recoil';

const AfterLoginMenu = () => {
  const navigate = useNavigate();
  const userInfo = useRecoilValue(userInfoState);
  const resetUserInfo = useResetRecoilState(userInfoState);

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const logout = () => {
    resetUserInfo();
    alert("성공적으로 로그아웃 되었습니다.");
    setAnchorEl(null);
  }

  return (
    <Grid container alignItems={'center'} spacing={1}>
      <Grid item>
        <Link to="/auction">
          <Button sx={{ textDecoration: 'none' }}> Auction </Button>
        </Link>
      </Grid>
      <Grid item>
        <p>{userInfo.name}님 환영합니다.</p>
      </Grid>
      <Grid item>
        <Button
          id="basic-button"
          aria-controls={open ? 'basic-menu' : undefined}
          aria-haspopup="true"
          aria-expanded={open ? 'true' : undefined}
          onClick={handleClick}
        >
          <StyledBadge
            overlap="circular"
            anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
            variant="dot"
          >
            <Avatar alt="Remy Sharp" src={userInfo.picture} />
          </StyledBadge>
        </Button>
        <Menu
          id="basic-menu"
          anchorEl={anchorEl}
          open={open}
          onClose={handleClose}
          MenuListProps={{
            'aria-labelledby': 'basic-button',
          }}
        >
          <MenuItem
            onClick={() => {
              handleClose();
              navigate('/mypage');
            }}
          >
            Mypage
          </MenuItem>
          <MenuItem onClick={logout}>Logout</MenuItem>
        </Menu>
      </Grid>
    </Grid>
  );
};

export default AfterLoginMenu;

const StyledBadge = styled(Badge)(({ theme }) => ({
  '& .MuiBadge-badge': {
    backgroundColor: '#44b700',
    color: '#44b700',
    boxShadow: `0 0 0 2px `,
    '&::after': {
      position: 'absolute',
      top: 0,
      left: 0,
      width: '100%',
      height: '100%',
      borderRadius: '50%',
      animation: 'ripple 1.2s infinite ease-in-out',
      border: '1px solid currentColor',
      content: '""',
    },
  },
  '@keyframes ripple': {
    '0%': {
      transform: 'scale(.8)',
      opacity: 1,
    },
    '100%': {
      transform: 'scale(2.4)',
      opacity: 0,
    },
  },
}));