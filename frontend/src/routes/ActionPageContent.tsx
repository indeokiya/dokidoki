import Container from "@mui/material/Container";
import SideBar from "../components/action/sidebar/SideBar";
import SearchBar from "../components/action/SearchBar";
import ContentsList from "../components/action/contents/ContentsList";
import Grid from "@mui/material/Grid"; // Grid version 1
import { Outlet } from "react-router-dom";
import { useState } from "react";
import LeaderBoard from "../components/LeaderBoard/LeaderBoard";

const ActionPageContent = () => {
  const [openDialog, setOpenDialog] = useState(false);   //다이얼 로그 여는 

  const DialogHandler = () =>{
    setOpenDialog(!openDialog)
  }

  return (
    <Container>
      <LeaderBoard open={openDialog} onClose={DialogHandler}/>
      <SearchBar />
      <Grid container spacing={2}>
        <Grid item xs={2}>
          <SideBar />
        </Grid>
        <Grid xs={10}>
          <ContentsList openPage={DialogHandler}/>
        </Grid>
      </Grid>
    </Container>
  );
};

export default ActionPageContent;
