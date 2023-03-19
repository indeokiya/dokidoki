package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionImageEntity;
import com.dokidoki.auction.domain.entity.CommentEntity;
import com.dokidoki.auction.domain.entity.LeaderboardEntity;
import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.AuctionImageRepository;
import com.dokidoki.auction.domain.repository.CommentRepository;
import com.dokidoki.auction.domain.repository.LeaderboardRepository;
import com.dokidoki.auction.dto.response.AuctionEndInterface;
import com.dokidoki.auction.dto.response.AuctionEndResponse;
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

    public AuctionEndResponse readAuctionEnds(Long auction_id) {
        // 완료된 경매 정보
        AuctionEndInterface auctionEndInterface = auctionEndRepository.findAuctionEndEntitiesById(auction_id);

        // 경매 제품 사진 URL 구하기
        List<String> auctionImageUrls = imageService.readAuctionImages(auction_id).getImage_urls();

        // 댓글 구하기
        List<CommentResponse> commentResponses = commentService.readComment(auction_id);

        // 입찰 내역 구하기
        List<LeaderboardEntity> leaderboardEntities = leaderboardRepository.findLeaderboardEntitiesByAuctionIdOrderByBidTime(auction_id);
        List<LeaderboardHistoryResponse> leaderboardHistoryResponses = new ArrayList<>();
        for (LeaderboardEntity leaderboard : leaderboardEntities)
            leaderboardHistoryResponses.add(new LeaderboardHistoryResponse(leaderboard));

        return new AuctionEndResponse(
                auctionEndInterface,
                auctionImageUrls,
                commentResponses,
                leaderboardHistoryResponses
        );
    }
}
