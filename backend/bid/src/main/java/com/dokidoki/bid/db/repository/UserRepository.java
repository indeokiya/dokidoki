package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    <T> Optional<T> findById(long id, Class<T> type);

}
