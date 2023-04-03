import styled from 'styled-components';
import { Button } from '@mui/material';
import { Box } from '@mui/system';
import { Navigate, useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { userInfoState } from 'src/store/userInfoState';

const StartAuction = () => {
  const navigate = useNavigate();

  return (
    <StyledDiv>
      <StyledSmallP>samsung for</StyledSmallP>
      <StyledLargeP>samsung</StyledLargeP>
      <Describe>삼성의 모든 것을 갖춘 옥션에서 일상을 즐겁게 만들어보세요.</Describe>
      <Describe>당신의 삶을 업그레이드하세요.</Describe>
      <Box sx={{ marginTop: '10px', marginLeft: '5px' }}>
        <StyledButton
          onClick={() => {
            navigate('/auction');
          }}
        >
          경매시작하기
        </StyledButton>
      </Box>
    </StyledDiv>
  );
};

export default StartAuction;

const StyledDiv = styled.div`
  height: 600px;
  background-color: #3a77ee;
  width: 100%;
  padding: 10%;
  box-sizing: border-box;
`;

const StyledSmallP = styled.p`
  color: white;
  margin-top: 10%;
  margin-bottom: 0px;
  padding: 0;
  padding-left: 5px;
`;

const StyledLargeP = styled.p`
  margin: 0;
  padding: 0;
  color: white;
  font-wieght: bold;
  font-size: 50px;
  text-transform: uppercase;
  padding-bottom: 5px;
`;

const Describe = styled.p`
  margin: 0;
  padding: 0;
  padding-left: 5px;
  color: white;
  white-space: pre-wrap;
`;

const StyledButton = styled.button`
font-size:15px;
  background-color: white;
  padding: 15px;
  border-radius: 5px;
  border: 1px solid white;
  color:#3a77ee;
  transition:0.2s;
  &:hover {
    box-shadow: 1px 1px 15px white;
  }
`;
