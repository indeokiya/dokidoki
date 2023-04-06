import { Box } from '@mui/material';
import { useSuperRich } from 'src/hooks/superRich';
import RankLabel from './RankLabel';
import RankBar from './RankBar';
import Smartphone from 'src/assets/image/richrank.png';
import styled, { keyframes } from 'styled-components';
import { Title } from './Title';
import { useInView } from 'react-intersection-observer';
import Grid from '@mui/material/Grid';

const SuperRichRank = () => {
  const [ref, inView] = useInView({ threshold: 0.5, triggerOnce: true });
  const { data } = useSuperRich();
  const bias = 'left';

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
        <Grid item xs={12} mb={3}>
      <Title title="Rich Rank"></Title>
        </Grid>
        <Grid item xs={12} sm={7} sx={{ display: 'flex' }}>
          <RankLabel bias={bias} widthSize="20%" rankData={data} />
          <RankBar bias={bias} widthSize="80%" rankData={data} animation={inView} />
        </Grid>
        <Grid item xs={12} sm={5} sx={{ position: 'relative', }}>
          <StyledImg src={Smartphone} start={inView} />
        </Grid>
      </Grid>
    </Box>
  );
};

export default SuperRichRank;

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
  position: absolute;
  opacity:0;
  top: -40px;
  left: 0%;
  width: 600px;
  height: 600px;
  animation : ${({start}) => start ? animation : ""};
  animation-duration : 1s;
  animation-fill-mode: forwards;


`;