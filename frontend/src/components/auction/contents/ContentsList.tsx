import Grid from '@mui/material/Grid';
import Content from './Content';
import { auctionAPI } from '../../../api/axios';
import { useQuery } from '@tanstack/react-query';
import { Post, endPost } from '../../../datatype/datatype';
import { useState, useEffect } from 'react';
import { KeyboardReturn } from '@mui/icons-material';

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
      return data.data.contens;
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

  //무한스크롤 구현을 위한 페이지
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [posts, setPosts] = useState<Post[] | undefined>([]);

  let { data, isLoading, isError, error } = useQuery<Post[]>(
    ['postList', category],
    () => {
      if (category == 0) {
        return getInProgress(page, size);
      } else if (category > 0 && category < 9) {
        return getSearchByKeyword(category, keyword, page, size);
      } else {
        return getDeadline(page, size);
      }
    },
    {
      retry: 0,
      staleTime:1000*60,
      refetchOnWindowFocus:false,

    },
  );

  useEffect(() => {
    setPosts(data);
  }, []);

  if (isLoading) return <h1>isLoading...</h1>
  if (isError) return <h1>error</h1>
  // data is not undefined


  console.log('category :', category, 'keyword : ', keyword);
  return (
    <div id="scroll">
      {/* 데이터가 있다면.. */}
      {data ? (
        <Grid container spacing={2} paddingLeft={2} maxWidth="100%">
          {data?.map((data, i) => (
            <Grid key={i} item xs={4} mb={4}>
              <Content auctionData={data} />
            </Grid>
          ))}
        </Grid>
      ) : null}

      {/* 데이터가 없다면 */}
      {posts === null && <div>데이터 없음 에러 </div>}
    </div>
  );
}

export default ContentsList;
