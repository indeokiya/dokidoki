package com.dokidoki.bid.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Arrays;

@Configuration
public class TopicConfig {

//    @Bean
//    public NewTopic topic_register() {
//        return TopicBuilder.name("register")
//                .partitions(1)
//                .replicas(1)
////                .compact() // retains latest value for each key; removes older value
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic2() {
//        return TopicBuilder.name("thing2")
//                .partitions(10)
//                .replicas(3)
//                .config(org.apache.kafka.common.config.TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic3() {
//        return TopicBuilder.name("thing3")
//                .assignReplicas(0, Arrays.asList(0, 1))
//                .assignReplicas(1, Arrays.asList(1, 2))
//                .assignReplicas(2, Arrays.asList(2, 0))
//                .config(org.apache.kafka.common.config.TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
//                .build();
//    }
//
//    @Bean
//    public KafkaAdmin.NewTopics topics456() {
//        return new KafkaAdmin.NewTopics(
//                TopicBuilder.name("defaultBoth")
//                        .build(),
//                TopicBuilder.name("defaultPart")
//                        .replicas(1)
//                        .build(),
//                TopicBuilder.name("defaultRepl")
//                        .partitions(3)
//                        .build());
//    }
}
