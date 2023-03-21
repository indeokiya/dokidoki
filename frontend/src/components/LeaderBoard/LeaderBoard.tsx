import * as React from "react";
import Button from "@mui/material/Button";
import Dialog, { DialogProps } from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Grid from "@mui/material/Grid";
import ProductImages from "./ProductImages";
import ProductInfo from "./ProductInfo";
import Divider from "@mui/material/Divider";
import Container from "@mui/material/Container";
import styled from "styled-components";
import ProductGraph from "./ProductGraph";
import ProductDescription from "./ProductDescription";
import CommentsList from "./comments/CommentsList";

const LeaderBoard: React.FC<{ open: boolean; onClose: () => void }> = (
  props
) => {
  const [open, setOpen] = React.useState(props.open); //이거 삭제 해야함
  const [scroll, setScroll] = React.useState<DialogProps["scroll"]>("paper");

  const handleClose = () => {
    props.onClose();
  };

  const descriptionElementRef = React.useRef<HTMLElement>(null);
  React.useEffect(() => {
    if (open) {
      const { current: descriptionElement } = descriptionElementRef;
      if (descriptionElement !== null) {
        descriptionElement.focus();
      }
    }
  }, [open]);

  const StyledDiv = styled.div`
    padding: 30px;
    box-sizing: border-box;
  `;

  return (
    <Container>
      {/* 모달창 열기  */}
      <Dialog
        fullWidth
        maxWidth="lg"
        open={props.open}
        onClose={handleClose}
        scroll={"body"}
        aria-labelledby="scroll-dialog-title"
        aria-describedby="scroll-dialog-description"
      >
        <DialogTitle id="scroll-dialog-title">제품 이름</DialogTitle>

        <DialogContent dividers={scroll === "paper"}>

          
          <Grid container spacing={3} mb={10}>
            <Grid item xs={1} />
            <Grid item xs={5}>
              {/* 제품 이미지 */}
              <ProductImages />
            </Grid>
            <Grid item xs={5}>
              {/* 제품 정보 */}
              <ProductInfo />
            </Grid>
          </Grid>
          <Divider />

          {/* 제품 상세 설명 */}
          <DialogContentText
            id="scroll-dialog-description"
            ref={descriptionElementRef}
            tabIndex={-1}
          >
            <StyledDiv>
              <ProductDescription />
            </StyledDiv>
          </DialogContentText>
        </DialogContent>

        {/* 제품 카테고리 평균 가격 */}
        <DialogContent>
          <ProductGraph />
        </DialogContent>

        {/* 댓글 작성과 댓글들  */}
        <DialogContent>
          <CommentsList/>
        </DialogContent>

        {/* 모달창 하단에 존재하는 버튼 */}
        <DialogActions>
          <Button onClick={handleClose}>나가기</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default LeaderBoard;
