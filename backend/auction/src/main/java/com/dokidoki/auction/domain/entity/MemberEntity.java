package com.dokidoki.auction.domain.entity;

import com.dokidoki.auction.domain.enumtype.ProviderType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemberEntity extends BaseEntity{
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

    @Column(name = "end_time_of_suspension")
    private LocalDateTime EndTimeOfSuspension;
}
