import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Box from '@mui/material/Box';
import BookmarkOutlinedIcon from '@mui/icons-material/BookmarkOutlined';
import Chip from '@mui/material/Chip';
import { Post } from '../../../datatype/datatype';
import { useNavigate } from 'react-router-dom';

function timeFormat(myNum: number) {
  if (myNum <= 0) return '마감';

  let hours: string | number = Math.floor(myNum / 3600);
  let minutes: string | number = Math.floor((myNum - hours * 3600) / 60);
  let seconds: string | number = myNum - hours * 3600 - minutes * 60;

  if (hours < 10) {
    hours = '0' + hours;
  }
  if (minutes < 10) {
    minutes = '0' + minutes;
  }
  if (seconds < 10) {
    seconds = '0' + seconds;
  }
  return hours + ':' + minutes + ':' + seconds;
}

function getPercentage(curr:number,offer:number){
  return (curr-offer)/offer;
}

// 1000 => '1,000 원' 으로 바꿔주는 함수
function numberFormat(price: number | null) {
    return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
}

//컴포넌트 시작
const Content: React.FC<{ auctionData: Post }> = (props) => {
  const navigate = useNavigate();
  //부모컴포넌트에서 받아온 게시글 정보
  const { auctionData } = props;

  //마우스를 올리면 그림자형성됨
  const [isHovered, setIsHovered] = useState(false);


  //현제 경매시간
  const [auctionTime, setAuctionTime] = useState(0);

  //시작하면서 경매시간 정보를 세팅한다.
  useEffect(() => {
    let hour = auctionData.remain_hours;
    let minute = auctionData.remain_minutes;
    let second = auctionData.remain_seconds;
    let total = hour * 60 * 60 + minute * 60 + second;
    setAuctionTime(total); // 음수로 셋팅된다.

      setInterval(() => {
        setAuctionTime((pretime) => pretime - 1); //1초씩 시간 줄이기
      }, 1000 * 10);
  }, []);

  return (
    <Card
      onMouseOver={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      raised={isHovered}
      sx={{
        cursor: 'pointer',
        position: 'relative',
        boxShadow: isHovered ? '' : 'none',
      }}
      onClick={() => {
          navigate('/auction/product/' + auctionData.auction_id);
      }}
    >
      {/* 내가 판매하고 있는 제품이라는 표시 */}
      {auctionData.is_my_auction && (
        <Chip
          label="MY SALE"
          color="primary"
          sx={{
            position: 'absolute',
            fontSize: '12px',
            margin: '5px',
          }}
        />
      )}

    

      {/* 북마크 표시,  북마크 안했다면 지워야함  */}
      {auctionData.is_my_interest && (
        <BookmarkOutlinedIcon
          color="error"
          sx={{ position: 'absolute', fontSize: '40px', right: '1rem', bottom: '45%' }}
        />
      )}

      <CardMedia
        sx={{marginTop:5}}
        component="img"
        alt="green iguana"
        height="200"
        image={auctionData.auction_image_url}
      />

      <CardContent sx={{ padding: 3, boxSizing: 'border-box' }}>
        <Chip label={auctionData.category_name} variant="outlined" />
        <Typography gutterBottom variant="subtitle1" component="div">
          {auctionData.auction_title}
        </Typography>

        <Box my={1}>
          <StyledFlex>
            <StyledSpan style={{ color: '#3A77EE' }}>남은 시간 : </StyledSpan>
            <StyledSpan style={{ color: '#3A77EE' }}>{timeFormat(auctionTime)}</StyledSpan>
          </StyledFlex>
          <StyledFlex>
            <StyledSpan>시작 가격 : </StyledSpan>
            <StyledSpan>{numberFormat(auctionData.offer_price)}</StyledSpan>
          </StyledFlex>
          <StyledFlex>
            <StyledSpan>경매 단위 : </StyledSpan>
            <StyledSpan> {auctionData.price_size} 원</StyledSpan>
          </StyledFlex>
        </Box>
        <StyledFlex>
          <StyledSpan> </StyledSpan>
          <StyledSpan style={{ color: 'red', fontSize: '12px' }}>( + {getPercentage(auctionData.cur_price, auctionData.offer_price)}%)</StyledSpan>
        </StyledFlex>
        <StyledFlex>
          <StyledSpan style={{ fontWeight: 'bold' }}>현재 가격 : </StyledSpan>
          <StyledSpan style={{ fontWeight: 'bold', fontSize: '1rem' }}>
            {numberFormat(auctionData.cur_price)}
          </StyledSpan>
        </StyledFlex>
      </CardContent>

      <CardActions>{/* <Button size="small">Share</Button> */}</CardActions>
    </Card>
  );
};

export default Content;

const SoldOut = styled.div`
  right:25%;
  top:20%;
  position: absolute;
  width: 150px;
  height: 100px;
  border-radius: 10px;
  color:grey;
  border: 3px solid grey;
  font-weight: bold;
  text-align:center;
  line-height:50px;
  font-size:30px;
`;

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
