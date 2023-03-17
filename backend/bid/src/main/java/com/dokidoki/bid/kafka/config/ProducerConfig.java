//package com.dokidoki.bid.kafka.config;
//
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class ProducerConfig {
//
//    @Value(value = "${spring.kafka.bootstrap-servers[0]}")
//    private String bootstrapAddress;
//
////    @Bean
////    public ProducerFactory<Integer, String> producerFactory() {
////        return new DefaultKafkaProducerFactory<>(producerConfigs());
////    }
//
////    @Bean
////    public Map<String, Object> producerConfigs() {
////        Map<String, Object> props = new HashMap<>();
////        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
////        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
////        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
////        return props;
////    }
////
////    @Bean
////    public KafkaTemplate<Integer, String> kafkaTemplate() {
////        return new KafkaTemplate<>(producerFactory());
////    }
//
//    public ProducerFactory<String, RegisterDTO> RegisterProducerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, RegisterDTO> RegisterKafkaTemplate() {
//        return new KafkaTemplate<>(RegisterProducerFactory());
//    }
//}
