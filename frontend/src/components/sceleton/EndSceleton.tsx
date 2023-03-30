import Skeleton from '@mui/material/Skeleton';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';

//로딩되기전
const EndSceleton = () => {
  const page = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

  return (
    <Grid container maxWidth="100%" gap={4}>
      {page.map((num) => (
        <Grid key={num} item xs={11} sx={{ height: '100px' ,mb:3}}>
          <Paper elevation={3} sx={{ padding: 2}}>
            <Grid container sx={{ height: '100%' }}>
              <Grid item xs={1} mr={2}>
                <Skeleton variant="rectangular" width={'100%'} height={'100%'} />
              </Grid>
              <Grid item xs={2} height="100%">
                <Skeleton animation="wave" sx={{ fontSize: '2rem', mt: 1 }} />
                <Skeleton animation="wave" />
                <Skeleton animation="wave" />
              </Grid>
              <Grid item xs></Grid>
              <Grid item xs={2}>
                <Skeleton animation="wave" sx={{ mt: 2 }} />
                <Skeleton animation="wave" sx={{ fontSize: '2rem' }} />
              </Grid>
            </Grid>
          </Paper>
        </Grid>
      ))}
    </Grid>
  );
};

export default EndSceleton;
