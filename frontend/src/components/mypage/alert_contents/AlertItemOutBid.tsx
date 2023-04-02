import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import styled from 'styled-components';
import { useNavigate } from 'react-router';
import IconButton from '@mui/material/IconButton';
import Grid from '@mui/material/Grid';
import DeleteIcon from '@mui/icons-material/Delete';
import Tooltip from '@mui/material/Tooltip';
import { noticeAPI } from 'src/api/axios';

const StyledBlueSpan = styled.span`
  font-weight: bold;
  color: #3a77ee;
  cursor: pointer;
`;

const StyledRedSpan = styled.span`
  font-weight: bold;
  color: #ff0000;
`;
const PreFixSpan = styled.span`
  //   font-weight: bold;
  //   color: #7fff00;
`;


type OutBidAlert = {
  type: string;
  product_id: number;
  product_name: string;
  auction_id: number;
  current_bid_price: number;
  time_stamp: string;
  read: boolean;
}

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


const AlertItemOutBid: React.FC<{
  data: AlertData;
  key: string;
  id: string;
  setAlertMap: (data: any) => void;
  setAlertCnt: (data: any) => void;
}> = (props) => {
  const navigate = useNavigate();

  // 안쓰는
  //   function deletHelder(id: number) {
  //    props.setAlertList((pre:AlertData[]) => pre.filter((data:AlertData) => (data.id !== id)))
  //   }

  function ComponentHidden(key: any) {
    props.setAlertMap((pre: any) => {
      const updateAlertMap = {...pre};
      updateAlertMap[key].read = true;
      Read(key)
      return updateAlertMap;
    });
  }

  function visibleStyle(x: boolean) {
    if (x) {
      return { opacity: 1, mb: 4, padding: '20px', height: '70px' };
    } else {
      return {
        opacity: 0,
        height: 0,
        transition: '0.2s',
      };
    }
  }

  function Read(key: string) {
    noticeAPI.put(
      `/${key}/read`
    ).then((res)=> {
      console.log(res)
    })
  }

  return (
    <Paper
      elevation={3}
      sx={{ width: '90%', boxSizing: 'border-box', ...visibleStyle(!props.data.read) }}
    >
      <Typography variant="subtitle1">
        <Grid container>
          <Grid item xs>
            <PreFixSpan>[경쟁 입찰] </PreFixSpan>
            <Tooltip title="누르면 제품 페이지로 이동">
              <StyledBlueSpan
                onClick={() => {
                  navigate(`/auction/product/${props.data.auction_id}`);
                }}
              >
                [{props.data.product_name}]
              </StyledBlueSpan>
            </Tooltip>
            <span>에 다른 입찰자가 나타났습니다. </span>
            <StyledRedSpan>현재 최고 가격: [{props.data.current_bid_price} 원]</StyledRedSpan>
          </Grid>
          <Grid item>
            <Tooltip title="Delete">
              <IconButton
                onClick={() => {
                  ComponentHidden(props.id); //사라지는 애니메이션 back이랑 연동하는거 아님
                  props.setAlertCnt((data: any) => data - 1); //카운트 줄어듬
                }}
              >
                <DeleteIcon />
              </IconButton>
            </Tooltip>
          </Grid>
        </Grid>
      </Typography>
    </Paper>
  );
};

export default AlertItemOutBid;

// [구매 성공] {물품 이름}을 {경매 최종 가격} 에 구매하는 데에 성공하셨습니다.
// [구매 실패] {물품 이름}을 {본인이 입찰했던 가격} 가격에 구매하는 데에 실패하셨습니다.
// [판매 완료] {물품 이름}을 {경매 최종 가격}에 판매하는 데에 성공하셨습니다.
// [입찰 강탈] {물품 이름}에 새로운 입찰자가 나타났습니다. [현재 최고 가격 : {갱신된 경매 가격}]
