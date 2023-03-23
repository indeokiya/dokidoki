import imgSrc1 from '../../assets/image/phone1.png';
import imgSrc2 from '../../assets/image/phone2.png';
import imgSrc3 from '../../assets/image/phone3.png';
import imgSrc4 from '../../assets/image/phone4.png';
import Grid from '@mui/material/Grid';
import styled from 'styled-components';
import { useState } from 'react';

const ProductImages = () => {
  const [index, setIndex] = useState(0);
  let images = [imgSrc1, imgSrc2, imgSrc3, imgSrc4, imgSrc3];

  const StyledImg = styled.img`
    cursor: pointer;
    width: 100%;
    &:hover {
      opacity: 0.5;
    }
  `;

  return (
    <div>
      <Grid container>
        <Grid item xs={12}>
          <img src={images[index]} width={'100%'} height={400} />
        </Grid>
        {images.map((data, i) => {
          console.log('이미지 나온다~');
          return (
            <Grid key={i} item xs={2}>
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
    </div>
  );
};

export default ProductImages;
