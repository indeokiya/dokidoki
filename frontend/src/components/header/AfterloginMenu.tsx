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
import MyPoint from './MyPoint';
import { useSnackbar } from 'notistack';
import { Logout } from 'src/hooks/logout';

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

  //컴포넌트 내부에 사용
  const { enqueueSnackbar } = useSnackbar(); //for alert stack
  // enqueueSnackbar('현재 최고가격이 갱신되어 입찰에 실패했습니다.', {variant: "info", anchorOrigin:{
  //   horizontal:"center",
  //   vertical:"top"
  // }})

   //소캣 연결 함수
   const connect = () => {
    // 연결할 때
    clientRef.current = new Client({
      brokerURL: `wss://j8a202.p.ssafy.io/api/notices/ws`,
      onConnect: () => {
        console.log('header socket connected');

        clientRef.current?.subscribe(`/topic/points/${userInfo.user_id}/realtime`, (message: Message) => {
          console.log(`Received message: ${message.body}`);
          
          const data = JSON.parse(message.body);
          console.log("data : ", data);
          if(data.point) setUserInfoState({ ...userInfo, point: data.point})
          
          if(data.type === "SELLER"){         // 판매자

            // 구매자가 돈 지불 성공 알림
            if(data.tradeSuccess){
              enqueueSnackbar(`${data.productName} 판매 성공, 획득 포인트 : +${data.earnedPoint}`, {variant: "success", anchorOrigin:{
                horizontal:"center",
                vertical:"top"
              }})
            }else{  // 구매자가 수수료 지급 혹은 정지
              // 수수료 획득 알림
              if(data.earnedPoint){
                enqueueSnackbar(`${data.productName} ${data.message} 획득 포인트 : +${data.earnedPoint}`, {variant: "info", anchorOrigin:{
                  horizontal:"center",
                  vertical:"top"
                }})
              }else{ // 구매자 정지 알림
                enqueueSnackbar(`${data.productName} ${data.message}`, {variant: "error", anchorOrigin:{
                  horizontal:"center",
                  vertical:"top"
                }})
              }
            }
          }else if(data.type === "BUYER"){    // 구매자
            console.log("구매 성공")
            enqueueSnackbar(`${data.productName} 구매 성공, 포인트 감소 : ${data.earnedPoint}`, {variant: "success", anchorOrigin:{
              horizontal:"center",
              vertical:"top"
            }})
          }else if(data.type === "PRISONER"){ // 이용 정지
            console.log("계정 정지")
            alert(data.message);
            Logout();
          }else if(data.type === "PENALTY"){  // 구매자 수수료 감소 알림
            console.log("패널티")
            enqueueSnackbar(`${data.productName} ${data.message} 감소 포인트 : ${data.earnedPoint}`, {variant: "error", anchorOrigin:{
              horizontal:"center",
              vertical:"top"
            }})
          }
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
  //const showPoint = (point: number) => point.toString().split( /(?=(?:\d{3})+(?:\.|$))/g ).join( "," )

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
          {/* 포인트 <StyledPoint>{showPoint(userInfo.point)}</StyledPoint> */}
          포인트 <MyPoint animation={false} increase={userInfo.point} max={userInfo.point}></MyPoint>
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