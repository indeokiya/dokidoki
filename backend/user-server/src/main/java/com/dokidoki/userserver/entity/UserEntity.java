package com.dokidoki.userserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
