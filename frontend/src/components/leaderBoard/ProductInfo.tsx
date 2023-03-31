import Divider from '@mui/material/Divider';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import styled from 'styled-components';
import { useState, useEffect } from 'react';
import IconButton from '@mui/material/IconButton';
import Chip from '@mui/material/Chip';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';

import TurnedInIcon from '@mui/icons-material/TurnedIn';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';

import { bidAPI, auctionAPI } from '../../api/axios';
import { userInfoState } from 'src/store/userInfoState';

import { useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router';

// public class AuctionBidReq {
//   private int currentHighestPrice;
//   private int currentPriceSize;
// }

function numberFormat(price: number | null) {
  return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
}

//초를 시분 초로 변경해줌
function formatSeconds(seconds: number): string {
  const hours = Math.floor(seconds / 3600);
  const minutes = Math.floor((seconds % 3600) / 60);
  const remainingSeconds = seconds % 60;

  const hoursStr = hours.toString().padStart(2, '0');
  const minutesStr = minutes.toString().padStart(2, '0');
  const secondsStr = remainingSeconds.toString().padStart(2, '0');

  return `${hoursStr}:${minutesStr}:${secondsStr}`;
}

type Props = {
  auction_title: string;
  auction_id: any;
  category: string;
  offer_price: number;
  price_size: number;
  highest_price: number;
  is_my_interest: boolean;
  end_time: string;
  start_time: string;
};

const ProductInfo = ({
  auction_title,
  auction_id,
  category,
  offer_price,
  price_size,
  highest_price,
  is_my_interest,
  end_time,
  start_time,
}: Props) => {
  const navigate = useNavigate();
  const dataLeft = ['카테고리', '남은시간', '시작가격', '경매단위'];
  const userInfo = useRecoilValue(userInfoState);
  const [bookmark, setBookmark] = useState(is_my_interest);

  console.log('highest_price >> ', highest_price);
  console.log('price_size >> ', price_size);
  console.log('offer_price >> ', offer_price);

  function TimeFormat(start: string, end: string) {
    // 두 시간 문자열
    const timeStr1 = end;
    const timeStr2 = start;

    // Date 객체로 변환
    const time1 = new Date(timeStr1);
    const time2 = new Date(timeStr2);

    // 두 Date 객체의 차이 계산 (밀리초 단위)
    const timeDiff = time1.getTime() - time2.getTime();

    // 초 단위로 변환
    const seconds = Math.floor(timeDiff / 1000);

    return seconds;
  }
  const [second, setSecond] = useState(TimeFormat(start_time, end_time));

  useEffect(() => {
    setTimeout(() => {
      setSecond((pre) => pre - 1);
    }, 1000);

    return () => clearInterval(second);
  }, [second]);

  const bid = () => {
    if (!userInfo.is_logged_in) {
      alert('먼저 로그인해주세요.');
      navigate('/login');
    }

    const axios = bidAPI;
    axios
      .post(`auctions/${auction_id}/bid`, {
        current_highest_price: highest_price,
        current_price_size: price_size,
        name: userInfo.name,
      })
      .then((res) => {
        // 성공 로직
        console.log('입찰 성공 res >> ', res);
        alert(`${highest_price + price_size}원에 입찰에 성공했습니다.`);
      })
      .catch((err) => {
        // 실패 로직
        console.log(err);
        const error_message = err.response.data.message;
        if (error_message === 'Different Highest Price') {
          alert('현재 최고가격이 갱신되어 입찰에 실패했습니다.');
        } else if (error_message === 'Different Price Size') {
          alert('경매 단위가 수정되었습니다. 다시 시도하세요.');
        } else if (error_message === 'Already Ended') {
          alert('이미 종료된 경매입니다.');
        } else {
          alert('알 수 없는 이유로 입찰에 실패했습니다.');
        }
      });
  };

  //찜함수
  const changeBookmark = () => {
    if (bookmark) {
      // 찜을 해놓은 경우
      auctionAPI
        .delete(`interests/${auction_id}`)
        .then((res) => {
          setBookmark(!bookmark);
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      auctionAPI
        .post(`interests/${auction_id}`)
        .then((res) => {
          setBookmark(!bookmark);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <div>
      {userInfo.is_logged_in && (
        <StyeldDiv>
          <IconButton>
            <EditOutlinedIcon />
          </IconButton>
        </StyeldDiv>
      )}
      <StyledH1>{auction_title}</StyledH1>

      <Divider />
      <Grid container>
        <Grid item xs={6} mt={4} mb={4}>
          {dataLeft.map((data, i) => {
            return (
              <Typography variant="subtitle1" key={i} mb="1px">
                {data}
              </Typography>
            );
          })}
        </Grid>
        <Grid item xs={6} mt={4} mb={4}>
          <Chip label={category} variant="outlined" />
          <Typography variant="subtitle1" sx={{ color: '#3A77EE' }}>
            {formatSeconds(second)}
          </Typography>
          <Typography variant="subtitle1">{numberFormat(offer_price)}</Typography>
          <Typography variant="subtitle1">{numberFormat(price_size)}</Typography>
        </Grid>
        <Grid item xs={12}>
          <Divider />
        </Grid>
        <Grid item xs={6} mt={2}>
          <Typography variant="h6" fontWeight={'bold'}>
            현재 가격 :{' '}
          </Typography>
        </Grid>
        <Grid item xs={6} mt={2}>
          <Typography variant="h6" fontWeight={'bold'}>
            {numberFormat(highest_price)}
          </Typography>
          <Typography color="red"> (+{numberFormat(highest_price - offer_price)})</Typography>
        </Grid>
      </Grid>
      <Stack spacing={2} direction="row" mt={3}>
        <Button variant="contained" sx={{ width: '50%', height: '50px' }} onClick={bid}>
          <StyledSpan>입찰하기</StyledSpan>
        </Button>
        <Button
          variant="outlined"
          sx={{ width: '50%', height: '50px' }}
          onClick={() => {
            changeBookmark();
          }}
        >
          <StyledSpan>찜하기 </StyledSpan>
          {bookmark ? <TurnedInIcon fontSize="large" /> : <TurnedInNotIcon fontSize="large" />}
        </Button>
      </Stack>
    </div>
  );
};

export default ProductInfo;

const StyledH1 = styled.h1`
  margin-top: 5px;
`;

const StyledSpan = styled.span`
  margin-right: 10px;
  font-size: 20px;
`;

const StyeldDiv = styled.div`
  text-align: right;
  padding-right: 5px;
  box-sizing: border-box;
  margin: 0px;
`;
