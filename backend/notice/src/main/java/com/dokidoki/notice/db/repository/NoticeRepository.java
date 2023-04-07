package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.api.response.NoticeResp;

import java.util.Map;

public interface NoticeRepository {

    public void save(Long memberId, NoticeResp resp);

    public Map<Long, NoticeResp> getAll(Long memberId);

    public void deleteAll(Long memberId);

    public void updateRead(Long memberId, Long noticeId, boolean isRead);
}
