package com.dokidoki.notice.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface NoticeResp {

    NoticeType typeIs();

    void setIsRead(boolean bool);

}
