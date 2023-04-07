package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.api.response.NoticeResp;
import com.dokidoki.notice.common.annotation.RTransactional;
import com.dokidoki.notice.common.codes.NoticeConstants;
import com.dokidoki.notice.common.error.exception.ErrorCode;
import com.dokidoki.notice.common.error.exception.InvalidValueException;
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
public class NoticeRepositoryImpl implements NoticeRepository {

    private final RedissonClient redisson;
    private final String keyPrefix = NoticeConstants.mapKey;

    private String getKey(Long memberId) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyPrefix).append(":").append(memberId);
        return sb.toString();
    }


    @Override
    @RTransactional
    public void save(Long memberId, NoticeResp resp) {
        RMap<Long, NoticeResp> noticeMap = redisson.getMap(getKey(memberId));
        long id = noticeMap.size();
        noticeMap.put(id, resp);
    }

    @Override
    public Map<Long, NoticeResp> getAll(Long memberId) {
        RMap<Long, NoticeResp> noticeMap = redisson.getMap(getKey(memberId));
        return noticeMap.readAllMap();
    }

    @Override
    @RTransactional
    public void deleteAll(Long memberId) {
        RMap<Long, NoticeResp> noticeMap = redisson.getMap(getKey(memberId));
        noticeMap.delete();
    }

    @Override
    @RTransactional
    public void updateRead(Long memberId, Long noticeId, boolean isRead) {
        RMap<Long, NoticeResp> noticeMap = redisson.getMap(getKey(memberId));
        NoticeResp noticeResp = noticeMap.get(noticeId);
        if (noticeResp == null) {
            throw new InvalidValueException("noticeId가 존재하지 않습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        noticeResp.setRead(isRead);
        noticeMap.put(noticeId, noticeResp);
    }
}
