import Grid from '@mui/material/Grid';
import ProductImages from '../components/leaderBoard/ProductImages';
import ProductInfo from '../components/leaderBoard/ProductInfo';
import Divider from '@mui/material/Divider';
import styled from 'styled-components';
import ProductGraph from '../components/leaderBoard/ProductGraph';
import ProductLeaderBoard from '../components/leaderBoard/ProductLeaderBoard';
import ProductDescription from '../components/leaderBoard/ProductDescription';
import CommentsList from '../components/leaderBoard/comments/CommentsList';
import ScrollTop from '../components/util/ScrollTop';
import { Client, Message, StompHeaders } from '@stomp/stompjs';

import { Box } from '@mui/material';
import MeetingPlace from '../components/leaderBoard/MeetingPlace';
import { useParams } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';

import { useAuctionDetail } from '../hooks/auctionDetail';

import { useRecoilValue } from 'recoil';
import { userInfoState } from 'src/store/userInfoState';
import { useNavigate } from 'react-router-dom';

const ProductPage = () => {
  const loginUser = useRecoilValue(userInfoState);
  const navigate = useNavigate();
  const { id } = useParams() as { id: string };

  const [highestPrice, setHighestPrice]  = useState(0);

  // let socket = new SockJS("ws");
  let clientRef = useRef<Client>();

  useEffect(() => {
    if (!loginUser.is_logged_in) {
      console.log('loginUser >> ', loginUser.is_logged_in);
      alert('로그인 부터 ㄱㄱ');
      navigate('/login');
    }

    if (!clientRef.current) connect();
    return () => disconnect();
  }, []);


  //소캣 연결 함수
  const connect = () => {
    // 연결할 때
    clientRef.current = new Client({
      brokerURL: `wss://j8a202.p.ssafy.io/api/notices/ws`,
      connectHeaders: {
        authorization: 'Bearer ' + localStorage.getItem('access_token'),
      },
      onConnect: () => {
        console.log('socket connected');

        clientRef.current?.subscribe(`/topic/auctions/${id}/realtime`, (message: Message) => {
          console.log(`Received message: ${message.body}`); //여기서 전부 뽑아씀 => 업데이트할 자료
          setHighestPrice(JSON.parse(message.body).bid_info.bid_price) //가격 갱신되면 최고가 갱신됨
        });
      },
    });
    clientRef.current?.activate(); // 클라이언트 활성화
  };

  const disconnect = () => {
    // 연결이 끊겼을 때
    clientRef.current?.deactivate();
    console.log('socket disconnected');
  };

  

  // props로 내려줄 초기 데이터 가져오기 . useQuery 사용
  // data fetching logic
  const { isLoading, isError, error, data } = useAuctionDetail({ id });
  if (isLoading) return <h1>isLoading..</h1>;
  if (isError) {
    console.error('error occured >> ', error.message);
    return <h1>error occured while fetching auction_id: {id}</h1>;
  }else{
    if(highestPrice === 0) setHighestPrice(data.highest_price); //에러가 없다면 초기값 최고가 갱신
  }
  // 이 아래부터는 data가 존재함이 보장됨
  console.log('fetched auction data >> ', data);
  const {
    auction_image_urls,
    auction_title,
    category_name,
    comments,
    description,
    end_time,
    is_my_interest,
    meeting_place,
    offer_price,
    product_name,
    seller_id,
    seller_name,
    start_time, /// from auction server
    highest_price,
    leader_board, //레디스에 담겨있음
    price_size, /// from bid server
  } = data;
  
  

  return (
    <>
      <BackgroundDiv>
        <Box
          sx={{
            border: '1px solid white',
            backgroundColor: 'white',
            width: '1000px',
            margin: '0 auto',
            padding: '50px',
            borderRadius: '10px',
            boxShadow: '1px 1px 10px grey',
          }}
        >
          <ScrollTop />
          <Grid container spacing={3} sx={{marginBottom:"5%"}}>
            <Grid item xs={6}>
              {/* 제품 이미지 */}
              <ProductImages images={auction_image_urls} end_time={end_time} />
            </Grid>
            <Grid item xs={6}>
              {/* 제품 정보 */}
              <ProductInfo
              setHighestPrice={setHighestPrice}
                auction_title={auction_title}
                auction_id={id}
                seller_id={seller_id}
                category={category_name}
                offer_price={offer_price}
                price_size={price_size}
                highestPrice={highestPrice}
                is_my_interest={is_my_interest}
                end_time={end_time}
                start_time={start_time}
                description={description}
                meeting_place={meeting_place}
                product_name={product_name}
                seller_name={seller_name}
              />
            </Grid>
          </Grid>
          <Divider />
          <Grid container direction="column" justifyContent="center" alignItems="center">
            {/* 제품 카테고리 평균 가격 */}

            <Grid item xs={12} sx={{ width: '100%', marginBottom: '10px' }}>
              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <ProductGraph />
                </Grid>
              </Grid>
            </Grid>

            {/* 제품 설명 */}
            <Grid item sx={{ width: '100%' }}>
              <ProductDescription description={description} />
            </Grid>
            {/* 지도 */}
            <Grid item sx={{ width: '100%' }}>
              <MeetingPlace location={meeting_place} />
            </Grid>

            {/* 댓글 */}
            <Grid item sx={{ width: '100%' }}>
              <CommentsList auction_id={id} comments={comments} seller_id={seller_id} />
            </Grid>
          </Grid>
        </Box>
      </BackgroundDiv>
    </>
  );
};

export default ProductPage;

const BackgroundDiv = styled.div`
  background-color: #dddddd;
  padding-top: 30px;
`;
