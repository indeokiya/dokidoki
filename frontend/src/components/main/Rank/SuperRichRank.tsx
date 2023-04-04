import { Box } from "@mui/material";
import { useSuperRich } from "src/hooks/superRich";
import RankLabel from "./RankLabel";
import RankBar from "./RankBar";
import { Title } from "./Title";
import Smartphone from "src/assets/image/smartphone.png";


const SuperRichRank = () => {
  const { data } = useSuperRich()
  const bias = "right"

  return (
    <Box sx={{
      position: 'relative',
      padding: '5% 10%',
      backgroundColor: '#3A77EE'
    }}>
      {/* 순위 제목 */}
      <Title title="! ! ! 경매를 열어 알부자가 되어보세요 ! ! !" />

      {/* 컴포넌트 가운데 정렬 용도의 div */}
      <div style={{
        display: "flex",
        justifyContent: "center"
      }}>
        {/* 순위 컴포넌트 Wrapper */}
        <div style={{
          width: "100%",
          maxWidth: "2140px",
          display: "flex",
          justifyContent: "center"
        }}>
          {/* 이미지 */}
          <img src={Smartphone} style={{ width: "30%", maxWidth: "400px", maxHeight: "400px" }} />
          {/* 지닌 포인트에 따른 Bar 그룹 */}
          <RankBar bias={bias} widthSize="50%" rankData={data} />
          {/* 각 사용자 이름 Label 그룹 */}
          <RankLabel bias={bias} widthSize="20%" rankData={data} />
        </div>
      </div>
    </Box>
  );
}

export default SuperRichRank;