import { Paper, Grid, Typography, Divider, TextField } from "@mui/material";
import { useState } from "react";
import AddressInput from "./AddressInput";
import InputAdornment from "@mui/material/InputAdornment";
import MapIcon from "@mui/icons-material/Map";
import { NumericFormat } from "react-number-format";
import { useRecoilState } from "recoil";
import { isEndAtErrorState, isOfferPriceErrorState, isPriceSizeErrorState } from "src/store/RegisterAuctionStates";

const ActionInfoInput = ( {dataRef, update} : any ) : React.ReactElement => {
  const [addressInputVisible, setAddressInputVisible] = useState(false);
  
  const [isOfferPriceError, setIsOfferPriceError] = useRecoilState(isOfferPriceErrorState)
  const [isPriceSizeError, setIsPriceSizeError] = useRecoilState(isPriceSizeErrorState)
  const [isEndAtError, setIsEndAtError] = useRecoilState(isEndAtErrorState)

  const onChange = (e: any) => {
    const { value, name } = e.target; // 우선 e.target 에서 name 과 value 를 추출
    dataRef.current[name] = parseInt(value.replace(/(,|\s|[A-Za-z])/g, ""));
    
    // 값이 바뀌면 error flag 해제
    switch (name) {
      case "offer_price":
        setIsOfferPriceError(false)
        break
      case "price_size":
        setIsPriceSizeError(false)
        break
      case "end_at":
        setIsEndAtError(false)
        break
    }
  };

  return (
    <>
      <Paper elevation={3} sx={{ width: 600, padding: 10 }}>
        <Grid container>
          <Grid item xs={12}>
            <Typography variant="h5" fontWeight="bold" color={'#3A77EE'}>
              경매 정보
            </Typography>
            <Divider sx={{ margin: '2rem 0px' }} />
          </Grid>
          { !update && <Grid item xs={2}>
            <Typography variant="subtitle1">시작 가격 : </Typography>
          </Grid> }
          { !update && <Grid item xs={10} mb={2}>
            <NumericFormat
              style={{
                width: '97%',
                height: 50,
                border: `1px solid ${isOfferPriceError ? "red" : "silver"}`,  // error flag에 따라 다르게 색상 설정
                fontSize: 20,
                paddingLeft: '10px',
              }}
              allowNegative={false}
              decimalScale={2}
              displayType={'input'}
              thousandSeparator={true}
              suffix={" 원"}
              name="offer_price"
              onChange={onChange}
            />
          </Grid> }

          <Grid item xs={2}>
            <Typography variant="subtitle1">경매 단위 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            <NumericFormat
              style={{
                width: '97%',
                height: 50,
                border: `1px solid ${isPriceSizeError ? "red" : "silver"}`,  // error flag에 따라 다르게 색상 설정
                fontSize: 20,
                paddingLeft: '10px',
              }}
              allowNegative={false}
              decimalScale={2}
              displayType={'input'}
              thousandSeparator={true}
              suffix={" 원"}
              name="price_size"
              onChange={onChange}
              value={update ? dataRef.current.price_size : ""}
            />
          </Grid>

          { !update && <Grid item xs={2}>
            <Typography variant="subtitle1">종료 시간 : </Typography>
          </Grid> }
          { !update && <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              variant="outlined"
              fullWidth
              type="datetime-local"
              name="end_at"
              onChange={(e) => {
                const { value, name } = e.target; // 우선 e.target 에서 name 과 value 를 추출
                dataRef.current[name] = value;
                setIsEndAtError(false)  // error flag 해제
              }}
              error={isEndAtError}
            />
          </Grid> }

          <Grid item xs={2}>
            <Typography variant="subtitle1">거래 장소 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            {addressInputVisible ? (
              <AddressInput
                setVisible={setAddressInputVisible}
                dataRef={dataRef}
              />
            ) : (
              <TextField
                id="outlined-basic"
                fullWidth
                type="text"
                value={dataRef.current.meeting_place}
                onClick={() => {
                  setAddressInputVisible(!addressInputVisible);
                }}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <MapIcon />
                    </InputAdornment>
                  ),
                }}
              />
            )}
          </Grid>
        </Grid>
      </Paper>
    </>
  );
};

export default ActionInfoInput;
