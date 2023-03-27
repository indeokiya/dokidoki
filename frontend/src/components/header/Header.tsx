import { Grid } from '@mui/material';
import AfterLoginMenu from './AfterloginMenu';
import BeforeLoginMenu from './BeforeLoginMenu';
import { useState } from 'react';
import styled from 'styled-components';
import LogoImgSrc from '../../assets/image/logo.png';
import { Link } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { userInfoState } from 'src/store/userInfoState';

const Header = () => {
  const userInfo = useRecoilValue(userInfoState)

  return (
    <HeaderBox>
      <Grid container alignItems={'center'}>
        <Grid item xs>
          <Link to="/">
            <LogoImg src={LogoImgSrc}></LogoImg>
          </Link>
        </Grid>
        <Grid item>{userInfo.is_logged_in ? <AfterLoginMenu /> : <BeforeLoginMenu />}</Grid>
      </Grid>
    </HeaderBox>
  );
};

export default Header;

const HeaderBox = styled.div`
padding-right: 1rem;
padding-left: 1rem;
border-bottom: 1px solid grey;
`;
const LogoImg = styled.img`
height: 30px;
margin: 5px;
`;