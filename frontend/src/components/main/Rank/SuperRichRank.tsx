import { Box } from "@mui/material";
import { useSuperRich } from "src/hooks/superRich";
import styled from "styled-components";
import RankLabel from "./RankLabel";
import RankBar from "./RankBar";
import { Title } from "./Title";


const SuperRichRank = () => {
  const { data, isLoading } = useSuperRich()

  return (
    <Box sx={{
      position: 'relative',
      padding: '5% 10%',
      backgroundColor: '#3A77EE',
      display: "flex",
      justifyContent: "center"
    }}>
      <div style={{
        width: "100%",
        maxWidth: "2140px",
        display: "flex",
        justifyContent: "right"
      }}>
        <RankBar widthSize="calc( 70% - 3px )" rankData={data} />
        <VerticalBar />
        <RankLabel widthSize="calc( 30% - 3px )" rankData={data} />
      </div>
    </Box>
  );
}

export default SuperRichRank;

const VerticalBar = styled.div`
  display: inline-block;
  background-color: #ECF4FF;
  width: 6px;
  height: 100%;
`;