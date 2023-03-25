import { Grid, Button } from '@mui/material';

const BeforLoginMenu = () => {
  return (
    <Grid container alignItems={'center'} spacing={1}>
      <Grid item>
        <Button variant="contained">로그인</Button>
      </Grid>
      <Grid item>
        <Button variant="outlined">회원가입</Button>
      </Grid>
    </Grid>
  );
};

export default BeforLoginMenu;
