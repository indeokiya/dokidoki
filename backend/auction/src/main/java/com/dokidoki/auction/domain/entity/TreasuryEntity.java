package com.dokidoki.auction.domain.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Table(name = "treasury")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TreasuryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberEntity member;

    private Long money;

    @Builder
    public TreasuryEntity(Long id, MemberEntity member, Long money) {
        this.id = id;
        this.member = member;
        this.money = money;
    }
}
