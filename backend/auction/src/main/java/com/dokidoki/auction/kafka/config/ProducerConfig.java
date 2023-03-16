package com.dokidoki.auction.kafka.config;

import com.dokidoki.auction.kafka.dto.KafkaAuctionRegisterDTO;
import com.dokidoki.auction.kafka.dto.KafkaAuctionUpdateDTO;
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

    public ProducerFactory<String, KafkaAuctionRegisterDTO> AuctionRegisterProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS,
                "auction-register:com.dokidoki.auction.kafka.dto.KafkaAuctionRegisterDTO");
        return new DefaultKafkaProducerFactory<>(props);
    }

    public ProducerFactory<String, KafkaAuctionUpdateDTO> AuctionUpdateProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS,
                "auction-update:com.dokidoki.auction.kafka.dto.KafkaAuctionUpdateDTO");
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, KafkaAuctionRegisterDTO> AuctionRegisterKafkaTemplate() {
        return new KafkaTemplate<>(AuctionRegisterProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, KafkaAuctionUpdateDTO> AuctionUpdateKafkaTemplate() {
        return new KafkaTemplate<>(AuctionUpdateProducerFactory());
    }
}
