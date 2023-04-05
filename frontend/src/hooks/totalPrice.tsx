import { useQuery } from "react-query";
import { auctionAPI } from "src/api/axios";


export const totalPriceKey = {
  readTotalExpenses: () => ["readTotalExpenses"] as const,
  readTotalProfits: () => ["readTotalProfits"] as const
}

export const useTotalExpensesQuery = () => useQuery({
  queryKey: totalPriceKey.readTotalExpenses(),
  queryFn: () => {
    auctionAPI
      .get(`/my-infos/total-expenses`)
      .then(({ data }) => {
        const totalExpenses: number = data.data
        console.log("넘어와라 좀!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", totalExpenses)
        return totalExpenses
      })
  },
  enabled: false
})

export const useTotalProfitsQuery = () => useQuery({
  queryKey: totalPriceKey.readTotalProfits(),
  queryFn: () => {
    auctionAPI
      .get(`/my-infos/total-profits`)
      .then(({ data }) => {
        const totalProfits: number = data.data
        console.log("넘어와라 좀!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", totalProfits)
        return totalProfits
      })
  },
  enabled: false
})