import React from 'react';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import styled from 'styled-components';
import imgSrc from '../../../assets/image/blank_img.png';
import { Post } from 'src/datatype/datatype';
import { useRecoilValue } from 'recoil';
import { userInfoState } from 'src/store/userInfoState';
import Chip from '@mui/material/Chip';


const EndContentItem: React.FC<{ auctionData: Post }> = (props) => {
  const loginUser = useRecoilValue(userInfoState);
  const { auctionData } = props;

  //종료시간 판매시간
  const dateFormat = () => {
    return auctionData.year + '.' + auctionData.month + '.' + auctionData.day;
  };

  //증가한 가격
  const translatePrice = () => {
    return auctionData.final_price - auctionData.offer_price;
  };

  function numberFormat(price: number | null) {
    return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
  }

  return (
    <React.Fragment>
      <Paper elevation={3} sx={{ minWidth: '660px' }}>
        <Grid container mx={1}>
          <Grid item xs={2} maxHeight={'100%'} alignItems={'center'} p={1} mr={1}>
            <StyledImg src={!auctionData.auction_image_url?imgSrc : auctionData.auction_image_url}></StyledImg>
          </Grid>
          <Grid item xs={4} py={3}>
            <Chip label={auctionData.category_name} variant="outlined" />
            <Typography variant="h6">{auctionData.auction_title}</Typography>

            {/*내가 판매자일 때와 내가 구매자일 때를 나눈다. */}
            {loginUser.name === auctionData.seller_name && (
              <>
                {/* 내가 판매자라면 구매자가 누군지 보여줘야 함 */}
                <Typography variant="overline">구매자 : {auctionData.buyer_name}</Typography>
                <Typography variant="subtitle1">판매날짜 : {dateFormat()}</Typography>
              </>
            )}
            {loginUser.name === auctionData.buyer_name && (
              <>
                {/* 내가 구매자라면 판매자가 누군지 보여줘야 함 */}
                <Typography variant="overline">판매자 : {auctionData.seller_name}</Typography>
                <Typography variant="subtitle1">구매날짜 : {dateFormat()}</Typography>
              </>
            )}
          </Grid>
          <Grid item xs />
          <Grid item xs={5} pt={6} pr={5} alignContent={'end'}>
            <Typography variant="subtitle2" sx={{ textAlign: 'end', color: '#ff0000' }}>
              (+ {translatePrice()})
            </Typography>
            <Typography variant="h4" sx={{ textAlign: 'end' }}>
              {auctionData.final_price ? numberFormat(auctionData.final_price) : "미판매 종료"}
            </Typography>
          </Grid>
        </Grid>
      </Paper>
    </React.Fragment>
  );
};

export default EndContentItem;

const StyledImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;
