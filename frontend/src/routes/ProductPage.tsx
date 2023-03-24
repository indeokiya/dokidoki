import Grid from '@mui/material/Grid';
import ProductImages from '../components/leaderBoard/ProductImages';
import ProductInfo from '../components/leaderBoard/ProductInfo';
import Divider from '@mui/material/Divider';
import Container from '@mui/material/Container';
import styled from 'styled-components';
import ProductGraph from '../components/leaderBoard/ProductGraph';
import ProductDescription from '../components/leaderBoard/ProductDescription';
import CommentsList from '../components/leaderBoard/comments/CommentsList';
import ScrollTop from '../components/util/ScrollTop';
import Header from '../components/header/Header';
import Paper from '@mui/material/Paper';

import { Box } from '@mui/material';

const ProductPage = () => {
  const StyledDiv = styled.div`
    padding: 30px;
    box-sizing: border-box;
  `;

  const BackgroundDiv = styled.div`
    background-color: #dddddd;
  `;

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
              <ProductImages />
            </Grid>
            <Grid item xs={4}>
              {/* 제품 정보 */}
              <ProductInfo />
            </Grid>
          </Grid>
          <Divider />

          <StyledDiv>
            <ProductDescription />
          </StyledDiv>

          {/* 제품 카테고리 평균 가격 */}
          <ProductGraph />

          {/* 댓글 작성과 댓글들  */}
          <CommentsList />

          {/* 모달창 하단에 존재하는 버튼 */}
        </Box>
      </BackgroundDiv>
    </>
  );
};

export default ProductPage;
