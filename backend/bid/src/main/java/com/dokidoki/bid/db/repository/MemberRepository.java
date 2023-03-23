package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    <T> Optional<T> findById(long id, Class<T> type);

}
