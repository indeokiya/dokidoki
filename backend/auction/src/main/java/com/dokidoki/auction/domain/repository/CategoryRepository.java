package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
