import { RankData } from "src/datatype/datatype";
import styled from "styled-components";

const RankLabel = ({
  widthSize,
  rankData,
  bias
}: {
  widthSize: string,
  rankData: RankData[],
  bias: string
}) => {
  return (
    <StyledRankLabel style={{ width: widthSize }} bias={bias}>
      {rankData !== undefined
        ? rankData.map((rank, index) => (
          <StyledLabel key={index} bias={bias} title={rank.label}>
            {rank.label}
          </StyledLabel>
        )) : null}
    </StyledRankLabel>
  );
};

export default RankLabel;

const StyledRankLabel = styled.div<{
  bias: string
}>`
  display: inline-block;
  color: #ECF4FF;
  font-size: 20px;
 
  font-weight: bold;
  ${({ bias }) => bias === "left"
    ? "border-right: 3px solid #ECF4FF;"
    : "border-left: 3px solid #ECF4FF;"}
`;
const StyledLabel = styled.div<{
  bias: string
}>`
  height: calc( 20% - 24px );
  max-height: 45px;

  padding: 12px 36px;

  ${({ bias }) => bias === "left"
    ? "text-align: right;"
    : "text-align: left;"}
  align-items: center;
  ${({ bias }) => bias === "left"
    ? "justify-content: right;"
    : "justify-content: left;"}

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;