import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import sampleImg from "../../../assets/image/phone1.png";
import { useState } from "react";
import styled from "styled-components";
import Box from "@mui/material/Box";

const Content = () => {
  const [isHovered, setIsHovered] = useState(false);

  const StyledFlex = styled.div`
    display: flex;
    justify-content: space-between;
    padding: 0px;
    margin-bottom: 3px;
  `;
  const CustomBadge = styled.div`
    display: inline-block;
    background-color: #a3a1a1;
    height: 20px;
    color: white;
    border-radius: 15px;
    padding: 5px 10px;
    font-size: 10px;
    font-weight: bold;
    box-sizing: border-box;
    text-align: center;
    margin-bottom: 3px;
  `;

  //마우스가 올라가면 raised 옵션을 주고 싶다.
  return (
    <Card
      onMouseOver={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      raised={isHovered}
      sx={isHovered ? { cursor: "pointer" } : {}}
    >
      <CardMedia
        component="img"
        alt="green iguana"
        height="250"
        image={sampleImg}
      />
      <CardContent sx={{ padding: 3, boxSizing: "border-box" }}>
        <CustomBadge>mobile</CustomBadge>
        <Typography gutterBottom variant="h5" component="div">
          게시글 제목~
        </Typography>

        <Box my={3}>
          <StyledFlex>
            <span>남은 시간 : </span>
            <span>20 : 20 : 20</span>
          </StyledFlex>
          <StyledFlex>
            <span style={{ color: "blue" }}>시작 가격 : </span>
            <span>20,000 원</span>
          </StyledFlex>
          <StyledFlex>
            <span>경매 단위 : </span>
            <span>2,000 원</span>
          </StyledFlex>
        </Box>
      </CardContent>

      <CardActions>
        <Button size="small">Share</Button>
        <Button size="small">Learn More</Button>
      </CardActions>
    </Card>
  );
};

export default Content;
