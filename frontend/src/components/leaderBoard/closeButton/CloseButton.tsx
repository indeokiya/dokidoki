import styles from './CloseButton.module.css';

const CloseButton:React.FC<{close:()=>void}> = (props) => {
    const {close} = props
  return (
    <button className={styles.closeButton} onClick={close}>
      내 경매 종료하기
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </button>
  );
};

export default CloseButton;


