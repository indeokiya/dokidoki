import { useQueries, UseQueryResult } from "react-query";
import { bidAPI, auctionAPI } from "../api/axios"

const auctionAxios = auctionAPI;
const bidAxios = bidAPI;

type AuctionDetailResult = UseQueryResult<void, unknown>;
type BidDetailResult = UseQueryResult<void, unknown>;

export const useAuctionDetail = ({ id } : {id: string}) => {

    const results = useQueries([
      { // auction
        queryKey: ["auctionDetailAuction", id], 
        queryFn: () => {
          return auctionAxios // return으로 프로미스가 완료되는 것을 기다림
            .get(`in-progress/${id}`)
            // .then(res => console.log("fetched >> ", res))
            // .catch(err => console.error(err))
        },
        staleTime: 5*1000,
        retry: 0,
        refetchOnWindowFocus: false,
      },
      { // bid
        queryKey: ["auctionDetailBid", id],
        queryFn: () => {
          return bidAxios
            .get(`auctions/${id}/initial-info`)
            // .then(res => console.log("fetched >> ", res))
            // .catch(err => console.error(err))
        },
        staleTime: 5*1000,
        retry: 0,
        refetchOnWindowFocus: false,
      },  
  ])

  // console.log("result[0] >> ", results[0]);
  
  const isLoading = results[0].isLoading || results[1].isLoading;
  const isError = results[0].isError || results[1].isError;
  const error: any = results[1].isError ? results[1].error : results[0].error

  if (!isError && !isLoading) {
    const data = {...results[0].data!.data!.data, ...results[1].data!.data!.data};
    return {isLoading, isError, error, data}
  } else {
    const data = {};
    return {isLoading, isError, error, data};
  }
  
}

// select로 fetch 해온 데이터 가공
const select = (response: [AuctionDetailResult, BidDetailResult]) => {
  const data = {};

}

const onError = (err: any) => {
  // 에러일 시 더미데이터 반환 (개발용)
  err.alterData = alterData;
  return err;
}

const alterData = {

}