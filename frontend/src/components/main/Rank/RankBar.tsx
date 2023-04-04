import { RankData } from "src/datatype/datatype";
import styled from "styled-components";

const RankBar: React.FC<{ widthSize: string, rankData: RankData[] }> = ({ widthSize, rankData }) => {
  if (rankData === undefined || rankData.length === 0)
    return null;
  
  const MAX_VALUE = rankData[0].value
  console.log(rankData)
  console.log(rankData[0].value / MAX_VALUE * 100)
  console.log(rankData[1].value / MAX_VALUE * 100)
  return (
    <StyledRankBar style={{width: widthSize}}>
      {rankData.map(rank =>
        <HorizontalBar percent={rank.value / MAX_VALUE * 100}>
          {rank.value} 원
        </HorizontalBar>
      )}
    </StyledRankBar>
  );
};

export default RankBar;

const StyledRankBar = styled.div`
  
`;

const HorizontalBar = styled.div<{
  percent: number
}>`
  color: #3A77EE;
  background-color: #ECF4FF;

  width: calc( ${({ percent }) => percent}% - 6px );
  height: calc( 20% - 6px );
  margin: 12px;
`;