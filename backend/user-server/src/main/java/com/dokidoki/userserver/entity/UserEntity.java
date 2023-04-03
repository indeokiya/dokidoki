package com.dokidoki.userserver.entity;

import com.dokidoki.userserver.enumtype.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sub;
    private String name;
    private String picture;
    private String email;
    private Long point;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
}
