import Typography from '@mui/material/Typography';
import styled from 'styled-components';

type Props = {
  description: string,
}

// const marked = require('marked');

const ProductDescription = ({ description }: Props) => {
  const StyledDiv = styled.div`
    padding: 10px;
    box-sizing: border-box;
  `;
  return (
    <StyledDiv>
      <Typography variant="h1" gutterBottom>
        {description}
      </Typography>
    </StyledDiv>
  );
};

export default ProductDescription;
