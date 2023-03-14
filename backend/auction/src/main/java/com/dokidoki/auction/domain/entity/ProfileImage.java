package com.dokidoki.auction.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "image_url")
    private String imageUrl;

    public static ProfileImage createProfileImage(Member member, String imageUrl) {
        ProfileImage profileImage = new ProfileImage();
        profileImage.member = member;
        profileImage.imageUrl = imageUrl;
        return profileImage;
    }
}
