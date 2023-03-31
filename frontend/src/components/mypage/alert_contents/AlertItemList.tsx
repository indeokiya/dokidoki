import AlertItem from './AlertItem';
import { useState } from 'react';
import { noticeAPI } from 'src/api/axios';

type AlertData = {
  type: string; // "PURCHASE_SUCCESS", "PURCHASE_FAIL", "SALE_COMPLETE", "OUTBID"
  productId: number;
  productName : string;
  auctionId : number;
  finalPrice : number;
  myFinalPrice : number;
  currentBidPrice : number;
  timeStamp : string;
  price: number;
  // title: string;
  // id: number;
  isVisible:boolean;
};

const AlertItemList = () => {
  // const [alertList, setAlertList] = useState([
  //   { id: 1, type: '구매 성공', productId: 1, price: 123123, title: '휴대폰', isVisible: true },
  //   { id: 2, type: '구매 실패', productId: 2, price: 123123, title: '컴퓨터', isVisible: true },
  //   { id: 3, type: '판매 성공', productId: 3, price: 123123, title: '노트북', isVisible: true },
  //   { id: 4, type: '입찰 강탈', productId: 4, price: 123123, title: '세탁기', isVisible: true },
  //   { id: 5, type: '구매 성공', productId: 5, price: 123123, title: '에어컨', isVisible: true },
  //   { id: 6, type: '구매 성공', productId: 6, price: 123123, title: '휴대폰', isVisible: true },
  // ]);
  // 알림 내역
  let alertDatas:any = {}

  // 알림 내역 가져오기
  noticeAPI
  .get("/")
  .then( ({ data }) => {
    console.log('알림 내역 >> ', data)
    alertDatas = data
  })
  .catch((err) => {
    console.log(err)
  })

  const renderAlerts = (): JSX.Element[] => {
    const alerts = Object.keys(alertDatas).map(
      (key: any) => {
        return <AlertItem key={key} data={alertDatas[key]} setAlertMap={setAlertMap} setAlertCnt={setAlertCnt}></AlertItem>;
      }
    );
    return alerts;
  };

  const [alertMap, setAlertMap] = useState<any>({})

  const [alertCnt , setAlertCnt] = useState(Object.keys(alertDatas).length)
  
  return (
    <div>
      알람 개수 : {Object.keys(alertDatas).length}
      {renderAlerts()}
    </div>
  );
};

export default AlertItemList;
