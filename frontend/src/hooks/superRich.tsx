import { useQuery } from "react-query";
import { userAPI } from "src/api/axios";
import { RankData } from "src/datatype/datatype";

export const superRichKeys = {
  readSuperRich: () => ["readSuperRich"] as const
};

export const useSuperRich = () => useQuery({
  queryKey: superRichKeys.readSuperRich(),
  queryFn: () => userAPI
    .get(`/points/super-rich`, { params: { size: 5 } })
    .then(({ data }: { data: any[] }) => {
      const rankData: RankData[] = []
      data.forEach(elem => {
        rankData.push({
          label: elem.encryptName,
          value: elem.point
        })
      })
      return rankData
    })
    .catch(err => err),
  refetchOnWindowFocus: false
});