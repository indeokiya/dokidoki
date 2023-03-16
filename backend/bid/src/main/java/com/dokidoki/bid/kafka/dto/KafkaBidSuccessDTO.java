package com.dokidoki.bid.kafka.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaBidSuccessDTO {

    private long memberId;
    private String name;
    private String email;
}
