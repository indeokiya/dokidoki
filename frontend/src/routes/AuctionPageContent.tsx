import Container from "@mui/material/Container";
import SideBar from "../components/auction/sidebar/SideBar";
import SearchBar from "../components/auction/SearchBar";
import ContentsList from "../components/auction/contents/ContentsList";
import Grid from "@mui/material/Grid"; // Grid version 1
import { Outlet, Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import LeaderBoard from "../components/leaderBoard/LeaderBoard";
import styled from "styled-components";

const ActionPageContent = () => {
  const [openDialog, setOpenDialog] = useState(false); //다이얼 로그 여는


  const navigate = useNavigate();
 

  const DialogHandler = () => {
    setOpenDialog(!openDialog);
  };

  const StyledIcon = styled.div`
    position: fixed;
    right: 5%;
    top: 90%;
    background-color: #3a77ee;
    border-radius: 100px;
    font-size: 50px;
    color: white;
    font-weight: bold;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 95px;
    cursor: pointer;
    transition: all 0.3s;
    &:hover {
      top: 89.5%;
      box-shadow: 1px 1rem 15px #dddddd;
    }
    &:active {
      background-color: #4285f4;
    }
  `;



  return (
    <>
      <Container>
          <StyledIcon onClick={()=>{
            
            navigate("/regist")
          }}>+</StyledIcon>

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
