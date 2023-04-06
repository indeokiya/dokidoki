import { Form } from 'react-router-dom';
import styled from 'styled-components';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import { useState, useRef } from 'react';
import ProductInfoInput from '../components/resigter/ProductInfoInput';
import AuctionInfoInput from '../components/resigter/AuctionInfoInput';
import { auctionAPI } from '../api/axios';
import { useNavigate, useLocation } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { isPriceSizeErrorState, isTitleErrorState } from 'src/store/RegisterAuctionStates';

const AuctionUpdatePage = () => {
  const navigate = useNavigate();
  const { state } = useLocation();

  const setIsTitleError = useSetRecoilState(isTitleErrorState)
  const setIsPriceSizeError = useSetRecoilState(isPriceSizeErrorState)

  const dataRef = useRef({
    title: state.title,
    description: state.description,
    price_size: state.price_size,
    meeting_place: state.meeting_place,
  });

  const update = () => {
    if (dataRef.current.title === "") {
      setIsTitleError(true)
      alert("제목을 입력해주세요.")
      return
    } else if (Number.isNaN(dataRef.current.price_size)
      || dataRef.current.price_size <= 0) {
      setIsPriceSizeError(true)
      alert("경매 단위를 1 이상 입력해주세요.")
      return
    }

    const axios = auctionAPI;
    axios
      .put(`${state.auction_id}`, {
        title: dataRef.current.title,
        description: dataRef.current.description,
        price_size: dataRef.current.price_size,
        meeting_place: dataRef.current.meeting_place,
      }, )
      .then((res) => {
        alert('경매 정보 수정에 성공했습니다.');
        console.log(res);
        navigate(`/auction/product/${state.auction_id}`);
      })
      .catch((err) => {
        alert('경매 정보 수정에 실패했습니다.');
        console.error(err);
      });
  };

  const cancel = () => {
    // 뒤로가기 로직
    navigate(`/auction/product/${state.auction_id}`);
  };

  return (
    <>
      <StyledDiv>
        <Form>
          {/* 전체를 감싸는 Grid */}
          <Grid container alignItems={'center'} direction="column" gap={4} py={5}>
            {/*상단 입력 박스 */}
            <Grid item xs={6}>
              <ProductInfoInput dataRef={dataRef} update={true}/>
            </Grid>

            {/* 하단 입럭 박스 */}
            <Grid item xs={6}>
              <AuctionInfoInput dataRef={dataRef} update={true}/>
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
                <Button variant="contained" sx={{ width: 340, fontSize: 25 }} onClick={update}>
                  수정
                </Button>
              </Stack>
            </Grid>
          </Grid>
        </Form>
      </StyledDiv>
    </>
  );
};

export default AuctionUpdatePage;

const StyledDiv = styled.div`
  width: 100%;
  height: 1000%;
  background-color: gainsboro;
`;
