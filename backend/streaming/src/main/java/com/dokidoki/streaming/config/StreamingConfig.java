package com.dokidoki.streaming.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class StreamingConfig {

    @Value("${spring.application.id}")
    private String applicationId;

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;

    @Value("${spring.kafka.num-stream-threads}")
    private String numStreamThreads;

    @Value("${spring.kafka.commit-interval-ms}")
    private String commitInterval;

    @Bean
    @Qualifier("KafkaStreamsConfigProps")
    public StreamsBuilderFactoryBean kStreamsConfigProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, numStreamThreads);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, commitInterval);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(props));
    }

}
