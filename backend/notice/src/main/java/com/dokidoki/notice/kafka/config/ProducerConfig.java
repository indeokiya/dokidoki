package com.dokidoki.notice.kafka.config;

import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers[0]}")
    private String bootstrapAddress;

//    @Bean
//    public ProducerFactory<Integer, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }

//    @Bean
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
//        return props;
//    }
//
//    @Bean
//    public KafkaTemplate<Integer, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }

    public ProducerFactory<String, KafkaAuctionEndDTO> AuctionEndProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS,
                "auction-end:com.dokidoki.bid.kafka.dto.KafkaAuctionEndDTO");
        return new DefaultKafkaProducerFactory<>(props);
    }

    public ProducerFactory<String, KafkaBidDTO> BidProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS,
                "bid:com.dokidoki.bid.kafka.dto.KafkaBidDTO");
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, KafkaAuctionEndDTO> AuctionEndKafkaTemplate() {
        return new KafkaTemplate<>(AuctionEndProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, KafkaBidDTO> BidKafkaTemplate() {
        return new KafkaTemplate<>(BidProducerFactory());
    }
}
