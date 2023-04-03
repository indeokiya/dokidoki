import styles from './BidButton.module.css';
import Button from '@mui/material/Button';
const BidButton:React.FC<{bid:()=>void}> = (props) => {
    const {bid} = props
  return (
    <Button className={styles.bidButton} onClick={bid}>
      <h2 style={{padding:0, margin:0}}>
      입찰하기
      </h2>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </Button>
  );
};

export default BidButton;


