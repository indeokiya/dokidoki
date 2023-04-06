import { RankData } from 'src/datatype/datatype';
import styled, {keyframes} from 'styled-components';
import MyPoint from 'src/components/header/MyPoint';

const RankBar = ({
  widthSize,
  rankData,
  bias,
  animation
}: {
  widthSize: string;
  rankData: RankData[];
  bias: string;
  animation:boolean;
}) => {
  if (rankData === undefined || rankData.length === 0) return null;

  // 정수 포맷팅


  const MAX_VALUE = rankData[0].value;
  return (
    <div style={{ width: widthSize }}>
      {rankData.map((rank, index) => (
        <EmptyBar key={index} bias={bias} start={animation}>
          <HorizontalBar percent={(rank.value / MAX_VALUE) * 100} bias={bias}>
            <MyPoint animation={false} max={rank.value} increase={rank.value}/>
          </HorizontalBar>
        </EmptyBar>
      ))}
    </div>
  );
};

export default RankBar;
const animation = keyframes`
  from{
    opacity:0;
    width:50%;
  }to{
    opacity:1;
    width:95%;
  }
`

const EmptyBar = styled.div<{
  bias: string;
  start:boolean;
}>`
  width: calc(100% - 24px);
  height: calc(20% - 24px);
  maxheight: 45px;
  padding: 12px;
  display: flex;
  opacity:0;
  justify-content: ${({ bias }) => bias};
  animation : ${({start}) => start ? animation : ""};
  animation-duration : 2s;
  animation-fill-mode: forwards;
  over-flow:hidden;
  `;

const HorizontalBar = styled.div<{
  percent: number;
  bias: string;
}>`
  text-align: ${({ bias }) => bias};
  font-size: 1.5rem;
  font-family: WorkSans;
  color: #3a77ee;
  background-color: #ecf4ff;

  min-width: 30%;
  width: calc(${({ percent }) => percent}% - 16px);
  height: calc(100%);
  padding: 0 8px;

  display: flex;
  align-items: center;
  ${({ bias }) => (bias === 'left' ? 'justify-content: right;' : 'justify-content: left;')}
`;
