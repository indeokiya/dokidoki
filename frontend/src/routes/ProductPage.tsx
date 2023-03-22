import * as React from "react";
import Button from "@mui/material/Button";
import Dialog, { DialogProps } from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Grid from "@mui/material/Grid";
import ProductImages from "../components/leaderBoard/ProductImages";
import ProductInfo from "../components/leaderBoard/ProductInfo";
import Divider from "@mui/material/Divider";
import Container from "@mui/material/Container";
import styled from "styled-components";
import ProductGraph from "../components/leaderBoard/ProductGraph";
import ProductDescription from "../components/leaderBoard/ProductDescription";
import CommentsList from "../components/leaderBoard/comments/CommentsList";

const ProductPage = () => {
  const StyledDiv = styled.div`
    padding: 30px;
    box-sizing: border-box;
  `;

  return (
    <Container>
      <Grid container spacing={3} mb={10}>
        <Grid item xs={1} />
        <Grid item xs={5}>
          {/* 제품 이미지 */}
          <ProductImages />
        </Grid>
        <Grid item xs={5}>
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
    </Container>
  );
};

export default ProductPage;
