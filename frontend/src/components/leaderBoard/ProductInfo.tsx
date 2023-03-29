import Divider from '@mui/material/Divider';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import styled from 'styled-components';
import { useState } from 'react';
import IconButton from '@mui/material/IconButton';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';

import TurnedInIcon from '@mui/icons-material/TurnedIn';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';

import { bidAPI, auctionAPI } from '../../api/axios'
import { userInfoState } from 'src/store/userInfoState';

import { useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router';
// public class AuctionBidReq {
//   private int currentHighestPrice;
//   private int currentPriceSize;
// }

type Props = {
  auction_title: string;
  auction_id: any;
  category: string;
  offer_price: number;
  price_size: number;
  highest_price: number;
}

const ProductInfo = ({auction_title, auction_id, category, offer_price, price_size, highest_price}: Props) => {
  const navigate = useNavigate();
  const dataLeft = ['카테고리', '남은시간', '시작가격', '경매단위'];
  const dataRight = [category, '20:00:10', offer_price, price_size];
  const userInfo = useRecoilValue(userInfoState);
  const [bookmark, setBookmark] = useState(false);

  const bid = () => {
    if (!userInfo.is_logged_in) {
      alert("먼저 로그인해주세요.")
      navigate("/login");
    }

    const axios = bidAPI;
    axios.post(
      `auctions/${auction_id}/bid`,
      {current_highest_price: highest_price, current_price_size: price_size, name: userInfo.name },
    ).then(res => { // 성공 로직
      console.log("입찰 성공 res >> ", res);
      alert(`${highest_price + price_size}원에 입찰에 성공했습니다.`)
    }).catch(err => { // 실패 로직
      console.log(err)
      const error_message = err.response.data.message;
      if (error_message === "Different Highest Price") {
        alert("현재 최고가격이 갱신되어 입찰에 실패했습니다.");
      } else if (error_message === "Different Price Size") {
        alert("경매 단위가 수정되었습니다. 다시 시도하세요.");
      } else if (error_message === "Already Ended") {
        alert("이미 종료된 경매입니다.");
      } else {
        alert("알 수 없는 이유로 입찰에 실패했습니다.");
      }
    })
  }

  const changeBookmark = () => {
    if (bookmark) { // 찜을 해놓은 경우
      auctionAPI.delete(`interests/${auction_id}`)
      .then(res => {
        setBookmark(!bookmark)
      })
      .catch(err => {
        console.log(err)
      })
    } else {
      auctionAPI.post(`interests/${auction_id}`)
      .then(res => {
        setBookmark(!bookmark)
      })
      .catch(err => {
        console.log(err)
      })
    }
  }

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
              <Typography variant="subtitle1" key={i}>
                {data}
              </Typography>
            );
          })}
        </Grid>
        <Grid item xs={6} mt={4} mb={4}>
          {dataRight.map((data, i) => {
            return (
              <Typography variant="subtitle1" key={i}>
                {data}
              </Typography>
            );
          })}
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
          <Typography variant="h5" fontWeight={'bold'}>
            {' '}
            {highest_price}{' '}원
          </Typography>
          <Typography color="red"> +{price_size}원</Typography>
        </Grid>
      </Grid>
      <Stack spacing={2} direction="row" mt={5}>
        <Button 
          variant="contained" 
          sx={{ width: '50%', height: '50px' }}
          onClick={bid}>
          <StyledSpan>입찰하기</StyledSpan>
        </Button>
        <Button
          variant="outlined"
          sx={{ width: '50%', height: '50px' }}
          onClick={() => {
            changeBookmark()
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