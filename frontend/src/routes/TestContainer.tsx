import Grid from '@mui/material/Grid';
import styled from 'styled-components';
import Typography from '@mui/material/Typography';

const TestContainer = () => {
  const list = [
    {
      time: '12:23:34',
      name: '김범식',
      price: 30000000,
    },
    {
      time: '12:23:34',
      name: '오종석',
      price: 30000000,
    },
    {
      time: '12:23:34',
      name: '윤재휘',
      price: 30000000,
    },
    {
      time: '12:23:34',
      name: '김범식',
      price: 30000000,
    },
    {
      time: '12:23:34',
      name: '오종석',
      price: 30000000,
    },
    {
      time: '12:23:34',
      name: '윤재휘',
      price: 30000000,
    },
  ];

  return (
    <BackgroundDiv>
      <Grid container>
        <Grid item xs={12}>
          <Typography variant="subtitle1" sx={{ color: '#BBCAFF' }}>
            ( + 11,000원)
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <Typography variant="h4" sx={{ color: 'white', fontWeight: 'bold' }}>
            <span>
            20,000,000 원
            </span>
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <InnerDiv>
            <Grid container>
              {list &&
                list.map((data: any, i: number) => {
                  return (
                    <Grid item xs={12} key={i} sx={{ marginBottom: '0.5rem' }}>
                      <Typography variant="caption" sx={{ fontSize: '1rem' }}>
                        [{data.time}]{' '}
                      </Typography>
                      <Typography variant="caption" color="primary" sx={{ fontSize: '1rem' }}>
                        {data.name}{' '}
                      </Typography>
                      <Typography variant="caption">님이 </Typography>
                      <Typography variant="caption" color="error" sx={{ fontSize: '1rem' }}>
                        {data.price}
                      </Typography>
                      <Typography variant="caption">원에 입찰하셨습니다.</Typography>
                    </Grid>
                  );
                })}
            </Grid>
          </InnerDiv>
        </Grid>
      </Grid>
    </BackgroundDiv>
  );
};

export default TestContainer;
const BackgroundDiv = styled.div`
  width: 400px;
  padding: 1rem;
  border-radius: 10px;
  background-color: #3a77ee;
  box-sizing: border-box;
`;

const InnerDiv = styled.div`
  margin-top: 1rem;
  padding: 1rem;
  box-sizing: border-box;
  background-color: white;
  width: 100%;
  height: 300px;
  border-radius: 10px;
`;


