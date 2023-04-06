import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css";
import {Paper, Grid, Typography, Divider, TextField, debounce} from "@mui/material"
import styled from "styled-components";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import { useRef, useState, useCallback } from "react";
import { auctionAPI } from "src/api/axios";
import { isCategoryErrorState, isFilesErrorState, isTitleErrorState } from "src/store/RegisterAuctionStates";
import { useRecoilState } from "recoil";

// 제품 Response DTO
type Product = {
  product_id: number,
  name: string,
  product_name: string
}

const ProductInfoInput = ( {dataRef, update} : any ) : React.ReactElement => {
  const [imageCnt,setImageCnt] = useState(0)
  const editorRef: any = useRef(null);
  console.log(dataRef)
  // Error 체크용 전역 State
  const [isTitleError, setIsTitleError] = useRecoilState(isTitleErrorState)
  const [isCategoryError, setIsCategoryError] = useRecoilState(isCategoryErrorState)
  const [isFilesError, setIsFilesError] = useRecoilState(isFilesErrorState)

  /*
  제품 검색 관련 변수, 함수
  */
  // 검색결과 표시 여부
  const [showResult, setShowResult] = useState(false)
  // 제품 검색결과 State
  const [products, setProducts] = useState<Product[]>([])
  // 사용자가 입력할 제품 이름
  const [productName, setProductName] = useState("")
  // 제품 검색 debounce
  const searchProducts = useCallback(debounce(() => {
    // 검색어 추출
    const keyword = dataRef?.current?.category

    // 검색어가 비어있다면 종료
    if (!keyword || keyword === "")
      return

    // 제품 조회
    auctionAPI
      .get(`products?keyword=${keyword}`)
      .then(({ data }) => {
        // 제품 조회 성공 시 products 설정 및 검색결과 표시
        setProducts(data.data)
        setShowResult(true)
      }).catch(err => err)
  }, 500), [])
  // 제품 선택 시 제품 ID 설정
  const selectProduct = (event: any) => {
    const { product_id, product_name } = products[event.target.id]
    dataRef.current.product_id = product_id  // 제품 ID 설정
    setIsCategoryError(false)  // 카테고리가 바뀌면 카테고리 error 테두리 해제
    setProductName(product_name)  // 제품명 state 갱신
    setProducts([])  // 검색결과 초기화
    setShowResult(false)  // 검색결과 숨기기
  }

  const onChange = (e: any) => {
    const { value, name } = e.target; // 우선 e.target 에서 name 과 value 를 추출
    dataRef.current[name] = value;
    console.log(value, name)
    
    // 제목이 바뀌면 제목 error 테두리 해제
    if (name === "title") {
      setIsTitleError(false)
    }    
    // 카테고리 입력 시 debounce 사용하여 제품 검색 API 호출
    else if (name === "category") {
      dataRef.current.product_id = -1  // 제품 ID 초기화
      setProductName(value)  // 사용자가 입력한 제품명을 state에 반영
      setProducts([])  // 기존 검색 정보 초기화
      setShowResult(false)  // 검색결과 숨기기
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
          { !update && 
          <Grid item xs={2}>
            <Typography variant="subtitle1">사진 : </Typography>
          </Grid> } 
          { !update && 
          <Grid item xs={10} mb={1}>
            <label htmlFor="imageInput">
              <ImageInputLabel style={{border: `${isFilesError ? "1px solid red" : ""}`}}>
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
                setIsFilesError(false)
                setImageCnt(files.length)  // error flag 해제
                if (files.length > 5) {
                  alert("사진은 5개를 초과할 수 없습니다.");
                  e.target.value = null;
                  return;
                }
                console.log("files >> ", files);
                dataRef.current.files = files;
                console.log("dataRef.current.files >> ", dataRef.current.files);
              }}
            />
          </Grid> }

          {/* 글 제목 */}
          <Grid item xs={2}>
            <Typography variant="subtitle1" >제목 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              label="title"
              variant="outlined"
              fullWidth
              name="title"
              onChange={onChange}
              error={isTitleError}
              defaultValue={
                dataRef.current.title !== ""
                  ? dataRef.current.title
                  : ""}
            />
          </Grid>

          {/* 카테고리 선택 */}
          { !update && <Grid item xs={2}>
            <Typography variant="subtitle1">카테고리 : </Typography>
          </Grid> }
          { !update && <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              label="category"
              variant="outlined"
              fullWidth
              name="category"
              onChange={onChange}
              value={productName}
              error={isCategoryError}
            />
            {showResult
              ? <Paper>
                {products.length === 0
                  ? <div style={{ textAlign: "center", padding: "8px" }}>
                    검색결과가 없습니다.
                  </div>
                  : products.map((product, index) => (
                    <ResultElem
                      key={index}
                      id={`${index}`}
                      onClick={selectProduct}
                    >
                      {product.name}
                    </ResultElem>
                  ))}
              </Paper>
              : null}
          </Grid> }

          {/* 제품 설명 */}
          <Grid item xs={2}>
            <Typography variant="subtitle1">상세설명 : </Typography>
          </Grid>
          <Grid item xs={10}>
            <Editor
              ref={editorRef}
              initialValue={
                dataRef.current.description !== ""
                  ? dataRef.current.description
                  : ""}
              previewStyle="tab"
              height="400px"
              useCommandShortcut={false}
              usageStatistics={false}
              onChange={(e) => {
                dataRef.current["description"] = editorRef.current.getInstance().getHTML();
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

const ResultElem = styled.div`
  font-size: 1rem;
  text-align: center;
  padding: 16px;
  cursor: pointer;
`;