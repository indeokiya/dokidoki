import Grid from '@mui/material/Grid'; // Grid version 1
import styled from 'styled-components';
import { useRef, useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { useInfiniteQuery } from 'react-query';
import { myPageMenuState } from 'src/store/userInfoState';
import { auctionAPI } from 'src/api/axios';
import { useInView } from 'react-intersection-observer';
import Typography from '@mui/material/Typography';
import CircularProgress from '@mui/material/CircularProgress';
import Sceleton from 'src/components/auction/contents/Sceleton';
import IngContentItem from './IngContentItem';

//입찰 중
const bidding = (page: number, size: number) => {
  return auctionAPI
    .get('/my-infos/bids', {
      params: {
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('입찰중인 데이터 >> ', data.data);
      return data.data;
    });
};

//판매중
const selling = (page: number, size: number) => {
  return auctionAPI
    .get('/my-infos/bade', {
      params: {
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('판매중인 데이터 >> ', data.data);
      return data.data;
    });
};

//관심 내역
const wishlist = (page: number, size: number) => {
  return auctionAPI
    .get('/my-infos/interests', {
      params: {
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('찜목록 >> ', data.data);
      return data.data;
    });
};

const IngContentItemList = () => {
  const size = 12;
  const menu = useRecoilValue(myPageMenuState); // 메뉴 가져옴
  const [ref, inView] = useInView(); //무한 스크롤 타겟

  //무한스크롤 구현을 위한 페이지
  const { fetchNextPage, isLoading, data, isError, isFetchingNextPage, hasNextPage } =
    useInfiniteQuery(
      ['mypage', menu.menu],
      ({ pageParam = 0 }) => {
        //조건에 따른 api 분기
        console.log('선택된 메뉴 >> ', menu.menu);
        if (menu.menu === '입찰 중') {
          return bidding(pageParam, size);
        }
        if (menu.menu === '판매 중') {
          return selling(pageParam, size);
        }
        if (menu.menu === '관심 내역') {
          return wishlist(pageParam, size);
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
    }
  }, [inView]);

  return (
    <div id="scroll">
      {isLoading && <Sceleton></Sceleton>}

      {/* 데이터가 있다면.. */}
        <Grid container spacing={2} paddingLeft={2} maxWidth="100%">
          {data !== undefined &&
            data.pages &&
            data.pages.map((data) =>
              data?.contents?.map((data: any, i: number) => (
                <Grid key={i} item xs={4} mb={4}>
                  <IngContentItem auctionData={data} />
                </Grid>
              )),
            )}
        </Grid>

      {/* 무한 스크롤을 위한 타겟 엘리먼트 */}
      <Grid container>
        {hasNextPage ? (
          isFetchingNextPage ? (
            <Grid item textAlign={'center'}>
              <CircularProgress />
            </Grid> //로딩중일 때 타겟 숨기기
          ) : (
            <Target ref={ref}></Target>
          )
        ) : (
          <Grid item textAlign={'center'}>
            <Typography variant="button" display="block" gutterBottom color={'primary'}>
              마지막 게시글 입니다.
            </Typography>
          </Grid> //마지막 페이지라면 더이상 불러올 데이터가 없음을 표시
        )}
      </Grid>
    </div>
  );
};

export default IngContentItemList;

const Target = styled.div`
  width: auto;
  height: 200px;
`;
