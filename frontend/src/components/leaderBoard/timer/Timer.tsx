import { useState, useEffect } from 'react';
import Typography from '@mui/material/Typography';

const Timer: React.FC<{ end_time: string; type?: string }> = (props) => {
  const { end_time, type = undefined } = props;
  const type_list=['h1','h2','h3','h4','h5','h6','subtitle1','subtitle2','body1','body2','caption']

  const [index, setIndex] = useState(0);

  // 종료시간 - 현재시간 = 초
  function TimeFormat(end: string) {
    // 두 시간 문자열
    // Date 객체로 변환
    const time1 = new Date(end);
    const time2 = new Date();

    // 두 Date 객체의 차이 계산 (밀리초 단위)
    const timeDiff = time1.getTime() - time2.getTime();

    // 초 단위로 변환
    const seconds = Math.floor(timeDiff / 1000);

    return seconds;
  }

  const [second, setSecond] = useState(TimeFormat(end_time));

  //초를 시분 초로 변경해줌
  function formatSeconds(seconds: number): string {
    if (seconds <= 0) return '마감';
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const remainingSeconds = seconds % 60;

    const hoursStr = hours.toString().padStart(2, '0');
    const minutesStr = minutes.toString().padStart(2, '0');
    const secondsStr = remainingSeconds.toString().padStart(2, '0');

    return `${hoursStr}:${minutesStr}:${secondsStr}`;
  }


  useEffect(() => {
    setTimeout(() => {
      setSecond((pre) => pre - 1);
    }, 1000);

    return () => clearInterval(second);
  }, [second]);

  return (
    <>
    
      <Typography variant="h4" color="primary">
        {formatSeconds(second)}
      </Typography>
    </>
  );
};

export default Timer;
