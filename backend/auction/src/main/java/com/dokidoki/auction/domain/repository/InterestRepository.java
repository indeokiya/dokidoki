package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {

}
