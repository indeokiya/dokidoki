package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    ProfileImage findProfileImageByMemberId(Long member_id);
}
