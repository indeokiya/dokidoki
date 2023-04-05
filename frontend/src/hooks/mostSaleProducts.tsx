import { useQuery } from "react-query";
import { auctionAPI } from "src/api/axios";
import { RankData } from "src/datatype/datatype";


export const mostSaleProductsKey = {
  readMostSaleProductsAllKey: () => ["readMostSaleProductsAll"] as const
};

export const useMostSaleProductsAllQuery = () => useQuery({
  queryKey: mostSaleProductsKey.readMostSaleProductsAllKey(),
  queryFn: () => auctionAPI
    .get(`/most-sale-products`)
    .then(({ data }: { data: any }) => {
      const products: any[] = data.data
      const rankData: RankData[] = []
      products.forEach(elem => {
        rankData.push({
          label: elem.product_name,
          value: elem.sale_cnt
        })
      })
      return rankData
    })
    .catch(err => err),
  refetchOnWindowFocus: false
});