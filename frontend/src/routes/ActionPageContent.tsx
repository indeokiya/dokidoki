import Container from "@mui/material/Container";
import SideBar from "../components/action/sidebar/SideBar";
import SearchBar from "../components/action/SearchBar";
import ContentsList from "../components/action/contents/ContentsList";
import Grid from "@mui/material/Grid"; // Grid version 1

const ActionPageContent = () => {
  return (
    <Container>
      <SearchBar />
      <Grid container spacing={2}>
        <Grid item xs={2}>
          <SideBar />
        </Grid>
        <Grid xs={10}>
          <ContentsList />
        </Grid>
      </Grid>
    </Container>
  );
};

export default ActionPageContent;
