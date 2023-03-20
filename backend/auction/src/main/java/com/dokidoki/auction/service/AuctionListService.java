package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionImageEntity;
import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.AuctionImageRepository;
import com.dokidoki.auction.dto.response.AuctionImageResponse;
import com.dokidoki.auction.dto.response.SimpleAuctionEndInfo;
import com.dokidoki.auction.dto.response.SimpleAuctionEndInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionListService {
    private final AuctionEndRepository auctionEndRepository;
    private final ImageService imageService;

    public List<SimpleAuctionEndInfo> readSimpleAuctionEnds(Pageable pageable) {
        // 데이터 조회
        Page<List<SimpleAuctionEndInterface>> simpleAuctionEndInterfaces = auctionEndRepository
                .findSimpleAuctionEndEntitiesById(pageable);

        // DTO 생성
        List<SimpleAuctionEndInfo> simpleAuctionEndInfos = new ArrayList<>();
        for (SimpleAuctionEndInterface simpleAuctionEndInterface : simpleAuctionEndInterfaces) {
            // 경매 제품 사진 검색
            AuctionImageResponse auctionImageResponse = imageService
                    .readAuctionImages(simpleAuctionEndInterface.getAuction_id());

            // Response DTO 담기
            simpleAuctionEndInfos.add(
                    new SimpleAuctionEndInfo(
                            simpleAuctionEndInterface,
                            auctionImageResponse.getImage_urls()
                    );
            )
        }

        return simpleAuctionEndInfos;
    }
}
