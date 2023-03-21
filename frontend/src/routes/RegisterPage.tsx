import { Form } from "react-router-dom";
import styled from "styled-components";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import { useState, useRef } from "react";
import ProductInfoInput from "../components/resigter/ProductInfoInput";
import ActionInfoInput from "../components/resigter/ActionInfoInput";

const RegisterPage = () => {
  const StyledDiv = styled.div`
    width: 100%;
    height: 1000%;
    background-color: gainsboro;
  `;

  return (
    <>
      <StyledDiv>
        <Form>
          {/* 전체를 감싸는 Grid */}
          <Grid
            container
            alignItems={"center"}
            direction="column"
            gap={4}
            py={5}
          >
            {/*상단 입력 박스 */}
            <Grid item xs={6}>
              <ProductInfoInput />
            </Grid>

            {/* 하단 입럭 박스 */}
            <Grid item xs={6}>
              <ActionInfoInput />
            </Grid>

            {/* 하단 버튼 */}
            <Grid item xs={6}>
              <Stack spacing={2} direction="row">
                <Button
                  variant="contained"
                  color="error"
                  sx={{ width: 340, fontSize: 25 }}
                >
                  취소
                </Button>
                <Button variant="contained" sx={{ width: 340, fontSize: 25 }}>
                  등록
                </Button>
              </Stack>
            </Grid>
          </Grid>
        </Form>
      </StyledDiv>
    </>
  );
};

export default RegisterPage;
