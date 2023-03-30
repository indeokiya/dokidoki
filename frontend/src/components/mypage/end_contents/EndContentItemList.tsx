import EndContentItem from './EndContentItem';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import { auctionAPI } from 'src/api/axios';
import { useInfiniteQuery } from 'react-query';
import { myPageMenuState } from 'src/store/userInfoState';
import CircularProgress from '@mui/material/CircularProgress';
import Sceleton from 'src/components/auction/contents/Sceleton';
import { useRecoilValue } from 'recoil';
import { useInView } from 'react-intersection-observer';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Post } from 'src/datatype/datatype';

//구매 내역
const purchaseHitory = (page: number, size: number) => {
  return auctionAPI
    .get('/my-infos/purchases', {
      params: {
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('구매한 데이터 >> ', data.data);
      return data.data;
    });
};

//판매 내역
const salesHistory = (page: number, size: number) => {
  return auctionAPI
    .get('/my-infos/sales', {
      params: {
        page,
        size,
      },
    })
    .then(({ data }) => {
      console.log('판매한 데이터 >> ', data.data);
      return data.data;
    });
};

const EndContentItemList = () => {
  const [posts, setPosts] = useState<Post[]>();

  const menu = useRecoilValue(myPageMenuState); // 메뉴 가져옴
  const [ref, inView] = useInView(); //무한 스크롤 타겟

  const size = 12;
  const { fetchNextPage, isLoading, data, isError, isFetchingNextPage, hasNextPage } =
    useInfiniteQuery(
      ['mypage', menu.menu],
      ({ pageParam = 0 }) => {
        //조건에 따른 api 분기
        console.log('선택된 메뉴 >> ', menu.menu);
        if (menu.menu === '구매 내역') {
          return purchaseHitory(pageParam, size);
        }
        if (menu.menu === '판매 내역') {
          return salesHistory(pageParam, size);
        }
      },
      {
        getNextPageParam: (lastPage, allPages) => {
          // lastPage 값이 존재하지 않으면 false를 반환
          if (!lastPage) {
            return false;
          }

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

  //타겟이 화면에 보이면 다음 화면을 불러올 준비를 한다.
  useEffect(() => {
    //타겟이 화면에 보이고 && 지금 데이터를 불러오는 중이 아니라면
    if (inView && !isFetchingNextPage) {
      fetchNextPage();
    }
  }, [inView]);

  if (isLoading) {
    return (
      <div id="scroll">
        <Sceleton />
      </div>
    );
  }

  console.log('data >>', data);
  console.log('posts >> ', posts);

  return (
    <div id="scroll">


      {/* 데이터가 있다면.. */}
      <Grid container spacing={2} paddingLeft={2} maxWidth="100%">
          {data !== undefined &&
            data.pages &&
            data.pages.map((data) =>
              data?.histories?.map((data: any, i: number) => (
                <Grid key={i} item xs={11} mb={4}>
                  <EndContentItem auctionData={data}/>
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

export default EndContentItemList;

const Target = styled.div`
  width: auto;
  height: 200px;
`;
