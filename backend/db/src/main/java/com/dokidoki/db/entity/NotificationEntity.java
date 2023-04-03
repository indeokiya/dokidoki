package com.dokidoki.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MemberEntity.class)
    @JoinColumn(name = "receiver_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private MemberEntity member;

    @Column(name = "receiver_id", insertable = false, updatable = false)
    private Long receiverId;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AuctionIngEntity.class)
    @JoinColumn(name = "auction_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private AuctionIngEntity auctionIng;

    @Column(name = "auction_id", insertable = false, updatable = false)
    private Long auctionId;


    private String message;

    @Column(name = "read_yn")
    private Character readYn;
}
