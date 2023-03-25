import Divider from '@mui/material/Divider';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import styled from 'styled-components';
import { useState } from 'react';
import IconButton from '@mui/material/IconButton';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';

import TurnedInIcon from '@mui/icons-material/TurnedIn';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';

const ProductInfo = () => {
  const dataLeft = ['카테고리', '남은시간', '시작가격', '경매단위'];
  const dataRight = ['mobile', '20:00:10', '20,000', '2,000'];
  const [bookmark, setBookmaek] = useState(false);

  const [loginUser, setloginUser] = useState(true);
  const StyledH1 = styled.h1`
    margin-top: 5px;
  `;

  const StyledSpan = styled.span`
    margin-right: 10px;
    font-size: 20px;
  `;

  const StyeldDiv = styled.div`
    text-align: right;
    padding-right: 5px;
    box-sizing: border-box;
    margin: 0px;
  `;

  return (
    <div>
      {loginUser && (
        <StyeldDiv>
          <IconButton>
            <EditOutlinedIcon />
          </IconButton>
        </StyeldDiv>
      )}
      <StyledH1>제목</StyledH1>

      <Divider />
      <Grid container>
        <Grid item xs={6} mt={4} mb={4}>
          {dataLeft.map((data, i) => {
            return (
              <Typography variant="subtitle1" key={i}>
                {data}
              </Typography>
            );
          })}
        </Grid>
        <Grid item xs={6} mt={4} mb={4}>
          {dataRight.map((data, i) => {
            return (
              <Typography variant="subtitle1" key={i}>
                {data}
              </Typography>
            );
          })}
        </Grid>
        <Grid item xs={12}>
          <Divider />
        </Grid>
        <Grid item xs={6} mt={2}>
          <Typography variant="h6" fontWeight={'bold'}>
            현재 가격 :{' '}
          </Typography>
        </Grid>
        <Grid item xs={6} mt={2}>
          <Typography variant="h5" fontWeight={'bold'}>
            {' '}
            43,000{' '}
          </Typography>
          <Typography color="red"> +3,000원</Typography>
        </Grid>
      </Grid>
      <Stack spacing={2} direction="row" mt={5}>
        <Button variant="contained" sx={{ width: '50%', height: '50px' }}>
          <StyledSpan>입찰하기</StyledSpan>
        </Button>
        <Button
          variant="outlined"
          sx={{ width: '50%', height: '50px' }}
          onClick={() => {
            setBookmaek(!bookmark);
          }}
        >
          <StyledSpan>찜하기 </StyledSpan>
          {bookmark ? <TurnedInIcon fontSize="large" /> : <TurnedInNotIcon fontSize="large" />}
        </Button>
      </Stack>
    </div>
  );
};

export default ProductInfo;
