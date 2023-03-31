package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.api.response.NoticeResp;
import com.dokidoki.notice.common.codes.NoticeConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class NoticeRepositoryImpl2 implements NoticeRepository {

    private final RedissonClient redisson;
    private final String keyPrefix = NoticeConstants.mapKey;

    private String getKey(long memberId) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyPrefix).append(":").append(memberId);
        return sb.toString();
    }

    @Override
    public void save(long memberId, NoticeResp resp) {
        RMap<Long, NoticeResp> noticeMap = redisson.getMap(getKey(memberId));
        long id = noticeMap.size();
        noticeMap.put(id, resp);
    }

    @Override
    public Map<Long, NoticeResp> getAll(long memberId) {
        RMap<Long, NoticeResp> noticeMap = redisson.getMap(getKey(memberId));
        return (Map<Long, NoticeResp>) noticeMap.values();
    }

    @Override
    public void deleteAll(long memberId) {
        RMap<Long, NoticeResp> noticeMap = redisson.getMap(getKey(memberId));
        noticeMap.delete();
    }
}
