import { Grid, Button, Avatar, Badge, Menu, MenuItem } from '@mui/material';
import { BadgeProps } from '@mui/material';
import styled from 'styled-components';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { userInfoState } from 'src/store/userInfoState';
import { useEffect } from 'react';
import { useRecoilValue, useResetRecoilState } from 'recoil';
import { noticeAPI } from 'src/api/axios';

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
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("access_token");
    localStorage.removeItem("user_info");
    alert("성공적으로 로그아웃 되었습니다.");
    setAnchorEl(null);
  }
  const [alertCnt , setAlertCnt] = useState(0)
  const [alertMap, setAlertMap] = useState<any>({})
  const [badgeKey, setBadgeKey] = useState(0)

  const countAlert = (data:any) => {
    var cnt = 0
    Object.keys(data).map((key: string) => {
      if (! data[key].read) {
        cnt += 1;
      }
    })
    return cnt;
  }

  useEffect(()=> {
    noticeAPI.get("/")
    .then(({ data }) => {
      console.log('알림 내역 >> ', data)
      setAlertMap(data)
    })
    .catch((err) => {
      console.log(err)
    })

  }, [])
  
  useEffect(() => {
    setAlertCnt(countAlert(alertMap))
    setBadgeKey((prev) => prev + 1)
    console.log(alertCnt)
  }, [alertMap, alertCnt])

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
            key={badgeKey}
            // overlap="circular"
            badgeContent={alertCnt}
            max={9}
            anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
            // variant="dot"
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

const StyledBadge = styled(Badge)<BadgeProps>(({ theme }) => ({
  '& .MuiBadge-badge': {
    backgroundColor: '#44b700',
    color: '#fff',
    // boxShadow: `0 0 0 2px `,
    '&::after': {
      position: 'absolute',
      top: 0,
      left: 0,
      width: '100%',
      height: '100%',
      borderRadius: '50%',
      animation: 'ripple 1.2s infinite ease-in-out',
      border: '1px solid #4bb700',
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