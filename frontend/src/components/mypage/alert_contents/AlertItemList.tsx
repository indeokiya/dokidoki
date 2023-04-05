import AlertItem from './AlertItem';
import { useState, useEffect } from 'react';
import { noticeAPI } from 'src/api/axios';
import AlertItemSuccess from './AlertItemSuccess';
import AlertItemOutBid from './AlertItemOutBid';
import AlertItemFail from './AlertItemFail';
import AlertItemComplete from './AlertItemComplete';
import { myAlertMenuState } from 'src/store/userInfoState';
import { useRecoilState } from 'recoil';
import Typography from '@mui/material/Typography';

function numberFormat(price: number | null) {
  return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 개';
}

type AlertData = {
  type: string; // "PURCHASE_SUCCESS", "PURCHASE_FAIL", "SALE_COMPLETE", "OUTBID"
  product_id: number;
  product_name: string;
  auction_id: number;
  final_price: number;
  my_final_price: number;
  current_bid_price: number;
  time_stamp: string;
  price: number;
  // title: string;
  // id: number;
  read: boolean;
};

const AlertItemList = () => {
  const [tabValue, setTabValue] = useRecoilState(myAlertMenuState);
  const [alertMap, setAlertMap] = useState<any>({});
  const [alertCnt, setAlertCnt] = useState(0);

  useEffect(() => {
    noticeAPI
      .get('/')
      .then(({ data }) => {
        console.log('알림 내역 >> ', data);
        setAlertCnt(countAlert(data, tabValue.menu));
        setAlertMap(data);
      })
      .catch((err) => {
        console.log(err);
      });
    console.log(tabValue);
  }, []);

  useEffect(() => {
    setAlertCnt(countAlert(alertMap, tabValue.menu));
  }, [alertMap, tabValue]);

  const countAlert = (data: any, alertType: string) => {
    var cnt = 0;
    if (alertType === 'TOTAL') {
      Object.keys(data).map((key: string) => {
        if (!data[key].read) {
          cnt += 1;
        }
      });
    } else {
      Object.keys(data).map((key: string) => {
        if (!data[key].read && data[key].type === alertType) {
          cnt += 1;
        }
      });
    }
    return cnt;
  };

  const renderAllAlerts = (): JSX.Element[] => {
    return Object.keys(alertMap)
      .reverse()
      .map((key: string) => {
        if (!alertMap[key]) {
          return <span />;
        }

        if (alertMap[key].type === 'PURCHASE_SUCCESS') {
          return (
            <AlertItemSuccess
              key={key}
              id={key}
              data={alertMap[key]}
              setAlertMap={setAlertMap}
              setAlertCnt={setAlertCnt}
            />
          );
        } else if (alertMap[key].type === 'PURCHASE_FAIL') {
          return (
            <AlertItemFail
              key={key}
              id={key}
              data={alertMap[key]}
              setAlertMap={setAlertMap}
              setAlertCnt={setAlertCnt}
            />
          );
        } else if (alertMap[key].type === 'SALE_COMPLETE') {
          return (
            <AlertItemComplete
              key={key}
              id={key}
              data={alertMap[key]}
              setAlertMap={setAlertMap}
              setAlertCnt={setAlertCnt}
            />
          );
        } else if (alertMap[key].type === 'OUTBID') {
          return (
            <AlertItemOutBid
              key={key}
              id={key}
              data={alertMap[key]}
              setAlertMap={setAlertMap}
              setAlertCnt={setAlertCnt}
            />
          );
        }

        return (
          <AlertItem
            key={key}
            data={alertMap[key]}
            setAlertMap={setAlertMap}
            setAlertCnt={setAlertCnt}
          />
        );
      });
  };

  const renderTypedAlerts = (alertType: string): JSX.Element[] => {
    console.log(alertType);
    return Object.keys(alertMap)
      .reverse()
      .map((key: string) => {
        if (!alertMap[key]) {
          return <span />;
        }
        if (alertMap[key].type === alertType && alertType === 'PURCHASE_SUCCESS') {
          return (
            <AlertItemSuccess
              key={key}
              id={key}
              data={alertMap[key]}
              setAlertMap={setAlertMap}
              setAlertCnt={setAlertCnt}
            />
          );
        }
        if (alertMap[key].type === alertType && alertType === 'PURCHASE_FAIL') {
          return (
            <AlertItemFail
              key={key}
              id={key}
              data={alertMap[key]}
              setAlertMap={setAlertMap}
              setAlertCnt={setAlertCnt}
            />
          );
        }
        if (alertMap[key].type === alertType && alertType === 'SALE_COMPLETE') {
          return (
            <AlertItemComplete
              key={key}
              id={key}
              data={alertMap[key]}
              setAlertMap={setAlertMap}
              setAlertCnt={setAlertCnt}
            />
          );
        }
        if (alertMap[key].type === alertType && alertType === 'OUTBID') {
          return (
            <AlertItemOutBid
              key={key}
              id={key}
              data={alertMap[key]}
              setAlertMap={setAlertMap}
              setAlertCnt={setAlertCnt}
            />
          );
        }
        return <span />;
      });
  };

  const renderAlerts = (menu: string): JSX.Element[] => {
    if (menu === 'TOTAL') {
      return renderAllAlerts();
    } else {
      return renderTypedAlerts(menu);
    }
  };

  return (
    <div>
      <Typography variant='h6' color="#4c4c4c" mb={3}>알람 개수: {numberFormat(alertCnt)}</Typography>
      {renderAlerts(tabValue.menu)}
    </div>
  );
};

export default AlertItemList;
