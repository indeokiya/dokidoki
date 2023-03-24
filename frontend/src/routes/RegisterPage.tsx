import { Form } from "react-router-dom";
import styled from "styled-components";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import { useState, useRef } from "react";
import ProductInfoInput from "../components/resigter/ProductInfoInput";
import ActionInfoInput from "../components/resigter/AuctionInfoInput";
import { ProductionQuantityLimitsRounded } from "@mui/icons-material";
import {auctionAPI, bidAPI} from "../api/axios"

// AuctionRegisterReq {
//   private String title;
//   private String Description;
//   private Integer offerPrice;
//   private Integer priceSize;
//   private LocalDateTime endAt;
//   private String meetingPlace;
// }

export type AuctionRegisterType = {
  productId: number
  title: string
  description: string
  offerPrice: number
  priceSize: number
  endAt: Date,
  meetingPlace: string
}

const RegisterPage = () => {
  const StyledDiv = styled.div`
    width: 100%;
    height: 1000%;
    background-color: gainsboro;
  `;

  const dataRef = useRef({
    product_id: 1,
    title: "",
    description: "",
    offer_price: -1,
    price_size: -1,
    end_at: "",
    meeting_place: "",
  })

  const createAuctionurl = "new"; 

  const register = () => {
    console.log("서버에 보낸 데이터 >> ",dataRef.current)
    const formData = new FormData();
    formData.set('product_id', String(dataRef.current.product_id));
    formData.set('title', dataRef.current.title);
    formData.set('description', dataRef.current.description);
    formData.set('offer_price', String(dataRef.current.offer_price));
    formData.set('price_size', String(dataRef.current.price_size));
    formData.set('end_at', dataRef.current.end_at);
    formData.set('meeting_place', dataRef.current.meeting_place);

    // 카테고리를 통해 product_id 받아오는 로직도 어디선가 필요함. 일단 1로 박음
    const axios = auctionAPI;
    axios.post(createAuctionurl, formData, {headers : {"Content-Type":"multipart/form-data"}})
      .then(res => {
        alert("성공")
        console.log(res)
        // 성공하면 alert 알림 후 location.href , location.replace 또는 navigator로 이동 
      })
      .catch(err => {
        alert("실패")
        console.error(err);
      })
    
  }

  const cancel = () => {
    // 뒤로가기 로직
  }

  return (
    <>
      <StyledDiv>
        <Form>
          {/* 전체를 감싸는 Grid */}
          <Grid container alignItems={'center'} direction="column" gap={4} py={5}>
            {/*상단 입력 박스 */}
            <Grid item xs={6}>
              <ProductInfoInput dataRef={dataRef} />
            </Grid>

            {/* 하단 입럭 박스 */}
            <Grid item xs={6}>
              <ActionInfoInput dataRef={dataRef} />
            </Grid>

            {/* 하단 버튼 */}
            <Grid item xs={6}>
              <Stack spacing={2} direction="row">
                <Button
                  variant="contained"
                  color="error"
                  onClick={cancel}
                  sx={{ width: 340, fontSize: 25 }}
                >
                  뒤로가기
                </Button>
                <Button 
                  variant="contained" 
                  sx={{ width: 340, fontSize: 25 }}
                  onClick={register}>
                  등록
                </Button>
              </Stack>
            </Grid>
          </Grid>
        </Form>
      </StyledDiv>
    </>
  );
};

export default RegisterPage;
