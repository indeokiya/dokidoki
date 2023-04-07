import styles from './BidButton.module.css';
import Box from '@mui/material/Box';
import LinearProgress from '@mui/material/LinearProgress';
import { useEffect, useState } from 'react';

const BidButton: React.FC<{ bid: () => void }> = (props) => {
  const { bid } = props;

  const [bidDisabled, setBidDisabled] = useState(false);

  return (
    <>
      {bidDisabled ? (
        <Box sx={{ width: '100%' }} mt={4}>
          <LinearProgress />
        </Box>
      ) : (
        <button
          className={styles.bidButton}
          disabled={bidDisabled}
          onClick={() => {
            setBidDisabled(true);
            setTimeout(() => {
              setBidDisabled(false);
            }, 1100);

            bid();
          }}
        >
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
