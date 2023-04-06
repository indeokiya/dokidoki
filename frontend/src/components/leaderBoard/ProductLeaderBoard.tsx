import Grid from '@mui/material/Grid';
import styled from 'styled-components';
import Typography from '@mui/material/Typography';
import { SocketBidData } from 'src/datatype/datatype';
import { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import styles from './ProductLeaderBoard.module.css';
import HighestPrice from './HighestPrice';

const ProductLeaderBoard: React.FC<{
  highestPrice: number;
  offerPrice: number;
  leaderBoardData: SocketBidData[];
  priceSize: number;
}> = (props) => {
  const [animation, setAnimation] = useState(false);
  const { highestPrice, offerPrice, leaderBoardData, priceSize } = props;
  function numberFormat(price: number | null) {
    return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
  }

  useEffect(() => {
    setAnimation(true);
    setTimeout(() => {
      setAnimation(false);
    }, 500);
  }, [leaderBoardData]);

  return (
    <BackgroundDiv>
      <Grid container>
        {/* 증가한 금액 */}
        <Grid item xs={12}>
          <Typography variant="subtitle1" sx={{ color: '#BBCAFF' }}>
            (+ {numberFormat(highestPrice - offerPrice)})
          </Typography>
        </Grid>

        {/* 최고 경매가 */}
        <Grid item xs={12}>
          <Typography variant="h4" sx={{ color: 'white', fontWeight: 'bold' }}>
            {/* <span>{numberFormat(highestPrice)}</span> */}
            <HighestPrice increase={Math.min(priceSize, highestPrice)} max={highestPrice} animation={true} />
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <InnerDiv>
            <Grid container sx={{overflow:"hidden"}}>
              {leaderBoardData &&
                leaderBoardData.map((data: any, i: number) => {
                  let isBig = i === 0;
                  const styleFunc =() =>{
                    if(isBig){
                      return animation? styles.largeText : styles.none
                    }else{
                      return animation? styles.smallText : styles.none
                    }
                  }
                  return (
                    <Grid item xs={12} key={i} sx={{ marginBottom: '0.5rem' }}>
                      {/* <Box className={animation ? styles.textContainer : styles.none} > */}
                      <Box className={styleFunc()}>
                        <Typography variant="caption" sx={{ fontSize:"1.2rem" }}>
                          [
                          {data.bid_time.length > 10
                            ? data.bid_time.substring(11, 19)
                            : data.bid_time}
                          ]{' '}
                        </Typography>
                        <Typography variant="caption" color="primary" sx={{ fontSize:"1.2rem" }}>
                          {data.name.substr(0, data.name.length >3 ? 3 : data.name.length)}
                        </Typography>
                        <Typography variant="caption">님이 </Typography>
                        <Typography variant="caption" color="error" sx={{ fontSize:"1.2rem" }}>
                          {numberFormat(data.bid_price)}
                        </Typography>
                        <Typography variant="caption">에 입찰하셨습니다.</Typography>
                      </Box>
                    </Grid>
                  );
                })}
            </Grid>
          </InnerDiv>
        </Grid>
      </Grid>
    </BackgroundDiv>
  );
};

export default ProductLeaderBoard;

const BackgroundDiv = styled.div`
  width: 100%;
  padding: 1rem;
  border-radius: 10px;
  background-color: #3a77ee;
  box-sizing: border-box;
`;

const InnerDiv = styled.div`
  margin-top: 1rem;
  padding: 1rem;
  box-sizing: border-box;
  background-color: white;
  width: 100%;
  height: 220px;
  border-radius: 10px;
`;
