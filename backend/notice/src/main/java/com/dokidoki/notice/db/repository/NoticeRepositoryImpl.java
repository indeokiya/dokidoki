//package com.dokidoki.notice.db.repository;
//
//import com.dokidoki.notice.api.response.NoticeResp;
//import com.dokidoki.notice.common.annotation.RTransactional;
//import com.dokidoki.notice.common.codes.NoticeConstants;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RList;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Component
//@Slf4j
//public class NoticeRepositoryImpl implements NoticeRepository {
//
//    private final RedissonClient redisson;
//    private final String keyPrefix =  NoticeConstants.listKey;
//
//    private String getKey(long memberId) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(keyPrefix).append(":").append(memberId);
//        return sb.toString();
//    }
//    @RTransactional
//    @Override
//    public void save(long memberId, NoticeResp resp) {
//        RList<NoticeResp> noticeList = redisson.getList(getKey(memberId));
//        noticeList.add(resp);
//
//    }
//    @Override
//    public List<NoticeResp> getAll(long memberId) {
//        RList<NoticeResp> noticeList = redisson.getList(getKey(memberId));
//        return noticeList.readAll();
//    }
//
//    @RTransactional
//    @Override
//    public void deleteAll(long memberId) {
//        RList<NoticeResp> noticeList = redisson.getList(getKey(memberId));
//        noticeList.delete();
//    }
//
//
//
//}
