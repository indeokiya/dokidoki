import AlertItem from './AlertItem';
import { useState, useEffect } from 'react';
import { noticeAPI } from 'src/api/axios';
import AlertItemSuccess from './AlertItemSuccess';
import AlertItemOutBid from './AlertItemOutBid';
import AlertItemFail from './AlertItemFail';
import AlertItemComplete from './AlertItemComplete';
import { myAlertMenuState } from 'src/store/userInfoState';
import { useRecoilState } from 'recoil';


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
const tmpData = {
  0: {
    "type":"PURCHASE_SUCCESS",
    "product_id":30000,
    "product_name":"갤럭시노트",
    "auction_id":40000,
    "final_price":2000,
    "my_final_price":1000,
    "current_bid_price":1500,
    "price":3000,
    "read":false,
  },
  1: {
    "type":"PURCHASE_FAIL",
    "product_id":30000,
    "product_name":"갤럭시노트",
    "auction_id":40000,
    "final_price":2000,
    "my_final_price":1000,
    "current_bid_price":1500,
    "price":3000,
    "read":false,
  },
  2: {
    "type":"SALE_COMPLETE",
    "product_id":30000,
    "product_name":"갤럭시노트",
    "auction_id":40000,
    "final_price":2000,
    "my_final_price":1000,
    "current_bid_price":1500,
    "price":3000,
    "read":false,
  },
  3: {
    "type":"OUTBID",
    "product_id":30000,
    "product_name":"갤럭시노트",
    "auction_id":40000,
    "final_price":2000,
    "my_final_price":1000,
    "current_bid_price":1500,
    "price":3000,
    "read":false,
  },
}


const AlertItemList = () => {
  const [tabValue, setTabValue] = useRecoilState(myAlertMenuState);
  const [alertMap, setAlertMap] = useState<any>(tmpData)
  const [alertCnt , setAlertCnt] = useState(0)

  useEffect(() => {
    // noticeAPI.get("/")
    // .then(({ data }) => {
    //   console.log('알림 내역 >> ', data)
    //   setAlertCnt(countAlert(data))
    //   setAlertMap(data)
    // })
    // .catch((err) => {
    //   console.log(err)
    // })
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
  
const renderAllAlerts = (): JSX.Element[] => {
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

const renderTypedAlerts = (alertType:string): JSX.Element[] => {
  return Object.keys(alertMap).reverse().map((key:string) => {
    if (!alertMap[key]) {
      return <span/>;
    }
    if (alertMap[key].type === alertType) {
      return <AlertItemSuccess key={key} id={key} data={alertMap[key]} setAlertMap={setAlertMap} setAlertCnt={setAlertCnt}/>
    }
    return <span/>;
  })
}

const renderAlerts = (): JSX.Element[] => {
  if (tabValue.menu === "전체") {
    return renderAllAlerts();
  } else if (tabValue.menu === "구매 성공") { 
    return renderTypedAlerts("PURCHASE_SUCCESS");
  } else if (tabValue.menu === "구매 실패") { 
    return renderTypedAlerts("PURCHASE_FAIL");
  } else if (tabValue.menu === "판매 성공") { 
    return renderTypedAlerts("SALE_COMPLETE");
  } else if (tabValue.menu === "경쟁 입찰") { 
    return renderTypedAlerts("OUTBID");
  } else {
    return renderAllAlerts();
  }
}

return (
  <div>
    알람 개수: {alertCnt}
    {renderAlerts()}
  </div>
)
};

export default AlertItemList;
