import Grid from '@mui/material/Grid'; // Grid version 1
import Content from './Content';
import styled from 'styled-components';
import { useRef, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Badge from '@mui/material/Badge';

const ContentsList = () => {
  const [posts, setPosts] = useState([
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
  ]);
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(true);
  const Target = styled.div`
    width: auto;
    height: 200px;
  `;

  //타겟 설정
  const targetRef = useRef<HTMLDivElement>(null);

  //스크롤 option 설정
  const options = {
    rootMargin: '0px',
    threshold: 0.5,
  };

  //
  const observer = new IntersectionObserver(() => {
    console.log('api 호출하기 ');
    const target: any = targetRef.current;
    // observer.unobserve(target); //이순간 옵져버 함수가 다시 실행된다. 즉 시작하자마자 호출되는걸 방지해야함
    // setPosts([...posts,1,2,3])
    setIsLoading(false);
    setTimeout(() => {
      setIsLoading(true);
    }, 2000);
  }, options);

  useEffect(() => {
    const target: any = targetRef.current;
    observer.observe(target);
  }, []);

  return (
    <div id="scroll">
      <Grid container spacing={2} paddingLeft={2} maxWidth="100%">
        {posts.map((data, i) => (
          <Grid key={i} item xs={4} mb={4}>
            <div
              onClick={() => {
                navigate('/product/' + data);
              }}
            >
                <Content />
            </div>
          </Grid>
        ))}
        <Grid item xs={12}>
          {isLoading ? (
            <div
              id="test"
              style={{ height: '100px', border: '1px solid black' }}
              ref={targetRef}
            ></div>
          ) : (
            <h1>로딩중...</h1>
          )}
        </Grid>
      </Grid>
    </div>
  );
};

export default ContentsList;
