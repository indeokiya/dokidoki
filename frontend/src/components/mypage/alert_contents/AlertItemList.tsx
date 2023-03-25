import AlertItem from './AlertItem';
import { useState } from 'react';

type AlertData = {
  type: string;
  price: number;
  productId: number;
  title: string;
  id: number;
  isVisible:boolean;
};


const AlertItemList = () => {
  const [alertList, setAlertList] = useState([
    { id: 1, type: '구매 성공', productId: 1, price: 123123, title: '휴대폰', isVisible: true },
    { id: 2, type: '구매 실패', productId: 2, price: 123123, title: '컴퓨터', isVisible: true },
    { id: 3, type: '판매 성공', productId: 3, price: 123123, title: '노트북', isVisible: true },
    { id: 4, type: '입찰 강탈', productId: 4, price: 123123, title: '세탁기', isVisible: true },
    { id: 5, type: '구매 성공', productId: 5, price: 123123, title: '에어컨', isVisible: true },
    { id: 6, type: '구매 성공', productId: 6, price: 123123, title: '휴대폰', isVisible: true },
  ]);

  const [alertCnt , setAlertCnt] = useState(alertList.length)

  return (
    <div>
      알람 개수 : {alertCnt}
      {alertList.map((data, i) => {
        return <AlertItem key={i} data={data} setAlertList={setAlertList} setAlertCnt={setAlertCnt}></AlertItem>;
      })}
    </div>
  );
};

export default AlertItemList;
