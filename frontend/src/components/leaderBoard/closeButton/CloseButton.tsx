import styles from './CloseButton.module.css';
import Button from '@mui/material/Button';
const CloseButton:React.FC<{close:()=>void}> = (props) => {
    const {close} = props
  return (
    <Button className={styles.closeButton} onClick={close}>
      내 경매 종료하기
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </Button>
  );
};

export default CloseButton;


