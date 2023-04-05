import { useEffect, useState } from 'react';
import styles from './HighestPrice.module.css';
import Box from '@mui/material/Box';
import { Troubleshoot } from '@mui/icons-material';
import styled from 'styled-components';

function numberFormat(price: number | null) {
  return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
}

const MyPoint: React.FC<{ max: number; increase: number; animation: boolean }> = (props) => {
  const { max, increase, animation } = props;
  //   const [animation, setAnimation] = useState(false);

  const [price, setPrice] = useState(max - increase);

  useEffect(() => {
    // setAnimation(true);
    let now = increase;
    setTimeout(() => {
      const count = setInterval(() => {
        if (now < 1) {
          clearInterval(count);
          // setAnimation(false);
        }

        const step = Math.ceil(now / 5);
        now -= step;

        setPrice(max - now);
      }, 50);
    },700);
  }, [max]);

  return <StyledPoint>{numberFormat(price)}</StyledPoint>;
};

const StyledPoint = styled.span`
  color: #3A77EE;
  font-family: WorkSans;
  font-weight: bold;
`;

export default MyPoint;
