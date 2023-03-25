import styled from 'styled-components';
import { Button } from '@mui/material';
import { Box } from '@mui/system';

const StartAction = () => {
  const StyledDiv = styled.div`
    border: 1px solid grey;
    width: 100%;
    padding: 10%;
    box-sizing: border-box;
  `;

  const StyledSmallP = styled.p`
    margin: 0;
    padding: 0;
    padding-left: 5px;
    color: #3a77ee;
  `;

  const StyledLargeP = styled.p`
    margin: 0;
    padding: 0;
    color: #3a77ee;
    font-wieght: bold;
    font-size: 50px;
    text-transform: uppercase;
    padding-bottom: 5px;
  `;

  const Describe = styled.p`
    margin: 0;
    padding: 0;
    padding-left: 5px;
    white-space: pre-wrap;
  `;

  return (
    <StyledDiv>
      <StyledSmallP>samsung for</StyledSmallP>
      <StyledLargeP>samsung</StyledLargeP>
      <Describe>삼성의 모든 것을 갖춘 옥션에서 일상을 즐겁게 만들어보세요.</Describe>
      <Describe>당신의 삶을 업그레이드하세요.</Describe>
      <Box sx={{ marginTop: '10px', marginLeft: '5px' }}>
        <Button variant="contained">경매시작하기</Button>
      </Box>
    </StyledDiv>
  );
};

export default StartAction;
