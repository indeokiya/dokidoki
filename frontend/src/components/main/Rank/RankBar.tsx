import { RankData } from "src/datatype/datatype";
import styled from "styled-components";

const RankBar = ({
  widthSize,
  rankData,
  bias
}: {
    widthSize: string,
    rankData: RankData[],
    bias: string
  }) => {
  if (rankData === undefined || rankData.length === 0)
    return null;
  
  // 정수 포맷팅
  const showValue = (point: number) => point.toString().split( /(?=(?:\d{3})+(?:\.|$))/g ).join( "," )
  
  const MAX_VALUE = rankData[0].value
  return (
    <div style={{width: widthSize}}>
      {rankData.map(rank =>
        <EmptyBar bias={bias}>
          <HorizontalBar
            percent={rank.value / MAX_VALUE * 100}
            bias={bias}
          >
            {showValue(rank.value)}
          </HorizontalBar>
        </EmptyBar>
      )}
    </div>
  );
};

export default RankBar;

const EmptyBar = styled.div<{
  bias: string
}>`
  width: calc( 100% - 24px );
  height: calc( 20% - 24px );
  maxHeight: 45px;
  padding: 12px;
  display: flex;
  justify-content: ${({ bias }) => bias};
`

const HorizontalBar = styled.div<{
  percent: number,
  bias: string
}>`
  text-align: ${({ bias }) => bias};
  font-size: 1.5rem;
  font-family: WorkSans;
  color: #3A77EE;
  background-color: #ECF4FF;

  min-width: 30%;
  width: calc( ${({ percent }) => percent}% - 16px );
  height: calc( 100% );
  padding: 0 8px;

  display: flex;
  align-items: center;
  ${({ bias }) => bias === "left"
    ? "justify-content: right;"
    : "justify-content: left;"}
`;