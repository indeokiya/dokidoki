import styles from './BidButton.module.css';
const BidButton:React.FC<{bid:()=>void}> = (props) => {
    const {bid} = props
  return (
    <button className={styles.bidButton} onClick={bid}>
      입찰하기
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </button>
  );
};

export default BidButton;


