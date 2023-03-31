import Grid from '@mui/material/Grid';
import styled from 'styled-components';
import blank_img from '../../assets/image/blank_img.png';
import { useState } from 'react';

type Props = {
  images: any[];
};

const ProductImages = ({ images }: Props) => {
  const [index, setIndex] = useState(0);
  // let images = [imgSrc1, imgSrc2, imgSrc3, imgSrc4, imgSrc3];

  const StyldImg = styled.img`
  `;

  return (
    <div>
      <Grid container>
        <Grid
          item
          xs={12}
          height={400}
          sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}
         
        >
          {images.length === 0 ? (
            <StyldImg src={blank_img} width={'100%'} />
          ) : (
            <StyldImg src={images[index]}width={'100%'} />
          )}
        </Grid>
        {images.map((data, i) => {
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

const StyledImg = styled.img`
  cursor: pointer;
  width: 100%;
  &:hover {
    opacity: 0.5;
  }
`;
