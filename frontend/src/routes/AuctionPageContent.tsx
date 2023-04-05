import Container from '@mui/material/Container';
import SideBar from '../components/auction/sidebar/SideBar';
import SearchBar from '../components/auction/SearchBar';
import ContentsList from '../components/auction/contents/ContentsList';
import Grid from '@mui/material/Grid'; // Grid version 1
import { Outlet, Link, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Tooltip from '@mui/material/Tooltip';
import { useInView } from 'react-intersection-observer';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import AddIcon from '@mui/icons-material/Add';

const AuctionPageContent = () => {
  const [ref, inView] = useInView({threshold:1});
  const [size, setSize] = useState(12);
  //query키로 사용할 키 값
  const [category, setCategory] = useState(0);
  const [keyword, setKeyword] = useState('');

  const navigate = useNavigate();

  useEffect(() => {
    console.log('auctionPageContent : category : ', category, ' keyword : ', keyword);
  }, []);

  return (
    <>
      <Container>
        {!inView && 
        <Tooltip title="위로 가기" placement="top">
          <StyledUpIcon
            onClick={() => {
              window.scrollTo(0, 0);
            }}
            >
            <ArrowUpwardIcon></ArrowUpwardIcon>
          </StyledUpIcon>
        </Tooltip>
          }
        <Tooltip title="글 작성하기" placement="top">
          <StyledIcon
          style={{justifyContent:"center", alignContent:"center", alignItems:"center"}}
            onClick={() => {
              navigate('/regist');
            }}
          >
            <AddIcon fontSize="large"></AddIcon>
          </StyledIcon>
        </Tooltip>
            <div ref={ref}></div>
        <SearchBar setKeyword={setKeyword} setSize={setSize} size={size}/>
        <Grid container spacing={2}>
          <Grid item xs={2}>
            <SideBar setCategory={setCategory} />
          </Grid>
          <Grid xs={9}>
            <ContentsList size={size} category={category} keyword={keyword} />
          </Grid>
          <Grid xs={1}></Grid>
        </Grid>
      </Container>
    </>
  );
};

export default AuctionPageContent;

const StyledUpIcon = styled.div`
  z-index: 10;
  position: fixed;
  right: 10%;
  top: 80%;
  background:linear-gradient(135deg, #e570e7 0%, #79f1fc 100%);
  border-radius: 100px;
  font-size: 50px;
  color: white;
  font-weight: bold;
  width: 60px;
  height: 60px;
  text-align: center;
  line-height: 45px;
  cursor: pointer;
  transition: all 0.3s;
  &:hover {
    top: 79.5%;
    box-shadow: 1px 1rem 15px #dddddd;
  }
  &:active {
    background-color: #4285f4;
  }
`;

const StyledIcon = styled.div`
  z-index: 10;
  position: fixed;
  right: 10%;
  top: 90%;
  background:linear-gradient(135deg, #e570e7 0%, #79f1fc 100%);
  border-radius: 100px;
  font-size: 50px;
  color: white;
  font-weight: bold;
  width: 60px;
  height: 60px;
  text-align: center;
  line-height: 55px;
  cursor: pointer;
  transition: all 0.3s;
  &:hover {
    top: 89.5%;
    box-shadow: 1px 1rem 15px #dddddd;
  }
  &:active {
    background-color: #4285f4;
  }
`;
