import styled from 'styled-components';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Avatar from '@mui/material/Avatar';
import Divider from '@mui/material/Divider';
import Tooltip from '@mui/material/Tooltip';
import { useRecentPopularInfo } from 'src/hooks/recentPopularInfo';
const TestContainer = () => {

  
  
const commentStyle = [{
  padding:"10%",
},]



  const { isLoading, isError, data } = useRecentPopularInfo();
  if (isLoading) return <h1>isLoading...</h1>
  if (isError) return <h1>error occured!</h1>

  // 최근 30초간 가장 많이 클릭된 auction, 가장 많은 입찰 요청(실패 포함)이 이루어진 auction 배열. 최소 0개~5개
  const clickArr: any[] = data.click;
  const bidArr: any[] = data.bid;

  console.log("clickArr >> ", clickArr);
  console.log("bidArr >> ", bidArr);

  return (
    <>

{/* <Grid container>
  <Grid item sx={...commentStyle}>


  </Grid>
  <Grid item>

  </Grid>
</Grid> */}
    
    </>
  );
};

export default TestContainer;

const StyledLink = styled.a`
  color: white;
`;

