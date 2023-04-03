 
 import styled from 'styled-components';
 import Grid from '@mui/material/Grid';
 import ScrollTop from 'src/components/util/ScrollTop';
 import { Box } from '@mui/material';
 import Divider from '@mui/material/Divider';
 import Skeleton from '@mui/material/Skeleton';



 const TestContainer=()=>{


    return(
        <>
              <BackgroundDiv>
        <Box
          sx={{
            border: '1px solid white',
            backgroundColor: 'white',
            width: '1000px',
            margin: '0 auto',
            padding: '50px',
            borderRadius: '10px',
            boxShadow: '1px 1px 10px grey',
          }}
        >
          <ScrollTop />
          <Grid container spacing={3} sx={{ marginBottom: '5%' }}>
            <Grid item xs={6}>
              {/* 제품 이미지 */}
              <Skeleton variant="rectangular" width={"100%"} height={600} />
            </Grid>
            <Grid item xs={6}>
              {/* 제품 정보 */}
              <br/>
              <Skeleton variant="text" sx={{ fontSize: '2rem' }} />
              <Skeleton variant="text" sx={{ fontSize: '15rem' }} />
              <Skeleton variant="rectangular" width={"100%"} height={200} />
              <Skeleton variant="rectangular" width={"100%"} sx={{marginTop:"5px"}} height={60} />
            </Grid>
          </Grid>
          <Divider />
          <Grid container direction="column" justifyContent="center" alignItems="center">
            {/* 제품 카테고리 평균 가격 */}

            <Grid item xs={12} sx={{ width: '100%', marginBottom: '10px' }}>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                <Skeleton variant="rectangular" width={"100%"} height={200} />
                </Grid>
              </Grid>
            </Grid>

            {/* 제품 설명 */}
            <Grid item sx={{ width: '100%' }}>
            <Skeleton variant="rectangular" width={"100%"} height={200} />
            </Grid>
            {/* 지도 */}
            <Grid item sx={{ width: '100%' }}>
            <Skeleton variant="rectangular" width={"100%"} height={200} />
            </Grid>

            {/* 댓글 */}
            <Grid item sx={{ width: '100%' }}>
            <Skeleton variant="rectangular" width={"100%"} height={200} sx={{marginTop:"15px"}}  />
            <Skeleton variant="rectangular" width={"100%"} height={200} sx={{marginTop:"15px"}}  />
            <Skeleton variant="rectangular" width={"100%"} height={200} sx={{marginTop:"15px"}}  />
            <Skeleton variant="rectangular" width={"100%"} height={200} sx={{marginTop:"15px"}}  />

            </Grid>
          </Grid>
        </Box>
      </BackgroundDiv>
        </>
    )
  
}

export default TestContainer;

const BackgroundDiv = styled.div`
  background-color: #dddddd;
  padding-top: 30px;
  padding-bottom: 100px;
`;
