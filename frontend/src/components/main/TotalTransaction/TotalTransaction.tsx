import styled from 'styled-components';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import { auctionAPI } from '../../../api/axios';
import { useState,  } from 'react';
import HighestPrice from 'src/components/leaderBoard/HighestPrice';

const TotalTransaction = () => {
 

  const [totalPrice, setTotalPrice] = useState(0);

  setTimeout(() => {
    auctionAPI.get('/total-prices').then(({ data }) => {
      setTotalPrice((pre) => data.data);
    });
  }, 3000);

  return (
    <>
      <StyledDiv>
        <AttachMoneyIcon fontSize="large" sx={{ marginBottom: '15px' }} />
        <StyledSmallP>총 거래된 금액</StyledSmallP>
        <StyledLargeP>
          <HighestPrice animation={false} increase={totalPrice} max={totalPrice} />
        </StyledLargeP>
      </StyledDiv>
    </>
  );
};

export default TotalTransaction;

const StyledDiv = styled.div`
  border: 1px solid gray;
  width: 100%;
  padding: 5%;
  box-sizing: border-box;
  text-align: center;
`;

const StyledSmallP = styled.p`
  margin-bottom:10px;
  margin-top:0px;
  padding: 0;
  padding-left: 5px;
`;

const StyledLargeP = styled.p`
color:#3A77EE;
  margin: 0;
  padding: 0;
  font-wieght: bold;
  font-size: 30px;
  text-transform: uppercase;
  padding-bottom: 5px;
`;
