import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import sampleImg from '../../../assets/image/phone1.png';
import { useState } from 'react';
import styled from 'styled-components';
import Box from '@mui/material/Box';
import BookmarkOutlinedIcon from '@mui/icons-material/BookmarkOutlined';
import Chip from '@mui/material/Chip';

import Badge from '@mui/material/Badge';

const IngContentItem = () => {
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

  const StyledSpan = styled.span`
    font-size: 12px;
  `;

  const [isSold, setIsSold] = useState(false);

  return (
    <Card
      onMouseOver={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      raised={isHovered}
      sx={{ cursor: isSold ? 'pointer' : '', position: 'relative', boxShadow:isHovered?"":"none"}}
    >
      {/* 내가 판매하고 있는 제품이라는 뜻 */}
      <Chip
        label="MY SALE"
        color="primary"
        sx={{
          position: 'absolute',
          fontSize: '12px',
          margin: '5px',
        }}
      />

      {/* 북마크 표시,  북마크 안했다면 지워야함  */}
      <BookmarkOutlinedIcon
        color="error"
        sx={{ position: 'absolute', fontSize: '40px', right: '1rem', bottom: '45%' }}
      />

      <CardMedia
        sx={{ opacity: false ? '0.5' : '1' }}
        component="img"
        alt="green iguana"
        height="200"
        image={sampleImg}
      />

      <CardContent sx={{ padding: 3, boxSizing: 'border-box' }}>
        <Chip label="mobile" variant="outlined" />
        <Typography gutterBottom variant="subtitle1" component="div">
          GALAXY ZZ flip 팝니다.
        </Typography>

        <Box my={1}>
          <StyledFlex>
            <StyledSpan style={{ color: '#3A77EE' }}>남은 시간 : </StyledSpan>
            <StyledSpan style={{ color: '#3A77EE' }}>20 : 20 : 20</StyledSpan>
          </StyledFlex>
          <StyledFlex>
            <StyledSpan>시작 가격 : </StyledSpan>
            <StyledSpan>20,000 원</StyledSpan>
          </StyledFlex>
          <StyledFlex>
            <StyledSpan>경매 단위 : </StyledSpan>
            <StyledSpan>2,000 원</StyledSpan>
          </StyledFlex>
        </Box>
        <StyledFlex>
          <StyledSpan> </StyledSpan>
          <StyledSpan style={{ color: 'red', fontSize: '12px' }}>( + 10%)</StyledSpan>
        </StyledFlex>
        <StyledFlex>
          <StyledSpan style={{ fontWeight: 'bold' }}>현제 가격 : </StyledSpan>
          <StyledSpan style={{ fontWeight: 'bold', fontSize: '1rem' }}>22,000 원</StyledSpan>
        </StyledFlex>
      </CardContent>

      <CardActions>{/* <Button size="small">Share</Button> */}</CardActions>
    </Card>
  );
};

 export default IngContentItem;

