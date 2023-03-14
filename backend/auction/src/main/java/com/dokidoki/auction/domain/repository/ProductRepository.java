package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_Id(Long category_id);
}
