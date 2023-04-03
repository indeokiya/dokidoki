package com.dokidoki.auction.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Table(name = "treasury")
@Getter
@Builder
@AllArgsConstructor
@Entity
public class TreasuryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long money;
}
