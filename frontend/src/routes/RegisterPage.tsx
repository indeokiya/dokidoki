import { Form } from 'react-router-dom';
import styled from 'styled-components';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import { useEffect, useRef } from 'react';
import ProductInfoInput from '../components/resigter/ProductInfoInput';
import ActionInfoInput from '../components/resigter/AuctionInfoInput';
import { auctionAPI } from '../api/axios';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { isCategoryErrorState, isEndAtErrorState, isFilesErrorState, isMeetingPlaceErrorState, isOfferPriceErrorState, isPriceSizeErrorState, isTitleErrorState } from 'src/store/RegisterAuctionStates';

//경매 등록 타입
export type AuctionRegisterType = {
  productId: number;
  title: string;
  description: string;
  offerPrice: number;
  priceSize: number;
  endAt: Date;
  meetingPlace: string;
  files: any[];
  name: string;
};

const RegisterPage = () => {
  const navigate = useNavigate();

  const dataRef = useRef({
    product_id: -1,
    title: '',
    description: '',
    offer_price: -1,
    price_size: -1,
    end_at: '',
    meeting_place: '',
    files: [],
  });

  // Error 체크용 전역 State
  const setIsTitleError = useSetRecoilState(isTitleErrorState)
  const setIsCategoryError = useSetRecoilState(isCategoryErrorState)
  const setIsOfferPriceError = useSetRecoilState(isOfferPriceErrorState)
  const setIsPriceSizeError = useSetRecoilState(isPriceSizeErrorState)
  const setIsEndAtError = useSetRecoilState(isEndAtErrorState)
  const setIsFilesError = useSetRecoilState(isFilesErrorState)
  const setIsMeetingPlaceError = useSetRecoilState(isMeetingPlaceErrorState)
  useEffect(() => {
    setIsTitleError(false)
    setIsCategoryError(false)
    setIsOfferPriceError(false)
    setIsPriceSizeError(false)
    setIsEndAtError(false)
    setIsFilesError(false)
    setIsMeetingPlaceError(false)
  }, [])

  let userName = 'defaultName';
  let localStorageInfo = window.localStorage.getItem('user-info');
  if (localStorageInfo) {
    userName = JSON.parse(localStorageInfo).name;
  }

  const register = () => {
    if (dataRef.current.title.length === 0) {
      setIsTitleError(true)
      alert("제목을 입력해주세요.")
      return
    } else if (dataRef.current.product_id < 1
      || 1072 < dataRef.current.product_id) {
      setIsCategoryError(true)
      alert("카테고리 검색 후 선택해주세요.")
      return
    } else if (Number.isNaN(dataRef.current.offer_price)
      || dataRef.current.offer_price < 0) {
      setIsOfferPriceError(true)
      alert("시작가를 0 이상 입력해주세요.")
      return
    } else if (Number.isNaN(dataRef.current.price_size)
      || dataRef.current.price_size <= 0) {
      setIsPriceSizeError(true)
      alert("경매 단위를 1 이상 입력해주세요.")
      return
    } else if (dataRef.current.end_at.length === 0) {
      setIsEndAtError(true)
      alert("종료 시간을 입력해주세요.")
      return
    } else if (dataRef.current.meeting_place.length === 0) {
      setIsMeetingPlaceError(true)
      alert("거래 장소를 입력해주세요.")
      return
    }
    for (let idx = 0; idx < dataRef.current.files.length; idx++) {
      const file: File = dataRef.current.files[idx]
      // console.log(file)
      if (!file.type.startsWith("image/")) {
        setIsFilesError(true)
        alert("이미지 파일만 업로드 가능합니다.")
        return
      }
    }

    // console.log('서버에 보낼 데이터 >> ', dataRef.current);
    const formData = new FormData();
    formData.set('product_id', String(dataRef.current.product_id));
    formData.set('title', dataRef.current.title);
    formData.set('description', dataRef.current.description);
    formData.set('offer_price', String(dataRef.current.offer_price));
    formData.set('price_size', String(dataRef.current.price_size));
    formData.set('end_at', dataRef.current.end_at);
    formData.set('meeting_place', dataRef.current.meeting_place);
    formData.set('name', userName);
    for (let i = 0; i < dataRef.current.files.length; i++) {
      formData.append('files', dataRef.current.files[i]);
    }

    // 카테고리를 통해 product_id 받아오는 로직도 어디선가 필요함. 일단 1로 박음
    const axios = auctionAPI;
    axios
      .post('new', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((res) => {
        alert('성공');
        // console.log(res);
        // 성공하면 alert 알림 후 location.href , location.replace 또는 navigator로 이동
        navigate('/auction');
      })
      .catch((err) => {
        alert('최대 10MB 까지 보낼 수 있습니다.');
        // console.error(err);
      });
  };

  const cancel = () => {
    // 뒤로가기 로직
    if(window.confirm("정말 취소하시겠습니까?")){
      navigate('/auction');
    }
  };

  return (
    <>
      <StyledDiv>
        <Form>
          {/* 전체를 감싸는 Grid */}
          <Grid container alignItems={'center'} direction="column" gap={4} py={5}>
            {/*상단 입력 박스 */}
            <Grid item xs={6}>
              <ProductInfoInput dataRef={dataRef} update={false}/>
            </Grid>

            {/* 하단 입럭 박스 */}
            <Grid item xs={6}>
              <ActionInfoInput dataRef={dataRef} update={false}/>
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
                <Button variant="contained" sx={{ width: 340, fontSize: 25 }} onClick={register}>
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

const StyledDiv = styled.div`
  width: 100%;
  height: 1000%;
  background-color: gainsboro;
`;
