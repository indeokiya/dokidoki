package com.dokidoki.auction.domain.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 제품 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    private String name;        // 제품명

    @Column(name = "img_url")
    private String imgUrl;      // 이미지 url

    @Column(name = "sale_cnt")
    private Long saleCnt;        // 판매 빈도

}
