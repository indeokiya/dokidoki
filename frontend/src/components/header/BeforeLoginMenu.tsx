import { Grid, Button } from '@mui/material';
import { useNavigate } from 'react-router';

const BeforLoginMenu = () => {

  const navigate = useNavigate();

  return (
    <Grid container alignItems={'center'} spacing={1}>
      <Grid item>
        <Button variant="contained" onClick={() => navigate("/login")}>로그인</Button>
      </Grid>
    </Grid>
  );
};

export default BeforLoginMenu;
