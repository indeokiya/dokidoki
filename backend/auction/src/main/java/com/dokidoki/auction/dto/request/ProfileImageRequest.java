package com.dokidoki.auction.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileImageRequest {
    private Long member_id;
    private MultipartFile file;
}
