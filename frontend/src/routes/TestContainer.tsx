import Grid from '@mui/material/Grid';
import ProductImages from '../components/leaderBoard/ProductImages';
import ProductInfo from '../components/leaderBoard/ProductInfo';
import Divider from '@mui/material/Divider';
import Container from '@mui/material/Container';
import styled from 'styled-components';
import ProductGraph from '../components/leaderBoard/ProductGraph';
import ProductLeaderBoard from '../components/leaderBoard/ProductLeaderBoard';
import ProductDescription from '../components/leaderBoard/ProductDescription';
import CommentsList from '../components/leaderBoard/comments/CommentsList';
import ScrollTop from '../components/util/ScrollTop';
import Header from '../components/header/Header';
import Paper from '@mui/material/Paper';

import MettingPlace from '../components/leaderBoard/MeetingPlace'
import { useState } from 'react';

import SockJS from 'sockjs-client'
import { Client, Message, StompHeaders } from '@stomp/stompjs'



import { Box } from '@mui/material';

import { useParams } from 'react-router-dom';
import { useEffect, useRef } from 'react';

// const { useAuctionDetail, test } = require("../hooks/auctionDetail");
import { useAuctionDetail } from '../hooks/auctionDetail'

import { Leaderboard } from '@mui/icons-material';
import MeetingPlace from '../components/leaderBoard/MeetingPlace';



import { CommentType } from 'src/datatype/datatype';

const ProductPage = () => {
  const { id } = useParams() as {id: string};
  
  // let socket = new SockJS("ws");
  let clientRef = useRef<Client>();
  const test = useRef<boolean>();
  
  useEffect(() => {
    if (!clientRef.current && !test.current) connect();
    return () => disconnect();
  }, []);



  const connect = () => { // 연결할 때
    test.current = true;
    clientRef.current = new Client({
      brokerURL: `wss://j8a202.p.ssafy.io/api/notices/ws`,
      connectHeaders: {
        authorization: "Bearer " + localStorage.getItem('access_token')
      },
      onConnect: () => {
        console.log("socket connected");

        clientRef.current?.subscribe(`/topic/auctions/${id}/realtime`, (message: Message) => {
          console.log(`Received message: ${message.body}`);
        });
      },
    });
    clientRef.current?.activate(); // 클라이언트 활성화
  };
  
  const disconnect = () => { // 연결이 끊겼을 때 
    clientRef.current?.deactivate();
    console.log("socket disconnected");
  };

  // 소켓 객체 생성
  // useEffect(() => {
  //   if (!ws.current) {
  //     ws.current = new WebSocket(webSocketUrl);
  //     ws.current.onopen = () => {
  //       console.log("connected to " + webSocketUrl);
  //       setSocketConnected(true);
  //     };
  //     ws.current.onclose = (error) => {
  //       console.log("disconnect from " + webSocketUrl);
  //       console.log(error);
  //     };
  //     ws.current.onerror = (error) => {
  //       console.log("connection error " + webSocketUrl);
  //       console.log(error);
  //     };
  //     ws.current.onmessage = (evt) => {
  //       const data = JSON.parse(evt.data);
  //       console.log(data);
  //       setItems((prevItems) => [...prevItems, data]);
  //     };
  //   }

  //   return () => {
  //     console.log("clean up");
  //     ws.current.close();
  //   };
  // }, []);
  
  // props로 내려줄 초기 데이터 가져오기 . useQuery 사용
  // data fetching logic
  const { isLoading, isError, error, data} = useAuctionDetail({id});
  if (isLoading) return <h1>isLoading..</h1>
  if (isError) {
    console.error("error occured >> ", error.message);
    return <h1>error occured while fetching auction_id: {id}</h1>
  }
  // 이 아래부터는 data가 존재함이 보장됨
  console.log("total data >> ", data)  
  const {
    auction_image_urls,
    auction_title,
    category_name,
    comments,
    description,
    end_time,
    highest_price,
    leaderboard,
    meeting_place,
    offer_price,
    price_size,
    product_name,
    seller_id,
    seller_name,
    start_time,
    is_my_interest,
  } = data

  console.log(description);

  return (
    <>
      <BackgroundDiv>
        <Box
          sx={{
            border: '1px solid white',
            backgroundColor: 'white',
            width:"80%",
            margin:"0 auto"
          }}
        >
          <ScrollTop />
          <Grid container spacing={3}>
            <Grid item xs={2} />
            <Grid item xs={4}>
              {/* 제품 이미지 */}
              <ProductImages 
                images={auction_image_urls}
              />
            </Grid>
            <Grid item xs={4}>
              {/* 제품 정보 */}
              <ProductInfo 
                auction_title={auction_title}
                auction_id={id}
                category={category_name}
                offer_price={offer_price}
                price_size={price_size}
                highest_price={highest_price}
                is_my_interest={is_my_interest}
              />
            </Grid>
          </Grid>
          <Divider />

          
          <ProductDescription description={description}/>
          

          {/* 제품 카테고리 평균 가격 */}
          <ProductGraph />
          <ProductLeaderBoard/>
          <h5>{meeting_place}</h5>
          <MeetingPlace location={meeting_place}/>
          {/* 댓글 작성과 댓글들  */}

          <CommentsList auction_id={id} comments={comments} seller_id={seller_id} />


          {/* 모달창 하단에 존재하는 버튼 */}
        </Box>
      </BackgroundDiv>
    </>
  );
};

export default ProductPage;

const StyledDiv = styled.div`
padding: 30px;
box-sizing: border-box;
`;

const BackgroundDiv = styled.div`
background-color: #dddddd;
`;