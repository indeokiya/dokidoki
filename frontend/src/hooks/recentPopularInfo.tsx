import { useQuery } from "react-query";
import { bidAPI } from "../api/axios"

const fetchRecentPopularInfo = () => {
  const axios = bidAPI;
  return axios.get(`analyze/realtime-interest`)
   .then(res => {
    console.log("res >> ", res);
    return res.data;
   })
   .catch(err => console.error(err));

}

export const useRecentPopularInfo = () => {
  
  return useQuery(["recentPopularInfo"], fetchRecentPopularInfo, {
    staleTime: 30*1000,
    cacheTime: 3*30*1000,
    retry: 0,
    select,
    refetchOnMount:true,
    refetchOnWindowFocus:false,
    refetchInterval: 30*1000,  
  })

}

// select로 fetch 해온 데이터 가공
const select = (data: any) => {
  if (!data?.bid?.length) {  // === 0 or undefined
    data.bid = [];
    console.log("dummyData added to bid!")
  }
  if (!data?.click?.length) {  // === 0 or undefined
    data.click = [];
    console.log("dummyData added to click!")
  }
  return data;
}

const onError = (err: any) => {
  // 에러일 시 더미데이터 반환 (개발용)
  // err.alterData = alterData;
  return err;
}
