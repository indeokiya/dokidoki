import { RankData } from "src/datatype/datatype";
import styled from "styled-components";

const RankLabel: React.FC<{ widthSize: string, rankData: RankData[] }> = ({ widthSize, rankData }) => {
  return (
    <StyledRankLabel style={{width: widthSize}}>
      {rankData !== undefined
        ? rankData.map((rank, index) => (
          <StyledLabel key={index}>
            {rank.label}
          </StyledLabel>
        )): null}
    </StyledRankLabel>
  );
};

export default RankLabel;

const StyledRankLabel = styled.div`
  display: inline-block;
  color: #ECF4FF;
  font-size: 2rem;
  font-weight: bold;
`;
const StyledLabel = styled.div`
  height: calc( 20% - 6px );
  margin: 12px;
`;