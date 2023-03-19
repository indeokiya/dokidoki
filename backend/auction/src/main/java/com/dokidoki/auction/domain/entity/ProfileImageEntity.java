package com.dokidoki.auction.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "profile_image")
@Table(name = "profile_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Column(name = "image_url")
    private String imageUrl;

    public static ProfileImageEntity createProfileImage(MemberEntity memberEntity, String imageUrl) {
        ProfileImageEntity profileImageEntity = new ProfileImageEntity();
        profileImageEntity.memberEntity = memberEntity;
        profileImageEntity.imageUrl = imageUrl;
        return profileImageEntity;
    }

    public void updateProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
