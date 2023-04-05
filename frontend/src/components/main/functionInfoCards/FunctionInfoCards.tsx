import { Grid } from '@mui/material';
import InfoCard from './InfoCard';
import trafficImg from '../../../assets/icon/traffic.png';
import realtimeImg from '../../../assets/icon/realtime.png';
import separationImg from '../../../assets/icon/separation.png';
import styled, { keyframes } from 'styled-components';
import { useInView } from 'react-intersection-observer';

const FunctionInfoCards = () => {
  const [ref, inView] = useInView({ threshold: 0.5, triggerOnce: true });
  console.log("inview >>> ",inView)
  const cardList = [
    {
      icon: trafficImg,
      title: '안정적인 경매',
      text: '카프카를 사용하여 대량의 트래픽을 빠르고 안정적으로 처리하며, 실시간으로 데이터를 처리하여 사용자들이 경매에 참여하는 동안 최신 정보를 얻을 수 있습니다.  이것은 사용자 경험을 개선하고, 경매사이트의 성능을 향상시키는데 큰 도움이 됩니다.',
    },
    {
      icon: realtimeImg,
      title: '실시간 경매가 확인',
      text: '경매사이트에서는 소켓 통신을 이용하여 실시간으로 가격 변동 정보를 전송하고 있습니다. 이를 통해 사용자들은 경매의 현재 상황을 즉시 파악할 수 있으며, 더욱 적극적으로 입찰을 진행할 수 있습니다. 이를 통해 사용자 경험을 개선하고, 경매사이트의 성능을 향상시키고, 경매 결과의 신뢰성을 높일 수 있습니다.',
    },
    {
      icon: separationImg,
      title: '분산처리',
      text: '경매사이트에서는 레디스를 사용하여 빠른 입출력을 구현하고 있습니다. 레디스는 인메모리 데이터베이스로써, 데이터를 메모리에 저장하여 빠르게 읽고 쓸 수 있도록 지원합니다. 따라서, 레디스를 이용하면 빠른 속도로 데이터를 처리할 수 있으며, 이를 통해 사용자들은 빠르게 경매 결과를 확인할 수 있습니다',
    },
  ];

  return (
    <Grid container ref={ref} sx={{position:"relative"}}  justifyContent="space-around">
      <Grid item xs={12} md={4}>
        <OneCard start={inView ? 'start' : 'end'}>
          <InfoCard primary={false} info={cardList[0]} />
        </OneCard>
      </Grid>
      <Grid item xs={12} md={4}>
        <TwoCard start={inView ? 'start' : 'end'}>
          <InfoCard primary={true} info={cardList[1]} />
        </TwoCard>
      </Grid>
      <Grid item xs={12} md={4}>
        <ThreeCard start={inView ? 'start' : 'end'}>
          <InfoCard primary={false} info={cardList[2]} />
        </ThreeCard>
      </Grid>
    </Grid>
  );
};

export default FunctionInfoCards;

type StyledDivProps = {
  start?: string;
};

const oneAnimation = keyframes`
0%{
  opacity : 0;
  transform : scale(0.8);
}
20%{
  opacity : 1;
  transform : scale(1);
}
100%{
  opacity : 1;
  transform : scale(1);
}
`;

const twoAnimation = keyframes`
  0%{
    opacity : 0;
    transform : scale(0.8);
  }
  20%{
    opacity : 0;
    transform : scale(0.8);
  }
  80%{
    opacity : 1;
    transform : scale(1);
  }
  100%{
    opacity : 1;
    transform : scale(1);
  }
`;

const threeAnimation = keyframes`
  0%{
    opacity : 0;
    transform : scale(0.8);
  }
  60%{
    opacity : 0;
    transform : scale(0.8);
  }
  100%{
    opacity : 1;
    transform : scale(1);
  }
`;

const OneCard = styled.div<StyledDivProps>`
opacity:0;
transition:1s;
animation-duration: 1s;
animation-name: ${(props) => (props.start === 'start' ? oneAnimation : '')};
animation-iteration-count: 1;
animation-timing-function: ease;
animation-fill-mode: forwards;
`;

const TwoCard = styled.div<StyledDivProps>`
opacity:0;
transition:1s;
animation-duration: 1s;
animation-name: ${(props) => (props.start === 'start' ? twoAnimation : '')};
animation-iteration-count: 1;
animation-timing-function: ease;
animation-fill-mode: forwards;
`;

const ThreeCard = styled.div<StyledDivProps>`
opacity:0;
transition:1s;
animation-duration: 1s;
animation-name: ${(props) => (props.start === 'start' ? threeAnimation : '')};
animation-iteration-count: 1;
animation-timing-function: ease;
animation-fill-mode: forwards;
`;
