import axios from 'axios';
import { Logout, RedirectLogin} from 'src/hooks/logout';

const auctionAPI = axios.create({
  baseURL: process.env.REACT_APP_AUCTION_SERVER_BASE_URL,
  headers: {
    'Content-Type': "application/json;charset=utf-8"
  },
});

auctionAPI.defaults.timeout = 3000;

const bidAPI = axios.create({
  baseURL: process.env.REACT_APP_BID_SERVER_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8"
  }
});

bidAPI.defaults.timeout = 3000;

const userAPI = axios.create({
  baseURL: process.env.REACT_APP_USER_SERVER_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8"
  }
});

userAPI.defaults.timeout = 3000;

const noticeAPI = axios.create({
  baseURL: process.env.REACT_APP_NOTICE_SERVER_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8"
  }
});

noticeAPI.defaults.timeout = 3000;

// 공통 request 인터셉터
function addRequestIntercepter(axiosApi : any){
  console.log("해더 붙지롱")
  axiosApi.interceptors.request.use(
    (config:any) => {
        // 로컬 스토리지에서 Access Token 가져오기, 없다면 Undefined
        let accessToken = localStorage.getItem("access_token");
        console.log("accessToken >> ", accessToken)
        
        // Authorization 헤더에 토큰 추가 및 credential 설정
        if (accessToken) config.headers["authorization"] = "Bearer " + accessToken;
        config.withCredentials = true;
        return config;
    }
  );
}

const refreshTokenAPI = axios.create({
  baseURL: process.env.REACT_APP_USER_SERVER_BASE_URL
});

const getRefreshToken = async () =>{
  const refresh_token = localStorage.getItem("refresh_token");
  return await refreshTokenAPI.get('/tokens/refresh',
        {
          headers: {
            Authorization: "Bearer " + refresh_token
          }
        }
  )
}

// 공통 response 인터셉터
function addResponseIntercepter(axiosApi : any){
  axiosApi.interceptors.response.use(function (response : any) {
    // 2xx 범위에 있는 상태 코드는 이 함수를 트리거 합니다.
    // 응답 데이터가 있는 작업 수행
    return response;
  }, function (error : any) {
    // 2xx 외의 범위에 있는 상태 코드는 이 함수를 트리거 합니다.
    // 응답 오류가 있는 작업 수행
    
    const res = error.response;
    console.log(res);
    if (res && res.data){
      const message = res.data.message;
      console.log("axios response intercepter : " + message);
      
      // api gateway에서 인가가 막힌 경우 처리
      if(message === "만료된 토큰입니다."){
      // access token 유효기간이 다 된 경우
      // refresh token으로 토큰을 다시 받아온다.
        return getRefreshToken().then(({data})=>{
          alert("토큰 재발급 성공!");

          const access_token = data.access_token;
          const refresh_token = data.refresh_token;
          
          localStorage.setItem("access_token", access_token);
          localStorage.setItem("refresh_token", refresh_token);
          
          // refresh token이 유효한 경우 토큰 재발급
          return Promise.resolve("토큰 재발급 받았음. 다시 시도해 주세요");
        }).catch((err)=>{
          alert("refresh failed");
          Logout();
          // refresh token이 유효하지 않은 경우 로그아웃
          return Promise.resolve("재발급 실패");
        })
      }else if(message === "유효하지 않은 토큰입니다."
      || message === "지원하지 않는 토큰입니다."
      || message === "토큰의 클레임이 비어있습니다"){
        console.log("이상한 토큰 일 때!");
        Logout();
        // access token이 변조 된 경우
        return Promise.resolve("이상한 토큰 쓰지 마세요 ㅡㅡ.");
      }else if(message === "로그인이 필요한 서비스입니다."){
        // 토큰이 비어 있는 경우
        alert(message);
        RedirectLogin();
        return Promise.reject(new Error("로그인하라고 아 ㅋㅋ"));
      }
    }

    // 그 외의 경우는 server error return
    return Promise.reject(error);
  });
}

addRequestIntercepter(auctionAPI);
addRequestIntercepter(bidAPI);
addRequestIntercepter(userAPI);
addRequestIntercepter(noticeAPI);

addResponseIntercepter(auctionAPI);
addResponseIntercepter(bidAPI);
addResponseIntercepter(userAPI);
addResponseIntercepter(noticeAPI);

export {auctionAPI, bidAPI, userAPI, noticeAPI};