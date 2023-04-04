import { Grid, Button, Avatar, Badge, Menu, MenuItem, Paper } from '@mui/material';
import { BadgeProps } from '@mui/material';
import styled from 'styled-components';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { userInfoState } from 'src/store/userInfoState';
import { useEffect, useRef } from 'react';
import { useRecoilState, useRecoilValue, useResetRecoilState } from 'recoil';
import { noticeAPI } from 'src/api/axios';
import { Client, Message, StompHeaders } from '@stomp/stompjs';

const AfterLoginMenu = () => {
  const navigate = useNavigate();
  const [userInfo, setUserInfoState] = useRecoilState(userInfoState);
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

  let clientRef = useRef<Client>();

  useEffect(() => {
    if (!clientRef.current) connect();
    return () => disconnect();
  }, []);

   //소캣 연결 함수
   const connect = () => {
    // 연결할 때
    clientRef.current = new Client({
      brokerURL: `wss://j8a202.p.ssafy.io/api/notices/ws`,
      onConnect: () => {
        console.log('header socket connected');

        clientRef.current?.subscribe(`/topic/points/${userInfo.user_id}/realtime`, (message: Message) => {
          console.log(`Received message: ${message.body}`);
          setUserInfoState({ ...userInfo, point: JSON.parse(message.body).updated_point});
        });
      },
    });
    clientRef.current?.activate(); // 클라이언트 활성화
  };

  const disconnect = () => {
    // 연결이 끊겼을 때
    clientRef.current?.deactivate();
    console.log('header socket disconnected');
  };


  // 정수 포맷팅
  const showPoint = (point: number) => point.toString().split( /(?=(?:\d{3})+(?:\.|$))/g ).join( "," )

  return (
    <Grid container alignItems={'center'} spacing={1}>
      <Grid item>   
          <Button sx={{ textDecoration: 'none' }} onClick={()=>{
            navigate("/auction")
          }}> Auction </Button>
      </Grid>
      <Grid item>
        <p>
          <b>{userInfo.name}</b>님
          <span style={{ margin: "0 8px", color: "gray" }}>|</span>
          포인트 <StyledPoint>{showPoint(userInfo.point)}</StyledPoint>
        </p>
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
              navigate('/mypage/alert-history');
            }}
          >
            <span style={{marginRight: "8px"}}>Alert</span>
            <StyledDropdownBadge
              key={badgeKey}
              badgeContent={alertCnt}
              anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
            />
          </MenuItem>
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

const StyledDropdownBadge = styled(Badge)<BadgeProps>(({ theme }) => ({
  '& .MuiBadge-badge': {
    backgroundColor: 'red',
    color: 'red',
    scale: "0.5"
  },
}));

const StyledPoint = styled.span`
  color: #3A77EE;
  font-family: WorkSans;
  font-weight: bold;
`;