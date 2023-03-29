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

import { Box } from '@mui/material';

import { useParams } from 'react-router-dom';

// const { useAuctionDetail, test } = require("../hooks/auctionDetail");
import { useAuctionDetail } from '../hooks/auctionDetail'

const ProductPage = () => {

  // test();

  const { id } = useParams() as {id: string};

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
  } = data

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
              />
            </Grid>
          </Grid>
          <Divider />

          <StyledDiv>
            <ProductDescription 
              description={description}  
            />
          </StyledDiv>

          {/* 제품 카테고리 평균 가격 */}
          <ProductGraph />
          <ProductLeaderBoard/>

          {/* 댓글 작성과 댓글들  */}
          <CommentsList />

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