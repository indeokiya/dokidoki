import React from 'react';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import styled from 'styled-components';
import imgSrc from '../../../assets/image/phone1.png';
import { Post } from 'src/datatype/datatype';
import { useRecoilValue } from 'recoil';
import { userInfoState } from 'src/store/userInfoState';

const EndContentItem: React.FC<{ auctionData: Post }> = (props) => {
  const loginUser = useRecoilValue(userInfoState);
  const { auctionData } = props;

  //종료시간 판매시간 
  const dateFormat = ()=>{
    return auctionData.year+"."+auctionData.month+"."+auctionData.day;
  }
 
  //증가한 가격
  const translatePrice = ()=>{
    return auctionData.final_price - auctionData.offer_price;
  }

  function numberFormat(price: number | null) {
    return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
  }
  

  return (
    <React.Fragment>
      <Paper elevation={3} >
        <Grid container mx={1} maxWidth={"880px"}>
          <Grid item xs={2} maxHeight={'150px'} alignItems={'center'}>
            <StyledImg src={imgSrc}></StyledImg>
          </Grid>
          <Grid item xs={4} py={3}>
            <Typography variant="subtitle2">{auctionData.category_name}</Typography>
            <Typography variant="h6">{auctionData.auction_title}</Typography>

            {/*내가 판매자일 때와 내가 구매자일 때를 나눈다. */}
            {loginUser.name === auctionData.seller_name && (
              <>
                <Typography variant="overline">판매자 : {auctionData.buyer_name}</Typography>
                <Typography variant="subtitle1">구매날짜 : {dateFormat()}</Typography>
              </>
            )}
            {loginUser.name === auctionData.buyer_name && (
              <>
                <Typography variant="overline">구매자 : {auctionData.seller_name}</Typography>
                <Typography variant="subtitle1">판매날짜 : {dateFormat()}</Typography>
              </>
            )}
          </Grid>
          <Grid item xs />
          <Grid item xs={5} pt={6} pr={5} alignItems="end">
            <Typography variant="subtitle2" sx={{ textAlign: 'end' }}>
               (+ {translatePrice()})
            </Typography>
            <Typography variant="h4" sx={{ textAlign: 'end' }}>
              {numberFormat(auctionData.final_price)}
            </Typography>
          </Grid>
        </Grid>
      </Paper>
    </React.Fragment>
  );
};

export default EndContentItem;


const StyledImg = styled.img`
height: 100%;
object-fit: cover;
`;