package com.dokidoki.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 제품 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "category_id" , insertable = false, updatable = false)
    private Long categoryId;

    private String name;        // 제품명

    @Column(name = "img_url")
    private String imgUrl;      // 이미지 url

    @Column(name = "sale_cnt")
    private Integer saleCnt;        // 판매 빈도

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<AuctionIngEntity> auctionIngEntities = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<AuctionEndEntity> auctionEndEntities = new ArrayList<>();
}
