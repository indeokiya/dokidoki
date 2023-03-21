import Container from "@mui/material/Container";
import SideBar from "../components/auction/sidebar/SideBar";
import SearchBar from "../components/auction/SearchBar";
import ContentsList from "../components/auction/contents/ContentsList";
import Grid from "@mui/material/Grid"; // Grid version 1
import { Outlet } from "react-router-dom";
import { useState } from "react";
import LeaderBoard from "../components/LeaderBoard/LeaderBoard";
import styled from "styled-components";

const ActionPageContent = () => {
  const [openDialog, setOpenDialog] = useState(false); //다이얼 로그 여는

  const DialogHandler = () => {
    setOpenDialog(!openDialog);
  };

  const StyledIcon = styled.div`
    position: fixed;
    right: 5%;
    top: 90%;
    background-color: #3a77ee;
    border-radius:40px;
    width: 50px;
    font-size:30px;
    color:white;
    font-weight:bold;
    height: 50px;
    text-align: center;
    line-height: 45px;
  `;

  return (
    <>
      <Container>
    <StyledIcon>+</StyledIcon>
        <LeaderBoard open={openDialog} onClose={DialogHandler} />
        <SearchBar />
        <Grid container spacing={2}>
          <Grid item xs={2}>
            <SideBar />
          </Grid>
          <Grid xs={10}>
            <ContentsList openPage={DialogHandler} />
          </Grid>
        </Grid>
      </Container>
    </>
  );
};

export default ActionPageContent;
