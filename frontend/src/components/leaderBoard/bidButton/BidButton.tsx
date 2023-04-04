import styles from './BidButton.module.css';
import Box from '@mui/material/Box';
import LinearProgress from '@mui/material/LinearProgress';
import { useEffect, useState } from 'react';

const BidButton: React.FC<{ bid: () => void; active: boolean }> = (props) => {
  const { bid, active } = props;

  const [progress, setProgress] = useState(0);
  useEffect(() => {
    const timer = setInterval(() => {
      setProgress((oldProgress) => {
        if (oldProgress === 100) {
          return 0;
        }
        const diff = Math.random() * 10;
        return Math.min(oldProgress + diff, 100);
      });
    }, 400);

    return () => {
      clearInterval(timer);
    };
  }, [active]);

  return (
    <>
      {!!active ? (
        <Box sx={{ width: '100%' }} mt={4}>
          <LinearProgress value={progress} />
        </Box>


      ) : (
        <button className={styles.bidButton} disabled={active} onClick={bid}>
          입찰하기
          <span></span>
          <span></span>
          <span></span>
          <span></span>
        </button>
      )}
    </>
  );
};

export default BidButton;
