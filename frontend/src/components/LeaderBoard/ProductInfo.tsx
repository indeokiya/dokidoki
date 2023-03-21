import Divider from "@mui/material/Divider";
import Grid from "@mui/material/Grid";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import styled from "styled-components";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import { useState } from "react";
import FavoriteIcon from "@mui/icons-material/Favorite";

const ProductInfo = () => {
  const dataLeft = ["카테고리", "남은시간", "시작가격", "경매단위"];
  const dataRight = [1, 2, 3, 4];
  const [bookmark, setBookmaek] = useState(false);
  const StyledH1 = styled.h1`
    margin-top: 50px;
  `;

  const StyledSpan = styled.span`
    margin-right:10px;
    font-size:20px;
  `
  return (
    <div>
      <StyledH1>제목</StyledH1>
      <Divider />
      <Grid container>
        <Grid item xs={6} mt={5} mb={5}>
          {dataLeft.map((data, i) => {
            return <h3 key={i}>{data}</h3>;
          })}
        </Grid>
        <Grid item xs={6} mt={5} mb={5}>
          {dataRight.map((data, i) => {
            return <h3 key={i}>{data}</h3>;
          })}
        </Grid>
        <Grid item xs={12}>
          <Divider />
        </Grid>
        <Grid item xs={6}>
          <h1>현재 가격</h1>
        </Grid>
        <Grid item xs={6}>
          <h1>43,000</h1>
          <Typography color="red"> +3,000원</Typography>
        </Grid>
      </Grid>
      <Stack spacing={2} direction="row" mt={10}>
        <Button variant="contained" sx={{ width: "50%", height: "80px" }}>
          <StyledSpan>입찰하기</StyledSpan>
        </Button>
        <Button
          variant="outlined"
          sx={{ width: "50%", height: "80px"}}
          onClick={() => {
            setBookmaek(!bookmark);
          }}
          
        >
           <StyledSpan>찜하기 </StyledSpan>
          {bookmark ? <FavoriteBorderIcon fontSize="large" /> : <FavoriteIcon fontSize="large" />}
        </Button>
      </Stack>
    </div>
  );
};

export default ProductInfo;
