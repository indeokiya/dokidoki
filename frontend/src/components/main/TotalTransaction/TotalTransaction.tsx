import styled from 'styled-components';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import {auctionAPI} from "../../../api/axios";
import { useState , useEffect} from 'react';

const TotalTransaction = () => {
  const StyledDiv = styled.div`
    border: 1px solid gray;
    width: 100%;
    padding: 5%;
    box-sizing: border-box;
    text-align: center;
  `;

  const StyledSmallP = styled.p`
    margin: 0;
    padding: 0;
    padding-left: 5px;
  `;

  const StyledLargeP = styled.p`
    margin: 0;
    padding: 0;
    font-wieght: bold;
    font-size: 30px;
    text-transform: uppercase;
    padding-bottom: 5px;
  `;

  function numberFormat(price: number | null) {
    return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
  }

  const [totalPrice, setTotalPrice] = useState(0)
  
  setTimeout(()=>{
    auctionAPI.get(
      "/total-prices"
    ).then(({data})=>{
      setTotalPrice(pre => data.data)
    })
  },3000)

  return (
    <>
      <StyledDiv>
        <AttachMoneyIcon fontSize="large" sx={{ marginBottom: '15px' }} />
        <StyledSmallP>총 거래된 금액</StyledSmallP>
        <StyledLargeP>{numberFormat(totalPrice)}</StyledLargeP>
      </StyledDiv>
    </>
  );
};

export default TotalTransaction;
