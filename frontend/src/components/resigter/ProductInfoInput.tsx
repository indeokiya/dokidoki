import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css";
import {Paper, Grid, Typography, Divider, TextField, debounce} from "@mui/material"
import styled from "styled-components";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import { useRef, useState, useCallback, useEffect } from "react";
import { AuctionRegisterType } from "../../routes/RegisterPage";
import { useReadProductsQuery } from "src/hooks/product";


type AuctionRegisterProps = {
  data: AuctionRegisterType // 부모컴포넌트에서 import 해온 타입을 재사용 해 줍시다.
  // onChange(key: string): void
}


const ProductInfoInput = ( {dataRef} : any ) : React.ReactElement => {
  const [imageCnt,setImageCnt] = useState(0)
  const editorRef: any = useRef(null);
  
  // 제품 데이터 가져오는 쿼리, refetch를 이용해 검색 API 요청
  const { data: productsData, refetch } = useReadProductsQuery("")
  
  // 제품 데이터 State
  const [products, setProducts] = useState([])

  // 제품 검색 debounce
  const searchProducts = debounce(() => {
    // 검색어 추출
    const keyword = dataRef?.current?.category

    // 검색어가 비어있다면 종료
    if (!keyword || keyword === "")
      return

    refetch(keyword)
  }, 500)

  // 새로운 제품 정보가 로딩되었다면 state 삽입
  useEffect(() => {
    setProducts(productsData)
  }, [productsData])

  useEffect(() => {
    console.log(products)
  }, [products])

  const onChange = (e: any) => {
    const { value, name } = e.target; // 우선 e.target 에서 name 과 value 를 추출
    dataRef.current[name] = value;
    // console.log("name >> ", name, dataRef.current[name])
    
    console.log(name, value)
    // 카테고리 입력 시 debounce 사용하여 제품 검색 API 호출
    if (name === "category") {
      searchProducts()
    }
  };

  return (
    <>
      <Paper elevation={3} sx={{ width: 600, padding: 10 }}>
        {/* 상단 Grid */}
        <Grid container direction="row">
          <Grid item xs={12}>
            <Typography variant="h5" fontWeight="bold" color={"#3A77EE"}>제품 정보</Typography>
            <Divider sx={{ margin: "2rem 0px" }} />
          </Grid>
          {/* 이미지 삽입 */}
          <Grid item xs={2}>
            <Typography variant="subtitle1">사진 : </Typography>
          </Grid>
          <Grid item xs={10} mb={1}>
            <label htmlFor="imageInput">
              <ImageInputLabel>
                <AddAPhotoIcon />
                {imageCnt}/5
              </ImageInputLabel>
            </label>
            <ImageInput
              id="imageInput"
              type="file"
              accept="image/*"
              // accept=".jpg,.jpeg,.png"
              multiple
              onChange={(e: any) => {
                const files: any[] = e.target.files;
                setImageCnt(files.length)
                if (files.length > 5) {
                  alert("사진을 5개 이상 등록할 수 없습니다.");
                  e.target.value = null;
                  return;
                }
                console.log("files >> ", files);
                dataRef.current.files = files;
                console.log("dataRef.current.files >> ", dataRef.current.files);
              }}
            />
          </Grid>

          {/* 글 제목 */}
          <Grid item xs={2}>
            <Typography variant="subtitle1">제목 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              label="title"
              variant="outlined"
              fullWidth
              name="title"
              onChange={onChange}
            />
          </Grid>

          {/* 카테고리 선택 */}
          <Grid item xs={2}>
            <Typography variant="subtitle1">카테고리 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              label="category"
              variant="outlined"
              fullWidth
              name="category"
              onChange={onChange}
            />
          </Grid>

          {/* 제품 설명 */}
          <Grid item xs={2}>
            <Typography variant="subtitle1">상세설명 : </Typography>
          </Grid>
          <Grid item xs={10}>
            <Editor
              ref={editorRef}
              initialValue=""
              previewStyle="tab"
              height="400px"
              useCommandShortcut={false}
              usageStatistics={false}
              onChange={(e) => {
                dataRef.current["description"] = editorRef.current.getInstance().getHTML();
                // console.log(dataRef.current["category"])
              }}
            />
          </Grid>
        </Grid>
      </Paper></>
  )
}

export default ProductInfoInput;

const ImageInputLabel = styled.div`
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    background-color: gainsboro;
    border-radius: 10px;
    cursor: pointer;
  `;

const ImageInput = styled.input`
    visibility: hidden;
  `;