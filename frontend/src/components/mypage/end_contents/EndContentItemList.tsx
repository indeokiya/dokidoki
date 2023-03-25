import EndContentItem from './EndContentItem';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import styled from 'styled-components';
import Divider from '@mui/material/Divider';

const EndContentItemList = () => {
  const list = [1, 2, 3, 4, 5, 6, 7, 8];

  return (
    <div>
        <Typography variant="h6">총 구매 금액 : 2000</Typography>
        <Divider  sx={{padding:"10 0"}}/>
        
      <Grid container gap={2} maxWidth="100%">
        {list.map((data, i) => {
          return (
            <Grid item key={i} my={1} xs={11} >
              <EndContentItem></EndContentItem>
            </Grid>
          )
        })}
      </Grid>
    </div>
  );
};

export default EndContentItemList;
