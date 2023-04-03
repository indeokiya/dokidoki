import * as React from 'react';
import { AppBar, Avatar, Grid, IconButton, Tab, Tabs, Toolbar } from '@mui/material';
import Typography from '@mui/material/Typography';
import { useRecoilState } from 'recoil';
import { myAlertMenuState } from 'src/store/userInfoState';
import { useState, useEffect } from 'react';

const MypageHeader: React.FC<{ selectedMenu: string }> = (props) => {

  const [tabValue, setTabValue] = useRecoilState(myAlertMenuState);

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    // setTabValue(newValue);
  };



  return (
    <React.Fragment>
      <AppBar color="primary" position="sticky" elevation={0}>
        <Toolbar>
          <Grid container spacing={1} alignItems="center">
            <Grid sx={{ display: { sm: 'none', xs: 'block' } }} item></Grid>
             
            <Grid item >
            <Typography variant='h5' color={"inherit"} sx={{marginTop:"30px", marginLeft:"20px"}}> {props.selectedMenu}</Typography>
            </Grid>

            <Grid item xs={12}>
        {props.selectedMenu === '알림 내역' && (
          <Tabs
            value={tabValue}
            onChange={handleChange}
            textColor="inherit"
            indicatorColor="secondary"
            aria-label="secondary tabs example"
          >
            <Tab value="전체" label="전체"/>
            <Tab value="입찰 성공" label="입찰 성공" />
            <Tab value="입찰 실패" label="입찰 실패" />
            <Tab value="판매 성공" label="판매 성공" />
            <Tab value="경쟁 입찰" label="경쟁 입찰" />
          </Tabs>
        )}
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>

      <AppBar component="div" position="sticky" elevation={0} sx={{ zIndex: 0 }}>
       
      </AppBar>
    </React.Fragment>
  );
};

export default MypageHeader;
