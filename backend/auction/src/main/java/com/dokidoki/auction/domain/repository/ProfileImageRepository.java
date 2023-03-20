package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long> {
    Optional<ProfileImageEntity> findProfileImageByMemberEntityId(Long member_id);
}
