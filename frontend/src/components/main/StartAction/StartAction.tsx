import styled, {keyframes} from 'styled-components';
import { Button } from '@mui/material';
import { Box } from '@mui/system';
import { Navigate, useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { userInfoState } from 'src/store/userInfoState';
import { relative } from 'path';
import topImg from '../../../assets/image/main_product.png';
import bottomImg from '../../../assets/image/main_product_bottom.png';

const StartAuction = () => {
  const navigate = useNavigate();

  return (
    <Box sx={{ position: 'relative', height: '400px', padding: '10%', backgroundColor: '#3A77EE' }}>
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
      <StyledBottomImg src={bottomImg} />
      <StyledTopImg src={topImg} />
    </Box>
  );
};

export default StartAuction;

const bottomAnimation = keyframes`
  0%{
    opacity:0;
    transform : translateY(-15px)
  }
  60%{
    opacity:1;
    transform : translateY(0px)
  }
  100%{
    opacity:1;
    transform : translateY(0px)
  }
`

const topAnimation = keyframes`
0%{
  opacity:0;
  transform : translateY(-15px)
}
  40%{
    opacity:0;
    transform : translateY(-15px)
  }
  100%{
    opacity:1;
    transform : translateY(0px)
  }
`


const StyledBottomImg = styled.img`
max-height:730;
  z-index: 10;
  right: 3%;
  top: 0%;
  width: 60%;
  position: absolute;
  animation-duration: 1s;
  animation-name: ${bottomAnimation};
  animation-iteration-count: 1;
  
`;
const StyledTopImg = styled.img`
max-height:600;
  z-index: 11;
  right: 7%;
  top: 5%;
  width: 55%;
  position: absolute;
  animation-duration: 1s;
  animation-name: ${topAnimation};
  animation-iteration-count: 1;
`;
const StyledDiv = styled.div`
  position: absolute;
  width: 100%;
  box-sizing: border-box;
  margin-left:1%;
`;

const StyledSmallP = styled.p`
  color: white;
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
  font-size: 15px;
  background-color: white;
  padding: 15px;
  border-radius: 5px;
  border: 1px solid white;
  color: #3a77ee;
  transition: 0.2s;
  &:hover {
    box-shadow: 1px 1px 15px white;
  }
`;
