import { useEffect, useState } from 'react';
import { AppBar, Grid, Tab, Tabs, Toolbar } from '@mui/material';
import Typography from '@mui/material/Typography';
import { useRecoilState } from 'recoil';
import { myAlertMenuState } from 'src/store/userInfoState';
import Box from '@mui/material/Box';
import { auctionAPI } from 'src/api/axios';

const MypageHeader: React.FC<{ selectedMenu: string }> = (props) => {
  const [totalPrice, setTotalPrice] = useState(0)
  // 탭 메뉴 바뀔 때마다 총 구매금, 총 판매금 조회
  useEffect(() => {
    // 판매내역이나 구매내역 탭 입장 시 API End point 설정
    let endPoint
    if (props.selectedMenu === "판매 내역") {
      endPoint = `/my-infos/total-profits`
    } else if (props.selectedMenu === "구매 내역") {
      endPoint = '/my-infos/total-expenses'
    }

    // End Point 설정됐다면 API 요청
    if (endPoint) {
      auctionAPI
        .get(endPoint)
        .then(({ data }) => {
          setTotalPrice(data.data)
        })
    }
  }, [props.selectedMenu])

  // 정수 포맷팅
  const showPoint = (point: number) => point.toString().split( /(?=(?:\d{3})+(?:\.|$))/g ).join( "," )

  const [tabValue, setTabValue] = useRecoilState(myAlertMenuState);

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setTabValue({ menu: newValue });
  };
  
  return (
    <>
      <Box sx={{ backgroundColor: "#3A77EE", color: "white", paddingBottom: "1rem" }}>
        <Toolbar>
          <Grid container spacing={1} alignItems="center" style={{ display: "flex", justifyContent: "space-between" }}>
            <Grid item >
              <Typography variant='h5' color={"inherit"} sx={{ marginTop: "30px", marginLeft: "20px" }}> {props.selectedMenu}</Typography>
            </Grid>

            {props.selectedMenu === '판매 내역' || props.selectedMenu === '구매 내역'
              ? <Grid item >
                  <Typography
                    variant='subtitle2'
                    style={{ display: "inline-block" }}
                  >
                    {props.selectedMenu === '판매 내역' ? "총 판매금" : "총 구매금"} : 
                  </Typography>
                  <Typography
                    variant='h6'
                    color={"inherit"}
                    sx={{ marginTop: "30px", marginLeft: "20px", display: "inline-block", fontFamily: "WorkSans" }}
                  >
                    {showPoint(totalPrice)} 원
                  </Typography>
              </Grid> : null}

            <Grid item xs={12}>
              {props.selectedMenu === '알림 내역' && (
                <Tabs
                  value={tabValue.menu}
                  onChange={handleChange}
                  textColor="inherit"
                  indicatorColor="secondary"
                  aria-label="secondary tabs example"
                >
                  <Tab value="TOTAL" label="전체" />
                  <Tab value="PURCHASE_SUCCESS" label="구매 성공" />
                  <Tab value="PURCHASE_FAIL" label="구매 실패" />
                  <Tab value="SALE_COMPLETE" label="판매 성공" />
                  <Tab value="OUTBID" label="경쟁 입찰" />
                </Tabs>
              )}
            </Grid>
          </Grid>
        </Toolbar>
      </Box>
      <AppBar component="div" position="sticky" elevation={0} sx={{ zIndex: 0 }}>
      </AppBar>
    </>
  );
};

export default MypageHeader;
