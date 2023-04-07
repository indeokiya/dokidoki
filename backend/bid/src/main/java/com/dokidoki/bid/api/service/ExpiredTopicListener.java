//package com.dokidoki.bid.api.service;
//
//import org.redisson.PubSubPatternMessageListener;
//import org.redisson.api.ExpiredObjectListener;
//import org.redisson.api.RBucket;
//import org.redisson.api.RPatternTopic;
//import org.redisson.api.RedissonClient;
//import org.redisson.api.listener.PatternMessageListener;
//import org.redisson.client.RedisPubSubListener;
//import org.redisson.client.protocol.pubsub.PubSubType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ExpiredTopicListener {
//
//    @Autowired
//    public void setTest(RedissonClient redisson) {
//        ExpiredObjectListener listener = new ExpiredObjectListener() {
//            @Override
//            public void onExpired(String name) {
//                System.out.println(name);
//            }
//        };
//
//        RBucket<Object> listen = redisson.getBucket("listen");
//        listen.addListener(listener);
//
//
//    }


//    @Autowired
//    public void setExpiredTopicListener(RedissonClient redisson) {
//        RPatternTopic patternTopic = redisson.getPatternTopic("__keyevent@*__:expired");
//        // topic에 리스너를 달아야하는데, 그 리스너에서 에러가 남
//
////        PatternMessageListener<String> listener = new PatternMessageListener<String>() {
////            @Override
////            public void onMessage(CharSequence pattern, CharSequence channel, String msg) {
////                System.out.println(msg);
////
////            }
////        };
//        PatternMessageListener pmListener = new PatternMessageListener() {
//            @Override
//            public void onMessage(CharSequence pattern, CharSequence channel, Object msg) {
//
//            }
//        };
//
//        RedisPubSubListener<String> listener = new RedisPubSubListener<String>() {
//            @Override
//            public boolean onStatus(PubSubType type, CharSequence channel) {
//                return false;
//            }
//
//            @Override
//            public void onPatternMessage(CharSequence pattern, CharSequence channel, String message) {
//
//            }
//
//            @Override
//            public void onMessage(CharSequence channel, String msg) {
//
//            }
//        };
//        // topic에 리스너를 다는데, 이 리스너는 PubSubPatternMessageListener 의 일부로 들어감
//        patternTopic.addListener(String.class, (pattern, channel, msg) -> {
//            System.out.println(pattern);
////            System.out.printf("pattern: %s, channel: %s, msg: %s", pattern, channel, msg);
//
//        });
//
//    }

//}
