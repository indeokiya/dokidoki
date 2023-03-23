import Button from '@mui/material/Button';
import kakaoLoginImgSrc from '../assets/icon/login/kakao_login.png';
import googleLoginImgSrc from '../assets/icon/login/google_login.png';
import styled from 'styled-components';
import Typography from '@mui/material/Typography';
import loginBackgroundImgSrc from '../assets/icon/login/login_background.png';
import Paper from '@mui/material/Paper';

const LoginPage = () => {
  const OutterDiv = styled.div`
    border-radius: 10px;
    width: 20%;
    margin: 5% auto;

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
  const kakaoLoginHandler = () => {
    console.log('카카오 로그인 입장');
  };

  return (
    <>
      <OutterDiv>
        <InnerDivTop>
          <h3>WELECOME </h3>
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
            <img src={googleLoginImgSrc} alt="구글 로그인" width="255px" />
          </Button>
        </InnerDivBottom>
      </OutterDiv>
    </>
  );
};

export default LoginPage;

// /oauth2/login/{provider}
