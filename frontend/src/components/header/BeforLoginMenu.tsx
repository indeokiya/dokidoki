import { Grid, Button } from '@mui/material';
import { Link } from 'react-router-dom';

const BeforLoginMenu = () => {
  return (
    <Grid container alignItems={'center'} spacing={1}>
       <Link to="/login">
        <Grid item>
          <Button variant="contained">로그인</Button>
        </Grid>
       </Link>
    </Grid>
  );
};

export default BeforLoginMenu;
