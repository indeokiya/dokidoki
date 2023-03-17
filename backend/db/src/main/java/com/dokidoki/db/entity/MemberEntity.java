package com.dokidoki.db.entity;


import com.dokidoki.db.enumtype.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sub;
    private String name;
    private String picture;
    private String email;

    // 원투매니

    // 경매 ing buyer
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<AuctionIngEntity> auctionIngEntities = new ArrayList<>();

    // 경매 ed seller
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<AuctionEndEntity> auctionEndEntitiesSeller = new ArrayList<>();

    // 경매 ed buyer
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<AuctionEndEntity> auctionEndEntitiesBuyer = new ArrayList<>();

    // 찜꽁
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<InterestEntity> InterestEntities = new ArrayList<>();

    // 리더보드
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<LeaderBoardEntity> leaderBoardEntities = new ArrayList<>();

    // 댓글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<CommentEntity> CommentEntities = new ArrayList<>();

    // 알림
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NotificationEntity> notificationEntities = new ArrayList<>();

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
}
