package com.dokidoki.db.entity;


import com.dokidoki.db.enumtype.ProviderType;
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
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sub;
    private String name;
    private String picture;
    private String email;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
}
