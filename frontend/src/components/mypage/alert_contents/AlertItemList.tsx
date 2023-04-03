import AlertItem from './AlertItem';
import { useState, useEffect } from 'react';
import { noticeAPI } from 'src/api/axios';
import AlertItemSuccess from './AlertItemSuccess';
import AlertItemOutBid from './AlertItemOutBid';
import AlertItemFail from './AlertItemFail';
import AlertItemComplete from './AlertItemComplete';


type AlertData = {
  type: string; // "PURCHASE_SUCCESS", "PURCHASE_FAIL", "SALE_COMPLETE", "OUTBID"
  product_id: number;
  product_name : string;
  auction_id : number;
  final_price : number;
  my_final_price : number;
  current_bid_price : number;
  time_stamp : string;
  price: number;
  // title: string;
  // id: number;
  read: boolean;
};

const AlertItemList = () => {
  const [alertMap, setAlertMap] = useState<any>({})
  const [alertCnt , setAlertCnt] = useState(0)

  useEffect(() => {
    noticeAPI.get("/")
    .then(({ data }) => {
      console.log('알림 내역 >> ', data)
      setAlertCnt(countAlert(data))
      setAlertMap(data)
    })
    .catch((err) => {
      console.log(err)
    })
}, [])

const countAlert = (data:any) => {
  var cnt = 0
  Object.keys(data).map((key: string) => {
    if (! data[key].read) {
      cnt += 1;
    }
  })
  return cnt;
}
  
const renderAlerts = (): JSX.Element[] => {
  return Object.keys(alertMap).reverse().map((key: string) => {
    if (!alertMap[key]) {
      return <span/>;
    }

    if (alertMap[key].type === "PURCHASE_SUCCESS") {
      return <AlertItemSuccess key={key} id={key} data={alertMap[key]} setAlertMap={setAlertMap} setAlertCnt={setAlertCnt}/>

    } else if (alertMap[key].type === "PURCHASE_FAIL") {
      return <AlertItemFail key={key} id={key} data={alertMap[key]} setAlertMap={setAlertMap} setAlertCnt={setAlertCnt}/>

    } else if (alertMap[key].type === "SALE_COMPLETE") {
      return <AlertItemComplete key={key} id={key} data={alertMap[key]} setAlertMap={setAlertMap} setAlertCnt={setAlertCnt}/>

    } else if (alertMap[key].type === "OUTBID") {
      return <AlertItemOutBid key={key} id={key} data={alertMap[key]} setAlertMap={setAlertMap} setAlertCnt={setAlertCnt}/>
    }

    return <AlertItem key={key} data={alertMap[key]} setAlertMap={setAlertMap} setAlertCnt={setAlertCnt} />;
  })
}

return (
  <div>
    알람 개수: {alertCnt}
    {renderAlerts()}
  </div>
)
};

export default AlertItemList;
