import { RankData } from 'src/datatype/datatype';
import styled from 'styled-components';
import Tooltip from '@mui/material/Tooltip';

const RankLabel = ({
  widthSize,
  rankData,
  bias,
}: {
  widthSize: string;
  rankData: RankData[];
  bias: string;
}) => {
  return (
    <StyledRankLabel style={{ width: widthSize }} bias={bias}>
      {rankData !== undefined
        ? rankData.map((rank, index) => (
            <Tooltip key={index} title={rank.label}  arrow placement={bias === "right" ? "top-end":"top-start"}>
              <StyledLabel key={index} bias={bias}>
                {rank.label}
              </StyledLabel>
            </Tooltip>
          ))
        : null}
    </StyledRankLabel>
  );
};

export default RankLabel;

const StyledRankLabel = styled.div<{
  bias: string;
}>`
  display: inline-block;
  color: #ecf4ff;
  font-size: 20px;

  font-weight: bold;
  ${({ bias }) =>
    bias === 'left' ? 'border-right: 3px solid #ECF4FF;' : 'border-left: 3px solid #ECF4FF;'}
`;
const StyledLabel = styled.div<{
  bias: string;
}>`
  height: calc(20% - 24px);
  max-height: 45px;

  padding: 12px 36px;

  ${({ bias }) => (bias === 'left' ? 'text-align: right;' : 'text-align: left;')}
  align-items: center;
  ${({ bias }) => (bias === 'left' ? 'justify-content: right;' : 'justify-content: left;')}

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;
