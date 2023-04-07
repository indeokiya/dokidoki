package com.dokidoki.userserver.repository;

import com.dokidoki.userserver.entity.UserEntity;
import com.dokidoki.userserver.enumtype.ProviderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findBySubAndProviderType(String sub, ProviderType providerType);
    Page<UserEntity> findAll(Pageable pageable);
}
