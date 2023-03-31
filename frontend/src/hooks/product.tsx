import { useQuery } from "react-query";

import { auctionAPI } from "../api/axios";

// useQuery 및 useMutation에서 사용하는 Key
export const productKey = {
  readProducts: (keyword: string) => ["readProducts", keyword] as const,
}

// 댓글 조회
export const useReadProductsQuery = (keyword: string) => useQuery({
  queryKey: productKey.readProducts(keyword),
  queryFn: () => auctionAPI
    .get(`products?keyword=${keyword}`)
    .then(({ data }) => data.data),
  enabled: false
})