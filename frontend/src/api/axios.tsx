import axios from 'axios';

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

// 공통 인터셉터
function addRequestIntercepter(axiosApi : any){
  axiosApi.interceptors.request.use(
    (config:any) => {
        // 로컬 스토리지에서 Access Token 가져오기, 없다면 Undefined
        let accessToken = localStorage.getItem("access_token");

        // Authorization 헤더에 토큰 추가 및 credential 설정
        if (accessToken) {
            config.headers["Authorization"] = "Bearer " + accessToken;
            config.withCredentials = true;
        }
        return config;
    }
  );
}

addRequestIntercepter(auctionAPI);
addRequestIntercepter(bidAPI);
addRequestIntercepter(userAPI);
addRequestIntercepter(noticeAPI);

export {auctionAPI, bidAPI, userAPI, noticeAPI};