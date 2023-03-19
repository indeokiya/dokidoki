package com.dokidoki.auction.domain.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

}
