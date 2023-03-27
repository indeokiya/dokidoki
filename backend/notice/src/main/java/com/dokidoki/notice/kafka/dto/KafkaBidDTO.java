package com.dokidoki.notice.kafka.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaBidDTO {

    private long memberId;
    private String name;
    private String email;
}
