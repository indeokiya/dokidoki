import { Box } from "@mui/material";
import RankLabel from "./RankLabel";
import RankBar from "./RankBar";
import { Title } from "./Title";
import Smartphone from "src/assets/image/smartphone.png";
import { useMostSaleProductsAllQuery } from "src/hooks/mostSaleProducts";


const MostSaleProductsRank = () => {
  const { data } = useMostSaleProductsAllQuery()
  const bias = "left"

  return (
    <Box sx={{
      position: 'relative',
      padding: '5% 10%',
      backgroundColor: '#3A77EE'
    }}>
      {/* 순위 제목 */}
      <Title title="! ! ! 역대 가장 많이 거래된 제품 ! ! !" />

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
          {/* 각 제품명 Label 그룹 */}
          <RankLabel bias={bias} widthSize="30%" rankData={data} />
          {/* 거래 횟수에 따른 Bar 그룹 */}
          <RankBar bias={bias} widthSize="50%" rankData={data} />
          {/* 이미지 */}
          <img src={Smartphone} style={{ width: "20%", maxWidth: "400px", maxHeight: "400px" }} />
        </div>
      </div>
    </Box>
  );
}

export default MostSaleProductsRank;