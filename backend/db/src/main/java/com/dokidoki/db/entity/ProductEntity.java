package com.dokidoki.db.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Long id;            // 제품 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private CategoryEntity category;

    @Column(name = "category_id" , insertable = false, updatable = false)
    private Long categoryId;

    private String name;        // 제품명

    @Column(name = "img_url",columnDefinition = "MEDIUMTEXT")
    private String imgUrl;      // 이미지 url

    @Column(name = "sale_cnt")
    private Long saleCnt;        // 판매 빈도
}
