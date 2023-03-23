import { useState, useCallback, useRef } from 'react';
import Grid from '@mui/material/Grid';
import axios from 'axios';
import { Button } from '@mui/material';
import DaumPostcode from 'react-daum-postcode';

const TestContainer = () => {
  const [address, setAddress] = useState('');
  const [visible, setVisible] = useState(false); // 우편번호 컴포넌트의 노출여부 상태

  const handleComplete = (data: any) => {
    let fullAddress = data.address;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress += extraAddress !== '' ? `,${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }
    setAddress(fullAddress);
  };

  return (
    <>
      <Button
        onClick={() => {
          setVisible(true);
        }}
      >
        열기
      </Button>
      {address}
      {visible && (
        <div>
          <Button onClick={() => setVisible(false)}>닫기</Button>
          <DaumPostcode onComplete={handleComplete} />
        </div>
      )}
    </>
  );
};

export default TestContainer;
