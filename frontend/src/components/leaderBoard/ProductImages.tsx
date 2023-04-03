import Grid from '@mui/material/Grid';
import styled from 'styled-components';
import blank_img from '../../assets/image/blank_img.png';
import { useState } from 'react';
import Timer from './timer/Timer';
import IconButton from '@mui/material/IconButton';

import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';
import RadioButtonUncheckedIcon from '@mui/icons-material/RadioButtonUnchecked';
type Props = {
  images: any[];
  end_time: string;
};

const ProductImages = ({ images, end_time }: Props) => {
  const [index, setIndex] = useState(0);

  return (
    <div>
      <Grid
        container
        spacing={1}
        sx={{
          height: '738px',
          border: '1px solid #dddddd',
          borderRadius: '10px',
          padding: '10px',
          boxSizing: 'border-box',
          boxShadow: '1px 1px 5px #dddddd',
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
        <Grid item xs={12}>
          <Grid container justifyContent="center" alignItems="center">
            {images.map((data, i) => {
              return (
                <Grid
                  key={i}
                  item
                  xs={1}
                  onClick={() => {
                    setIndex(i);
                  }}
                >
                  <IconButton>
                    {i === index ? (
                      <RadioButtonCheckedIcon color="primary" fontSize="small"/>
                    ) : (
                      <RadioButtonUncheckedIcon fontSize="small"/>
                    )}
                  </IconButton>
                </Grid>
              );
            })}
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <StyledTimer>
            <Timer end_time={end_time} />
          </StyledTimer>
        </Grid>
      </Grid>
    </div>
  );
};

export default ProductImages;

const StyledTimer = styled.div`
  text-align: center;
  height: 50px;
  font-size: 2rem;
  line-height: 25px;
  padding: 5px;
  box-sizing:border-box;
  margin-bottom:20px;
  object-fit: cover;
`;

const StyledBigImg = styled.img`
  width: 100%;
  max-height: 400px;x
`;

 