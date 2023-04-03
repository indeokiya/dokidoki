import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import styled from 'styled-components';
import { useState } from 'react';
import IconButton from '@mui/material/IconButton';
import Chip from '@mui/material/Chip';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import Tooltip from '@mui/material/Tooltip';

import { bidAPI, auctionAPI } from '../../api/axios';
import { userInfoState } from 'src/store/userInfoState';

import { useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router';
import BidButton from './bidButton/BidButton';
import ProductLeaderBoard from './ProductLeaderBoard';
import { SocketBidData } from 'src/datatype/datatype';
import Button from '@mui/material/Button';
import CloseButton from './closeButton/CloseButton';

function numberFormat(price: number | null) {
  return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
}



type Props = {
  auction_title: string;
  auction_id: any;
  seller_id: string;
  category: string;
  offer_price: number;
  priceSize: number;
  highestPrice: number;
  is_my_interest: boolean;
  end_time: string;
  start_time: string;
  description: string;
  meeting_place: string;
  product_name: string;
  seller_name: string;
  setHighestPrice: (price: number) => void; //갱신된 입찰가

  leaderBoardData: SocketBidData[]; //리더보드 데이터
};

const ProductInfo = ({
  auction_title,
  auction_id,
  seller_id,
  category,
  offer_price,
  priceSize,
  highestPrice,
  is_my_interest,
  end_time,
  start_time,
  description,
  meeting_place,
  product_name,
  seller_name,
  leaderBoardData,
  setHighestPrice,
}: Props) => {
  const navigate = useNavigate();
  console.log('leaderBoardData >>> ', leaderBoardData);

  const loginUser = useRecoilValue(userInfoState);

  const dataLeft = ['작성자', '시작가격', '경매단위', '제품명'];
  const userInfo = useRecoilValue(userInfoState);
  const [bookmark, setBookmark] = useState(is_my_interest);

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
        current_highest_price: highestPrice,
        current_price_size: priceSize,
        name: userInfo.name,
      })
      .then((res) => {
        // 성공 로직
        console.log('입찰 성공 res >> ', res);
        alert(`${highestPrice + priceSize}원에 입찰에 성공했습니다.`);
        setHighestPrice(highestPrice + priceSize);

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

  const close = () => {
    if (!userInfo.is_logged_in) {
      alert('먼저 로그인해주세요.');
      navigate('/login');
    }
    console.warn('seller >>', seller_id, ', user id >> ', userInfo.user_id);
    if (seller_id !== userInfo.user_id) {
      alert('종료할 수 없습니다. 내 경매가 아닙니다.');
      return;
    }

    if (!window.confirm("정말로 경매를 종료하시겠습니까?")) {
      return;
    }
    const axios = bidAPI;
    axios
      .delete(`auctions/${auction_id}/close`)
      .then((res) => {
        // 성공 로직
        console.log('입찰 성공 res >> ', res);
        alert(`${highestPrice + priceSize}원에 입찰에 성공했습니다.`);
        setHighestPrice(highestPrice + priceSize);

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

  //찜 함수
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
    price_size: priceSize,
    meeting_place: meeting_place,
  };

  const updateAuction = () => {
    navigate(`/auction/update/${auction_id}`, { state: updateData });
  };

  return (
    <StyledBox>
      {/* {userInfo.user_id === seller_id && ( */}
      <StyeldDiv>
        <Tooltip title="수정">
          <IconButton onClick={updateAuction}>
            <EditOutlinedIcon sx={{ display: userInfo.user_id === seller_id ? '' : 'none' }} />
          </IconButton>
        </Tooltip>
        <Tooltip title="찜하기">
          <IconButton
            sx={{ width: '10%', height: '40px' }}
            onClick={() => {
              changeBookmark();
            }}
          >
            {bookmark ? (
              <TurnedInIcon fontSize="large" color="error" />
            ) : (
              <TurnedInNotIcon fontSize="large" color="error" />
            )}
          </IconButton>
        </Tooltip>
      </StyeldDiv>

      {/* )} */}
      <Chip label={category} sx={{ color: 'bluegray' }} variant="outlined" />
      <StyledH1>{auction_title}</StyledH1>

      <Grid container>
        <Grid item xs={2} mt={4} mb={4}>
          {dataLeft.map((data, i) => {
            return (
              <Typography
                variant="subtitle1"
                key={i}
                mb="1px"
                textAlign={'end'}
                sx={{ fontSize: '0.9rem' }}
              >
                {data} :
              </Typography>
            );
          })}
        </Grid>
        <Grid xs={1} />
        <Grid item xs={9} mt={4} mb={4} height={'122px'}>
          <Typography variant="subtitle1" sx={{ fontSize: '0.9rem' }}>
            {seller_name}
          </Typography>
          <Typography variant="subtitle1" sx={{ fontSize: '0.9rem' }} color="error">
            {numberFormat(offer_price)}
          </Typography>
          <Typography variant="subtitle1" sx={{ fontSize: '0.9rem' }} color="primary">
            + {numberFormat(priceSize)}
          </Typography>
          <Typography variant="caption" sx={{ fontSize: '0.9rem' }}>
            {product_name}
          </Typography>
        </Grid>
        <Grid item xs={12}></Grid>

        {/* 리더보드 */}
        <ProductLeaderBoard
        priceSize={priceSize}
          highestPrice={highestPrice}
          offerPrice={offer_price}
          leaderBoardData={leaderBoardData}
        ></ProductLeaderBoard>
      </Grid>
      <Stack spacing={2} direction="row" mt={3}>
        {(seller_id === userInfo.user_id) &&(
          <CloseButton close={close}/>
        )
        }
        {(seller_id !== userInfo.user_id) && (
        <BidButton bid={bid} />
        )}
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
  margin-bottom: 0px;
`;

const StyeldDiv = styled.div`
  text-align: right;
  padding-right: 5px;
  box-sizing: border-box;
  margin: 0px;
`;
