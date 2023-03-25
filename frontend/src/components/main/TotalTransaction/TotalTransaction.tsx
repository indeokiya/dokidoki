import styled from 'styled-components';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';

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

  return (
    <>
      <StyledDiv>
        <AttachMoneyIcon fontSize="large" sx={{ marginBottom: '15px' }} />
        <StyledSmallP>총 거래된 금액</StyledSmallP>
        <StyledLargeP>123,1241,212,315 원</StyledLargeP>
      </StyledDiv>
    </>
  );
};

export default TotalTransaction;
