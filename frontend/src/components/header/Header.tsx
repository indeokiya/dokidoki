import { Grid } from '@mui/material';
import AfterLoginMenu from './AfterloginMenu';
import BeforeLoginMenu from './BeforeLoginMenu';
import styled from 'styled-components';
import LogoImgSrc from '../../assets/image/logo.png';
import { Link, useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { userInfoState } from 'src/store/userInfoState';

const Header = () => {
  const userInfo = useRecoilValue(userInfoState);
  const navigate = useNavigate();

  return (
    <HeaderBox>
      <Grid container alignItems={'center'}>
        <Grid item xs>
          <StyledLogo
            onClick={() => {
              navigate('/');
            }}
          >
            DOKIDOKI
          </StyledLogo>
        </Grid>
        <Grid item>{userInfo.is_logged_in ? <AfterLoginMenu /> : <BeforeLoginMenu />}</Grid>
      </Grid>
    </HeaderBox>
  );
};

export default Header;

const HeaderBox = styled.div`
  padding: 1rem;
  border-bottom: 1px solid whitesmoke;
  padding-right:1.5rem;
  padding-left:1.5rem;
`;


const StyledLogo = styled.span`
  cursor: pointer;
  padding: 10px;
  font-weight: bold;
  font-size: 40px;
  background-image: linear-gradient(135deg, #e570e7 0%, #79f1fc 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
`;
