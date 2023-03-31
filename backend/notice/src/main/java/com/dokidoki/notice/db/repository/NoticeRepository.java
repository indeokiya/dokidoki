package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.api.response.NoticeResp;

import java.util.Map;

public interface NoticeRepository {

    public void save(long memberId, NoticeResp resp);

    public Map<Long, NoticeResp> getAll(long memberId);

    public void deleteAll(long memberId);
}
