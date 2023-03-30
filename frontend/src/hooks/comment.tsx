import { useQuery } from "react-query";

import { auctionAPI } from "../api/axios";

// useQuery 및 useMutation에서 사용하는 Key
export const commentKey = {
  readComments: (auction_id: string) => ["readComments", auction_id] as const,
}

// 댓글 조회
export const useReadCommentsQuery = (auction_id: string) => useQuery({
  queryKey: commentKey.readComments(auction_id),
  queryFn: () => auctionAPI
    .get(`${auction_id}/comments`)
    .then(({ data }) => data.data),
  enabled: false
})