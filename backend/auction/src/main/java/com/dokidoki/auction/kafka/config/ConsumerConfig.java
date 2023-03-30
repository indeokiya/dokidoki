package com.dokidoki.auction.kafka.config;

import com.dokidoki.auction.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.auction.kafka.dto.KafkaBidDTO;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers[0]}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.auctionEndConfig.topic}")
    private String auctionEndTopic;
    @Value(value = "${spring.kafka.bidConfig.topic}")
    private String bidTopic;

    public ConsumerFactory<String, KafkaAuctionEndDTO> auctionEndConsumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS,
                "auction-end:com.dokidoki.auction.kafka.dto.KafkaAuctionEndDTO");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), // key
                new JsonDeserializer<>(KafkaAuctionEndDTO.class)); // value
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaAuctionEndDTO> auctionEndKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(auctionEndConsumerFactory(auctionEndTopic + ".auctionGroup"));
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    public ConsumerFactory<String, KafkaBidDTO> bidConsumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS,
                "bid:com.dokidoki.auction.kafka.dto.KafkaBidDTO");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), // key
                new JsonDeserializer<>(KafkaBidDTO.class)); // value
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaBidDTO> bidKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(bidConsumerFactory(bidTopic + ".auctionGroup"));
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
}
