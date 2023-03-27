import { useQuery, useQueries, UseQueryResult } from "react-query";
import { bidAPI, auctionAPI } from "../api/axios"

const auctionAxios = auctionAPI;
const bidAxios = bidAPI;

type AuctionDetail = {
  data: any
}

type BidDetail = {
  data: any
}

type AuctionDetailResult = UseQueryResult<void, unknown>;
type BidDetailResult = UseQueryResult<void, unknown>;

// type UseAuctionDetailResult = [AuctionDetailResult, BidDetailResult];

// type AuctionDetailFn = ({ id }: strin) => UseQueryResult;

const axios = bidAPI;



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
        staleTime: 0,
        retry: 0,
      },
      { // bid
        queryKey: ["auctionDetailBid", id],
        queryFn: () => {
          return bidAxios
            .get(`auctions/${id}/initial-info`)
            // .then(res => console.log("fetched >> ", res))
            // .catch(err => console.error(err))
        },
        staleTime: 0,
        retry: 0,
      },  
  ])

  // console.log("result[0] >> ", results[0]);
  
  const isLoading = results[0].isLoading || results[1].isLoading;
  const isError = results[0].isError || results[1].isError;
  const error: any = results[0].isError ? results[0].error : results[1].error
  const data = results[0].data
  console.log("results[0].data >> ", results[0].data)
  if (!isLoading) {
    const data = {...results[0].data!.data!.data, ...results[1].data!.data!.data};
    // console.warn("재휘 >> ", results[0].data!.data!.data);
    // console.warn("혜진 >> ", results[1].data!.data!.data);
    return {isLoading, isError, error, data}
  }
    return {isLoading, isError, error, data};
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

// import { useQueries, UseQueryResult } from "react-query";
// import { bidAPI, auctionAPI } from "../api/axios"

// const auctionAxios = auctionAPI;
// const bidAxios = bidAPI;

// type AuctionDetail = {
//   data: any
// }

// type BidDetail = {
//   data: any
// }

// type AuctionDetailResult = UseQueryResult<void, unknown>;
// type BidDetailResult = UseQueryResult<void, unknown>;

// type UseAuctionDetailResult = [AuctionDetailResult, BidDetailResult];

// const useAuctionDetail = ({ id }: {id: any}): UseAuctionDetailResult => {
//   const results = useQueries([
//       { // auction
//         queryKey: ["auctionDetailAuction", id], 
//         queryFn: () => {
//           auctionAxios
//             .get(`in-progress/${id}`)
//             // .then(res => res.data)
//             // .catch(err => console.error(err))
//         },
//         staleTime: 0,
//       },
//       { // bid
//         queryKey: ["auctionDetailBid", id],
//         queryFn: () => {
//           bidAxios
//             .get(`${id}/initial-info`)
//             // .then(res => res.data)
//             // .catch(err => console.error(err))
//         },
//         staleTime: 0,
//       },  
//   ])

//   // console.log("result >> ", results);
//   // const data = results.map(result => result.data);
//   console.log("results >> ", results);
//   const data = results.map(result => result.data);

//   return results;
// }

// // select로 fetch 해온 데이터 가공
// const select = (response) => {

//   return response.data.body;
// }

// const onError = (err) => {
//   // 에러일 시 더미데이터 반환 (개발용)
//   err.alterData = alterData;
//   return err;
// }

// const alterData = {

// }

// export default useAuctionDetail;