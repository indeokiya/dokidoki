import Grid from '@mui/material/Grid';
import Content from './Content';
import { auctionAPI } from '../../../api/axios';
import { useInfiniteQuery } from '@tanstack/react-query';
import { Post, endPost } from '../../../datatype/datatype';
import { useState, useEffect, useRef } from 'react';
import {useInView} from "react-intersection-observer";

//get 함수로 전체 경매 불러오기
const getInProgress = (page: number, size: number) => {
  return auctionAPI
    .get('/lists/in-progress', {
      params: {
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('경매중인 물건 : ', data.data.contents);
      return data.data.contents;
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
      console.log('경매가 끝난 물건 : ', data.data.contents);
      return data.data.contents;
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
      console.log('마감이 임박한 경매 데이터 : ', data.data.contents);
      return data.data.contents;
    });
};

//키워드와 페이지로 불러오는 데이터 타입
const getSearchByKeyword = (category_id: number, keyword: string, page: number, size: number) => {
  return auctionAPI
    .get(`/lists/search`, {
      params: {
        category_id,
        keyword,
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('키워드로 불러온 데이터 : ', data.data.contents);
      return data.data.contents;
    });
};

//================================================================= 컴포넌트 시작

const ContentsList: React.FC<{ category: number; keyword: string }> = (props) => {
  let { category, keyword } = props;

  const[ref ,inView] = useInView();



  //무한스크롤 구현을 위한 페이지
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(12);

  

  const { fetchNextPage, isLoading, data, isError, isFetchingNextPage, hasNextPage } =
    useInfiniteQuery<Post[]>(
      ['infinity', category, keyword],
      ({ pageParam = 0 }) => {
        console.log('useInfinity 함수 동작하는 중~');

        //조건에 따른 api 분기
        if (category == 0){
          return getInProgress(pageParam, size); // 여기서 분기할 수 있을 것 같음
        }else if(category > 0 && category < 9){
          return getSearchByKeyword(category, keyword, pageParam, size)
        }else{
          return getDeadline(pageParam, size)
        }

      },
      {
        getNextPageParam: (lastPage, allPages) => {
          if (lastPage.length < size) {
            return false;
          } else {
            return allPages.length; //여기서 return 되는 값이 pageParam에 삽입된다.
          }
        },
      },
    );

    useEffect(()=>{
      if(inView && !isFetchingNextPage){
        fetchNextPage();
      }
    },[inView])

  if (isLoading) return <h1>isLoading...</h1>;
  if (isError) return <h1>error</h1>;
  // data is not undefined

  return (
    <div id="scroll">
      {/* 데이터가 있다면.. */}
      {data.pages !== null ? (
        <Grid container spacing={2} paddingLeft={2} maxWidth="100%">
          {data.pages.map((data) =>
            data.map((data, i) => (
              <Grid key={i} item xs={4} mb={4}>
                <Content auctionData={data} />
              </Grid>
            )),
          )}
        </Grid>
      ) : (
        <h1>데이터가 없습니다.</h1>
      )}


        {/* 무한 스크롤 하단*/}
      {hasNextPage ? (

        isFetchingNextPage ? (
          <div>로딩중~</div>
        ) : (
       	<div> 더보기 버튼 있던 곳</div>
        )
      ) : (
        <div> 데이터가 없습니다.</div>
      )}

    <div ref={ref}></div>
    </div>
  );
};

export default ContentsList;
