package com.dokidoki.userserver.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileImageRequest {
    private MultipartFile file;
}
