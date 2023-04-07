import styled, { keyframes } from 'styled-components';
import { Box } from '@mui/system';
import { useNavigate } from 'react-router-dom';
import topImg from '../../../assets/image/main_product.png';
import bottomImg from '../../../assets/image/main_product_bottom.png';
import Grid from '@mui/material/Grid';
import backgroundSrc from '../../../assets/image/startAuction_background.png';
import SamsungLogo from '../../../assets/logo/삼성로고.png'

const StartAuction = () => {
  const navigate = useNavigate();

  return (
    <Grid
      container
      sx={{
        height: '100vh',
        backgroundImage: `url(${backgroundSrc})`,
        backgroundSize: 'cover',
      }}
    >
      <Grid item xs={12} sm={4} sx={{ padding: '10%' }}>
        <Box>
          <StyledDiv>
            <StyledSmallP>auction for</StyledSmallP>
            <StyledImg src={SamsungLogo}></StyledImg>
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
        </Box>
      </Grid>
      <Grid
        item
        xs={12}
        sm={8}
        sx={{
          position: 'relative',
        }}
      >
        <StyledBottomImg src={bottomImg} />
        <StyledTopImg src={topImg} />
      </Grid>
    </Grid>
  );
};

export default StartAuction;

const StyledImg = styled.img`
margin:5px 0;
  width:300px;
  height:auto;
`

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
`;

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
`;

const StyledBottomImg = styled.img`
  max-height: 730;
  z-index: 10;
  // top:20%;
  width: 100%;
  position: absolute;
  animation-duration: 1s;
  animation-name: ${bottomAnimation};
  animation-iteration-count: 1;
`;
const StyledTopImg = styled.img`
  max-height: 600;
  z-index: 11;
  // top:20%;
  width: 95%;
  position: absolute;
  animation-duration: 1s;
  animation-name: ${topAnimation};
  animation-iteration-count: 1;
`;
const StyledDiv = styled.div`
  position: absolute;
  width: 40%;
  box-sizing: border-box;
  margin-left: 1%;
`;

const StyledSmallP = styled.p`
  color: #3a77ee;
  font-size: 1.2rem;
  margin-bottom: 0px;
  padding: 0;
  padding-left: 5px;
`;


const Describe = styled.p`
  margin: 0;
  padding: 0;
  padding-left: 5px;
  color: #3a77ee;
  white-space: pre-wrap;
`;

const StyledButton = styled.button`
  font-weight: bold;
  margin-top: 10px;
  font-size: 15px;
  background-color: #3a77ee;
  padding: 15px;
  border-radius: 5px;
  border: 1px solid #3a77ee;
  color: white;
  transition: 0.2s;
  &:hover {
    box-shadow: 1px 1px 15px #3a77ee;
  }
`;
