package com.dokidoki.bid.db.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;            // 제품 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String name;        // 제품명

    @Column(name = "img_url")
    private String imgUrl;      // 이미지 url

    @Column(name = "sale_cnt")
    private int saleCnt;        // 판매 빈도

}
