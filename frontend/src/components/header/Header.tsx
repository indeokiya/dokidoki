import { Grid } from '@mui/material';
import AfterLoginMenu from './AfterloginMenu';
import BeforLoginMenu from './BeforLoginMenu';
import { useState } from 'react';
import styled from 'styled-components';
import LogoImgSrc from '../../assets/image/logo.png';
import { Link } from 'react-router-dom';

const Header = () => {
  const [isLogin, setIsLogin] = useState(true);

  const HeaderBox = styled.div`
    padding-right: 1rem;
    padding-left: 1rem;
    border-bottom: 1px solid grey;
    
  `;
  const LogoImg = styled.img`
    height: 30px;
    margin: 5px;
  `;

  let HeaderMenu;
  if (isLogin) {
    HeaderMenu = <AfterLoginMenu />;
  } else {
    HeaderMenu = <BeforLoginMenu />;
  }
  return (
    <HeaderBox>
      <Grid container alignItems={'center'}>
        <Grid item xs>
          <Link to="/">
            <LogoImg src={LogoImgSrc}></LogoImg>
          </Link>
        </Grid>
        <Grid item>{HeaderMenu}</Grid>
      </Grid>
    </HeaderBox>
  );
};

export default Header;
