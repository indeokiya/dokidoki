package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.db.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    <T> Optional<T> findById(long id, Class<T> type);

}
