import styles from './BidButton.module.css';
import styled from 'styled-components';
import Button from '@mui/material/Button';
const BidButton:React.FC<{bid:()=>void}> = (props) => {
    const {bid} = props
  return (
    <Button className={styles.bidButton} onClick={bid}>
      입찰하기
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </Button>
  );
};

export default BidButton;

const ButtomBackground = styled.div`
  background-color: black;
`;
