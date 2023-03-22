import * as React from "react";
import {
  AppBar,
  Avatar,
  Grid,
  IconButton,
  Tab,
  Tabs,
  Toolbar,
} from "@mui/material";

import { useState } from "react";

const MypageHeader: React.FC<{ selectedMenu: string }> = (props) => {
  const [tabValue, setTabValue] = useState("");

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setTabValue(newValue);
  };

  return (
    <React.Fragment>
      <AppBar color="primary" position="sticky" elevation={0}>
        <Toolbar>
          <Grid container spacing={1} alignItems="center">
            <Grid sx={{ display: { sm: "none", xs: "block" } }} item></Grid>
            <Grid item xs />

            <Grid item>김범식님 환영합니다.</Grid>
            <Grid item>
              <IconButton color="inherit" sx={{ p: 0.5 }}>
                <Avatar src="/static/images/avatar/1.jpg" alt="My Avatar" />
              </IconButton>
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
      <AppBar
        component="div"
        color="primary"
        position="static"
        elevation={0}
        sx={{ zIndex: 0 }}
      ></AppBar>
      <AppBar
        component="div"
        position="static"
        elevation={0}
        sx={{ zIndex: 0 }}
      >
        {props.selectedMenu !== "내 정보" &&
          props.selectedMenu !== "알림 내역" && (
            <Tabs
              value={tabValue}
              onChange={handleChange}
              textColor="inherit"
              indicatorColor="secondary"
              aria-label="secondary tabs example"
            >
              <Tab value="가격순" label="가격순" />
              <Tab value="마감순" label="마감순" />
            </Tabs>
          )}

        {props.selectedMenu === "알림 내역" && (
          <Tabs
            value={tabValue}
            onChange={handleChange}
            textColor="inherit"
            indicatorColor="secondary"
            aria-label="secondary tabs example"
          >
            <Tab value="입찰 성공" label="입찰 성공" />
            <Tab value="입찰 실패" label="입찰 실패" />
            <Tab value="판매 성공" label="판매 성공" />
            <Tab value="입찰 강탈" label="입찰 강탈" />
          </Tabs>
        )}
      </AppBar>
    </React.Fragment>
  );
};

export default MypageHeader;
