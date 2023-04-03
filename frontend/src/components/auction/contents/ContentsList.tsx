import Grid from '@mui/material/Grid';
import Content from './Content';
import { auctionAPI } from '../../../api/axios';
import { useInfiniteQuery } from 'react-query';
import {  useEffect } from 'react';
import { useInView } from 'react-intersection-observer';
import CircularProgress from '@mui/material/CircularProgress';
import styled from 'styled-components';
import Typography from '@mui/material/Typography';
import IngSceleton from 'src/components/sceleton/IngSceleton';
import ErrorImg from '../../../assets/image/error_page.png'

//get 함수로 전체 경매 불러오기
const getInProgress = (category_id: number, keyword: string, page: number, size: number) => {
  return auctionAPI
    .get('/lists/in-progress', {
      params: {
        category_id,
        keyword,
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('경매중인 물건 >> ', data.data);
      return data.data;
    });
};

//경매가 종료된 물건 불러오기
const getEndList = (page: number, size: number) => {
  return auctionAPI
    .get('/lists/end', {
      params: {
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('경매가 끝난 물건 >> ', data.data);
      return data.data;
    });
};

//마감이 임박한
const getDeadline = (page: number, size: number) => {
  return auctionAPI
    .get('/lists/deadline', {
      params: {
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('마감이 임박한 경매 데이터 >> ', data.data);
      return data.data;
    });
};

//================================================================= 컴포넌트 시작

const ContentsList: React.FC<{ category: number; keyword: string; size: number }> = (props) => {
  let { category, keyword, size } = props;

  const [ref, inView] = useInView();

  //무한스크롤 구현을 위한 페이지
  const { fetchNextPage, isLoading, data, isError, isFetchingNextPage, hasNextPage } =
    useInfiniteQuery(
      ['infinity', category, keyword, size],
      ({ pageParam = 0 }) => {
        console.log('useInfinity 함수 동작하는 중~');
        console.log('category >> ', category);
        console.log('keyword >> ', keyword );

        //조건에 따른 api 분기
        if (category < 9) {
          return getInProgress(category, keyword, pageParam, size); // 여기서 분기할 수 있을 것 같음
        } else if(category === 9){
          return getDeadline(pageParam, size);
        }else{
          return getEndList(pageParam,size);
        }
      },
      {
        getNextPageParam: (lastPage, allPages) => {
          //is_last:true이면 false리턴해서 hasNextpage 변수를 false로 변경해줌 => 무한스크롤 정지
          if (lastPage.is_last) {
            return false;
          } else {
            return allPages.length;
          }
        },
        retry: 0,
      },
    );

  useEffect(() => {
    if (inView && !isFetchingNextPage) {
      fetchNextPage();
      console.log("hasNextPage >> ",hasNextPage)
    }
  }, [inView]);

  if (isError) return <StylecError src={ErrorImg}></StylecError>;

  // data is not undefined
  if ( !isLoading){
    console.log(data)
  }


  if(isLoading){
    return <IngSceleton/>
  }

  return (
    <>
      {/* 데이터가 있다면.. */}
      {data !== undefined && (
        <Grid container spacing={2} paddingLeft={2} maxWidth="100%">
          {data.pages.map((data) =>
            data.contents.map((data: any, i: number) => (
              <Grid key={i} item xs={4} mb={4}>
                <Content auctionData={data} />
              </Grid>
            )),
          )}
        </Grid>
      )}

      {/* 무한 스크롤 타겟 */}
      <Grid container>
      {hasNextPage ? (
        isFetchingNextPage ? (
          <Grid item xs={12} alignItems={"center"}  textAlign={"center"}><CircularProgress/></Grid> //로딩중일 때 타겟 숨기기
        ) : (
          <Target ref={ref}></Target>
        )
      ) : (
        <Grid item xs={12} alignItems={"center"} textAlign={"center"}> 
           <Typography variant="button" display="block" gutterBottom color={'primary'}>
        마지막 게시글 입니다.
      </Typography>
        </Grid> //마지막 페이지라면 더이상 불러올 데이터가 없음을 표시
      )}
</Grid>
     
    </>
  );
};

export default ContentsList;

const Target = styled.div`
margin-top:50px;
`

const StylecError = styled.img`
  width:100%
`