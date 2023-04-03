import styled, { keyframes } from 'styled-components';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import { auctionAPI } from '../../../api/axios';
import { useState, useEffect } from 'react';
import HighestPrice from 'src/components/leaderBoard/HighestPrice';
import { useInView } from 'react-intersection-observer';
import Box from '@mui/material/Box';

const TotalTransaction = () => {
  const [ref, inView] = useInView({threshold:0.5,triggerOnce: true,});


  const [totalPrice, setTotalPrice] = useState(0);

  

  if(inView){
    auctionAPI.get('/total-prices').then(({ data }) => {
      setTotalPrice((pre) => data.data);
    });
  }


 

  return (
    <>
  <Box ref={ref}>
      <StyledDiv  start={inView ? "start":"end"} >
        <AttachMoneyIcon fontSize="large" sx={{ marginBottom: '15px' }} />
        <StyledSmallP>총 거래된 금액</StyledSmallP>
        <StyledLargeP >
          <HighestPrice animation={false} increase={totalPrice} max={totalPrice} />
        </StyledLargeP>
      </StyledDiv>
  </Box>
    </>
  );
};

export default TotalTransaction;

type StyledDivProps = {
  start?: string;
}

const animation = keyframes`
0%{
  opacity:0;
  transform : translateY(-15px);
}
100%{
  opacity:1;
  transform : translateY(0px);
}
`;



const StyledDiv = styled.div<StyledDivProps>`
opacity:0;
transition:1s;
position: relative;
width: 100%;
padding: 15%;
box-sizing: border-box;
text-align: center;
animation-duration: 1s;
animation-name: ${props => props.start ==="start" ? animation:""};
animation-iteration-count: 1;
animation-timing-function: ease;
animation-fill-mode: forwards;
`;

 

const StyledSmallP = styled.p`
  margin-bottom: 10px;
  margin-top: 0px;
  padding: 0;
  padding-left: 5px;
`;

const StyledLargeP = styled.p`
  color: #3a77ee;
  margin: 0;
  padding: 0;
  font-wieght: bold;
  font-size: 30px;
  text-transform: uppercase;
  padding-bottom: 5px;
`;
