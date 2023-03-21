import { Grid } from "@mui/material";
import AfterLoginMenu from "./AfterloginMenu";
import BeforLoginMenu from "./BeforLoginMenu";
import {useState}from 'react';
import styled from "styled-components";
import LogoImgSrc from "../../assets/image/logo.png"


const Header = () => {

  const [isLogin, setIsLogin] = useState(true);

  const HeaderBox = styled.div`
    padding-right:1rem;
    padding-left:1rem;
    border-bottom : 1px solid grey;
  `
  const LogoImg = styled.img`
    height:30px;
    margin:5px;
  `

  let HeaderMenu;
  if(isLogin){
    HeaderMenu = <AfterLoginMenu/>
  }else{
    HeaderMenu = <BeforLoginMenu/>
  }
  return (
    <HeaderBox>
    <Grid container spacing={2}  alignItems={'center'}>
      <Grid item xs>
        <LogoImg src={LogoImgSrc}></LogoImg>
      </Grid>
      <Grid item>
        {HeaderMenu}
      </Grid>
    </Grid>
    </HeaderBox>
  );
};

export default Header;
