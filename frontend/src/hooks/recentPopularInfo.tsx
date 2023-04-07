import { useQuery } from "react-query";
import { bidAPI } from "../api/axios"

const fetchRecentPopularInfo = () => {
  const axios = bidAPI;
  return axios.get(`analyze/realtime-interest`)
   .then(res => {
    // console.log("res >> ", res);
    return res.data;
   })
  //  .catch(err => console.error(err));

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
  if (data.bid.length === 0) {
    data.bid = dummyData;
    // console.log("dummyData added to bid!")
  }
  if (data.click.length === 0) {
    data.click = dummyData;
    // console.log("dummyData added to click!")
  }
  return data;
}

const onError = (err: any) => {
  // 에러일 시 더미데이터 반환 (개발용)
  // err.alterData = alterData;
  return err;
}

const dummyData: any[] = [
  {auction_id: 70, auction_title: "삼성전자 ❄무풍 에어컨❄", category_name: "BESPOKE", cnt: 999999, highest_price: 1000000, product_name: "무풍에어컨 클래식 (58.5 ㎡ + 18.7 ㎡) + BESPOKE 큐브™ Air (53 ㎡)" },
  {auction_id: 70, auction_title: "삼성전자 ❄무풍 에어컨❄", category_name: "BESPOKE", cnt: 888888, highest_price: 2000000, product_name: "무풍에어컨 클래식 (58.5 ㎡ + 18.7 ㎡) + BESPOKE 큐브™ Air (53 ㎡)" },
  {auction_id: 70, auction_title: "삼성전자 ❄무풍 에어컨❄", category_name: "BESPOKE", cnt: 777777, highest_price: 3000000, product_name: "무풍에어컨 클래식 (58.5 ㎡ + 18.7 ㎡) + BESPOKE 큐브™ Air (53 ㎡)" },
  {auction_id: 70, auction_title: "삼성전자 ❄무풍 에어컨❄", category_name: "BESPOKE", cnt: 666666, highest_price: 4000000, product_name: "무풍에어컨 클래식 (58.5 ㎡ + 18.7 ㎡) + BESPOKE 큐브™ Air (53 ㎡)" },
  {auction_id: 70, auction_title: "삼성전자 ❄무풍 에어컨❄", category_name: "BESPOKE", cnt: 555555, highest_price: 5000000, product_name: "무풍에어컨 클래식 (58.5 ㎡ + 18.7 ㎡) + BESPOKE 큐브™ Air (53 ㎡)" },
];
