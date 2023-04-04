import Button from '@mui/material/Button';
import kakaoLoginImgSrc from '../assets/login/kakao_login.png';
import googleLoginImgSrc from '../assets/login/google_login.png';
import styled, { keyframes } from 'styled-components';
import Typography from '@mui/material/Typography';
import { userAPI } from '../api/axios';
import Box from '@mui/material/Box';

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
    <Box
      sx={{
        padding: '10%',
        margin: '0 auto',
        height: '100vh',
      }}
    >
      <Box
        sx={{
          display: 'flex',
          width: '600px',
          margin: '0 auto',
          borderRadius: '10px',
          boxShadow: '1px 1px 15px #dddddd',
          overflow: 'hidden',
        }}
      >
        <LoginDiv>
          <Typography variant="subtitle2" mt={8} color="white">
            WELCOME TO
          </Typography>

          <Typography variant="h3" color="white">
            DOKIDOKI
          </Typography>

          <Typography variant="caption" color="white">
            Enjoy the site easily
            <br /> with social login
          </Typography>
        </LoginDiv>
        <LoginForm>
          <Typography variant="h6" mt={10} color="#a3aae1" textAlign={'center'} mb={3}>
            LogIn
          </Typography>
          <Button>
            <img
              src={googleLoginImgSrc}
              alt="구글 로그인"
              width="100%"
              onClick={googleLoginHandler}
            />
          </Button>
          <Button>
            <img
              src={kakaoLoginImgSrc}
              alt="카카오 로그인"
              width="100%"
              onClick={kakaoLoginHandler}
            />
          </Button>
        </LoginForm>
      </Box>
    </Box>
  );
};

export default LoginPage;

// /oauth2/login/{provider}

const LoginForm = styled.div`
  width: 300px;
  height: 400px;
  padding: 5%;
  box-sizing: border-box;
  background-color: white;
`;

const gradient = keyframes`
0% {
  background-position: 0% 50%;
}
50% {
  background-position: 100% 50%;
}
100% {
  background-position: 0% 50%;
}`;

const LoginDiv = styled.div`
  padding: 5%;
  width: 400px;
  height: 400px;
  box-sizing: border-box;
  background: linear-gradient(135deg, #e570e7 0%, #79f1fc 100%);
  background-size: 400% 400%;
  animation: ${gradient} 5s ease infinite;
`;

