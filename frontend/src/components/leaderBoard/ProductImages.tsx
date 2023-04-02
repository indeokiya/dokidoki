import Grid from '@mui/material/Grid';
import styled from 'styled-components';
import blank_img from '../../assets/image/blank_img.png';
import { useState, useEffect } from 'react';
import Timer from './timer/Timer';
type Props = {
  images: any[];
  end_time:string;
};

const ProductImages = ({ images, end_time }: Props) => {
  const [index, setIndex] = useState(0);

 


  return (
    <div>
      <Grid
        container
        spacing={1}
        sx={{
          height: '650px',
          border: '1px solid #dddddd',
          borderRadius: '10px',
          padding: '10px',
          boxSizing: 'border-box',
          boxShadow:"1px 1px 5px #dddddd"
        }}
      >
        <Grid
          item
          xs={12}
          sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}
          height={'550px'}
        >
          {images.length === 0 ? (
            <StyledBigImg src={blank_img} />
          ) : (
            <StyledBigImg src={images[index]} />
          )}
        </Grid>
        {images.map((data, i) => {
          return (
            <Grid key={i} item xs={2} sx={{ height: '50px' }}>
              <StyledImg
                src={data}
                onClick={() => {
                  setIndex(i);
                }}
              />
            </Grid>
          );
        })}
      </Grid>
      <StyledTimer>
 <Timer end_time={end_time}/>
      </StyledTimer>
    </div>
  );
};

export default ProductImages;

const StyledTimer = styled.div`
  text-align:center;
  height:50px;
  font-size:2rem;
  line-height:25px;
  padding:5px;
  box-sizing:border-box;
  margin-top:5px;

`

const StyledBigImg = styled.img`
  width: 100%;

  max-height: 400px;
`;

const StyledImg = styled.img`
  cursor: pointer;
  width: 100%;
  height: 100%;
  &:hover {
    opacity: 0.5;
  }
`;
