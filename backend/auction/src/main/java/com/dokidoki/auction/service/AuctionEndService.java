package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.LeaderboardEntity;
import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.LeaderboardRepository;
import com.dokidoki.auction.dto.response.DetailAuctionEndInterface;
import com.dokidoki.auction.dto.response.DetailAuctionEndResponse;
import com.dokidoki.auction.dto.response.CommentResponse;
import com.dokidoki.auction.dto.response.LeaderboardHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionEndService {
    private final AuctionEndRepository auctionEndRepository;
    private final ImageService imageService;
    private final CommentService commentService;
    private final LeaderboardRepository leaderboardRepository;

    public DetailAuctionEndResponse readAuctionEnds(Long auction_id) {
        // 완료된 경매 정보
        List<DetailAuctionEndInterface> detailAuctionEndInterfaces = auctionEndRepository.findDetailAuctionEndEntitiesById(auction_id);

        // 존재하지 않는다면 null 반환
        if (detailAuctionEndInterfaces.isEmpty())
            return null;

        // 경매 제품 사진 URL 구하기
        List<String> auctionImageUrls = imageService.readAuctionImages(auction_id).getImage_urls();

        // 댓글 구하기
        List<CommentResponse> commentResponses = commentService.readComment(auction_id);

        // 입찰 내역 구하기
        List<LeaderboardEntity> leaderboardEntities = leaderboardRepository.findLeaderboardEntitiesByAuctionIdOrderByBidTime(auction_id);
        List<LeaderboardHistoryResponse> leaderboardHistoryResponses = new ArrayList<>();
        for (LeaderboardEntity leaderboard : leaderboardEntities)
            leaderboardHistoryResponses.add(new LeaderboardHistoryResponse(leaderboard));

        return new DetailAuctionEndResponse(
                detailAuctionEndInterfaces.get(0),
                auctionImageUrls,
                commentResponses,
                leaderboardHistoryResponses
        );
    }
}
