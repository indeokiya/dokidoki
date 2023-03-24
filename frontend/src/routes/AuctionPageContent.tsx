import Container from '@mui/material/Container';
import SideBar from '../components/auction/sidebar/SideBar';
import SearchBar from '../components/auction/SearchBar';
import ContentsList from '../components/auction/contents/ContentsList';
import Grid from '@mui/material/Grid'; // Grid version 1
import { Outlet, Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import styled from 'styled-components';
import Tooltip from '@mui/material/Tooltip';

const ActionPageContent = () => {
  const navigate = useNavigate();

  const StyledIcon = styled.div`
    position: fixed;
    right: 5%;
    top: 90%;
    background-color: #3a77ee;
    border-radius: 100px;
    font-size: 50px;
    color: white;
    font-weight: bold;
    width: 60px;
    height: 60px;
    text-align: center;
    line-height: 50px;
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

  return (
    <>
      <Container>
        <Tooltip title="글 작성하기" placement="top">
          <StyledIcon
            onClick={() => {
              navigate('/regist');
            }}
          >
            +
          </StyledIcon>
        </Tooltip>

        <SearchBar />
        <Grid container spacing={2}>
          <Grid item xs={2}>
            <SideBar />
          </Grid>
          <Grid xs={10}>
            <ContentsList />
          </Grid>
        </Grid>
      </Container>
    </>
  );
};

export default ActionPageContent;
