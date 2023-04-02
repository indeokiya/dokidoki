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
import BidButton from './bidButton/BidButton';
import ProductLeaderBoard from './ProductLeaderBoard';

function numberFormat(price: number | null) {
  return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
}



type Props = {
  auction_title: string;
  auction_id: any;
  seller_id: string;
  category: string;
  offer_price: number;
  price_size: number;
  highest_price: number;
  is_my_interest: boolean;
  end_time: string;
  start_time: string;
  description: string;
  meeting_place: string;
  product_name: string;
  seller_name: string;
};

const ProductInfo = ({
  auction_title,
  auction_id,
  seller_id,
  category,
  offer_price,
  price_size,
  highest_price,
  is_my_interest,
  end_time,
  start_time,
  description,
  meeting_place,
  product_name,
  seller_name,
}: Props) => {
  const navigate = useNavigate();
  const dataLeft = ['작성자', '시작가격', '경매단위', '제품명'];
  const userInfo = useRecoilValue(userInfoState);
  const [bookmark, setBookmark] = useState(is_my_interest);

  function TimeFormat(end: string) {
    // 두 시간 문자열
    // Date 객체로 변환
    const time1 = new Date(end);
    const time2 = new Date();

    // 두 Date 객체의 차이 계산 (밀리초 단위)
    const timeDiff = time1.getTime() - time2.getTime();

    // 초 단위로 변환
    const seconds = Math.floor(timeDiff / 1000);

    return seconds;
  }
  const [second, setSecond] = useState(TimeFormat(end_time));

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

    console.warn('seller >>', seller_id, ', user id >> ', userInfo.user_id);
    if (seller_id === userInfo.user_id) {
      alert('내 경매는 입찰할 수 없습니다.');
      return;
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

  const updateData = {
    auction_id: auction_id,
    title: auction_title,
    description: description,
    price_size: price_size,
    meeting_place: meeting_place,
  };

  const updateAuction = () => {
    navigate(`/auction/update/${auction_id}`, { state: updateData });
  };

  return (
    <StyledBox>
      {userInfo.user_id === seller_id && (
        <StyeldDiv onClick={updateAuction}>
          <IconButton>
            <EditOutlinedIcon />
          </IconButton>
        </StyeldDiv>
      )}
      <Chip label={category} sx={{ color: 'bluegray' }} variant="outlined" />
      <StyledH1>{auction_title}</StyledH1>

      <Grid container>
        <Grid item xs={2} mt={4} mb={4}>
          {dataLeft.map((data, i) => {
            return (
              <Typography variant="subtitle1" key={i} mb="1px" textAlign={'end'} sx={{fontSize:"0.9rem"}}>
                {data} :
              </Typography>
            );
          })}
        </Grid>
        <Grid xs={1} />
        <Grid item xs={9} mt={4} mb={4} height={"122px"}>
          <Typography variant="subtitle1"sx={{fontSize:"0.9rem"}}>{seller_name}</Typography>
          <Typography variant="subtitle1"sx={{fontSize:"0.9rem"}} color="error">{numberFormat(offer_price)}</Typography>
          <Typography variant="subtitle1"sx={{fontSize:"0.9rem"}}>{numberFormat(price_size)}</Typography>
          <Typography variant="caption"sx={{fontSize:"0.9rem"}}>{product_name}</Typography>
        </Grid>
        <Grid item xs={12}></Grid>
        <ProductLeaderBoard></ProductLeaderBoard>
        {/* <Grid item xs={6} mt={2}>
          <Typography variant="h6" fontWeight={'bold'}>
            현재 가격 :{' '}
          </Typography>
        </Grid>
        <Grid item xs={6} mt={2}>
          <Typography variant="h6" fontWeight={'bold'}>
            {numberFormat(highest_price)}
          </Typography>
          <Typography color="red"> (+{numberFormat(highest_price - offer_price)})</Typography>
        </Grid> */}
      </Grid>
      <Stack spacing={2} direction="row" mt={3}>
        <BidButton bid={bid} />
        <IconButton
          sx={{ width: '10%', height: '40px' }}
          onClick={() => {
            changeBookmark();
          }}
        >
          {bookmark ? (
            <TurnedInIcon fontSize="large" color="primary" />
          ) : (
            <TurnedInNotIcon fontSize="large" color="primary" />
          )}
        </IconButton>
      </Stack>
    </StyledBox>
  );
};

export default ProductInfo;

const StyledH1 = styled.h1`
  margin-top: 5px;
  margin-bottom: 0px;
`;

const StyledBox = styled.div`
  margin-bottom:30px;
`

const StyeldDiv = styled.div`
  text-align: right;
  padding-right: 5px;
  box-sizing: border-box;
  margin: 0px;
`;
