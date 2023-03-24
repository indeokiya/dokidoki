import React from 'react';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import styled from 'styled-components';
import imgSrc from '../../../assets/image/phone1.png';

const EndContentItem = () => {
  const StyledImg = styled.img`
    height: 100%;
    object-fit: cover;
    margin-right:20px;
  `;

  return (
    <React.Fragment>
      <Paper elevation={3}>
        <Grid container>
          <Grid item xs={2} maxHeight={'150px'} alignItems={'center'}>
            <StyledImg src={imgSrc}></StyledImg>
          </Grid>
          <Grid item xs py={3}>
            <Typography variant="subtitle2">mobile</Typography>
            <Typography variant="h6">게시글 제목</Typography>
            <Typography variant="overline">nimkname</Typography>
            <Typography variant="subtitle1">판매 날짜 : 2023.02.01</Typography>
          </Grid>
          <Grid item xs />
          <Grid item xs={2} pt={6} pr={5}alignItems="end">
            <Typography variant="subtitle2" sx={{ textAlign: 'end' }}>
              (+2,000)
            </Typography>
            <Typography variant="h4" sx={{ textAlign: 'end' }}>22,000</Typography>
          </Grid>
        </Grid>
      </Paper>
    </React.Fragment>
  );
};

export default EndContentItem;
