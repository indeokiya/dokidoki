import Typography from '@mui/material/Typography';
import styled from 'styled-components';

type Props = {
  description: string;
};

const ProductDescription = ({ description }: Props) => {
  return (
    <>
      <StyledDiv>
        <Typography variant="h5" gutterBottom color="primary">
          제품 상세 설명
          <hr />
        </Typography>
        <div dangerouslySetInnerHTML={{ __html: description }} />
      </StyledDiv>
    </>
  );
};

export default ProductDescription;

const StyledDiv = styled.div`
  padding: 10%;
  box-shadow:1px 1px 5px #dddddd;
  box-sizing: border-box;
  border: 1px solid #dddddd;
  border-radius: 10px;
  background-color: #dfdfdfd;
`;
