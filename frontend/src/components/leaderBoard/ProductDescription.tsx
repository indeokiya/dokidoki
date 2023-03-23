import Typography from '@mui/material/Typography';
import styled from 'styled-components';

const ProductDescription = () => {
  const StyledDiv = styled.div`
    padding: 10px;
    box-sizing: border-box;
  `;
  return (
    <StyledDiv>
      <Typography variant="h1" gutterBottom>
        h1. Heading
      </Typography>
    </StyledDiv>
  );
};

export default ProductDescription;
