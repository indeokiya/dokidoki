import Button from '@mui/material/Button';
import kakaoLoginImgSrc from '../assets/icon/login/kakao_login.png';
import googleLoginImgSrc from '../assets/icon/login/google_login.png';
import styled from 'styled-components';
import Typography from '@mui/material/Typography';
import loginBackgroundImgSrc from '../assets/icon/login/login_background.png';
import { userAPI } from '../api/axios';

const LoginPage = () => {
  const kakaoLoginHandler = () => {
    userAPI.get('/oauth2/login/kakao').then(({ data }) => {
      window.location.replace(data.url);
    });
  };

  const googleLoginHandler = () => {
    userAPI.get('/oauth2/login/google').then(({ data }) => {
      window.location.replace(data.url);
    });
  };

  return (
    <>
      <OutterDiv>
        <InnerDivTop>
          <h3>WELCOME </h3>
          <Typography variant="h3">SSAFY</Typography>
        </InnerDivTop>
        <InnerDivBottom>
          <Button>
            <img
              src={kakaoLoginImgSrc}
              alt="카카오 로그인"
              width="250px"
              onClick={kakaoLoginHandler}
            />
          </Button>
          <br />
          <Button>
            <img
              src={googleLoginImgSrc}
              alt="구글 로그인"
              width="255px"
              onClick={googleLoginHandler}
            />
          </Button>
        </InnerDivBottom>
      </OutterDiv>
    </>
  );
};

export default LoginPage;

// /oauth2/login/{provider}

const OutterDiv = styled.div`
  border-radius: 10px;
  margin: 50px auto;
  width: 400px;
  color: white;
  background-image: url(${loginBackgroundImgSrc});
  border: 1px solid silver;
  // box-shadow: 1px 1px 10px grey;
`;

const InnerDivTop = styled.div`
  width: 100%;
  padding: 100px 18%;
  box-sizing: border-box;
  color: white;
`;

const InnerDivBottom = styled.div`
  border-radius: 0 0 10px 10px;
  text-align: center;
  background-color: white;
  width: 100%;
  box-sizing: border-box;
  padding: 40px 30px;
`;
