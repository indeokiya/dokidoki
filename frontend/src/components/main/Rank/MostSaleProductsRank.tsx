import { Box } from '@mui/material';
import RankLabel from './RankLabel';
import RankBar from './RankBar';
import Smartphone from 'src/assets/image/smartphone.png';
import styled, { keyframes } from 'styled-components';
import { Title } from './Title';
import { useInView } from 'react-intersection-observer';
import Grid from '@mui/material/Grid';
import { useMostSaleProductsAllQuery } from "src/hooks/mostSaleProducts";

const MostSaleProductsRank = () => {
  const [ref, inView] = useInView({threshold:0.5,triggerOnce: true,});
  const { data } = useMostSaleProductsAllQuery()
  const bias = "right"

  return (
    <Box
      ref={ref}
      sx={{
        position: 'relative',
        padding: '5% 0%',
        backgroundColor: '#3A77EE',
      }}
    >
      {/* 순위 제목 */}
      <Grid container px={3}>
      <Title title="Most populer Product"></Title>
        <Grid item xs={12} sm={5} sx={{ position: 'relative' }}>
          <StyledImg src={Smartphone} start={inView} />
        </Grid>
        <Grid item xs={12} sm={7} sx={{ display: 'flex' }}>
          <RankBar bias={bias} widthSize="80%" rankData={data} animation={inView} />
          <RankLabel bias={bias} widthSize="20%" rankData={data} />
        </Grid>
      </Grid>
    </Box>
  );
};



export default MostSaleProductsRank;

const animation = keyframes`
  from{
    opacity:0;
    transform:translateY(15px);
  }to{
    opacity:1;
    transform:translateY(0px);
  }
`

const StyledImg = styled.img<{ start: boolean }>`
  opacity:0;
z-index:10;
  position: absolute;
  top: -40px;
  left: -25%;
  width: 750px;
  height: 600px;
  animation : ${({start}) => start ? animation : ""};
  animation-duration : 1s;
  animation-fill-mode: forwards;;


`;


