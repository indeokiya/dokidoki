package com.dokidoki.auction.domain.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "treasury")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TreasuryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long money;

    @Builder
    public TreasuryEntity(Long id, Long money) {
        this.id = id;
        this.money = money;
    }
}
