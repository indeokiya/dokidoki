package com.dokidoki.auction.kafka.service;

import com.dokidoki.auction.kafka.dto.KafkaAuctionRegisterDTO;
import com.dokidoki.auction.kafka.dto.KafkaAuctionUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaAuctionProducer {
    @Value(value = "${spring.kafka.auctionRegisterConfig.topic}")
    private String auctionRegisterTopic;

    @Value(value = "${spring.kafka.auctionUpdateConfig.topic}")
    private String auctionUpdateTopic;
    private final KafkaTemplate<String, KafkaAuctionRegisterDTO> auctionRegisterKafkaTemplate;
    private final KafkaTemplate<String, KafkaAuctionUpdateDTO> auctionUpdateKafkaTemplate;
    @Autowired
    public KafkaAuctionProducer(
            KafkaTemplate<String, KafkaAuctionRegisterDTO> auctionRegisterKafkaTemplate,
            KafkaTemplate<String, KafkaAuctionUpdateDTO> auctionUpdateKafkaTemplate) {
        this.auctionRegisterKafkaTemplate = auctionRegisterKafkaTemplate;
        this.auctionUpdateKafkaTemplate = auctionUpdateKafkaTemplate;
    }

    public void sendAuctionRegister(KafkaAuctionRegisterDTO auction) {
        ListenableFuture<SendResult<String, KafkaAuctionRegisterDTO>> future = auctionRegisterKafkaTemplate.send(auctionRegisterTopic, auction);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, KafkaAuctionRegisterDTO> result) {
                System.out.println("auction created: [" + auction + "] with partition = [" + result.getRecordMetadata().partition() + "] offset=[" + result.getRecordMetadata().offset() +"]");
                log.debug("auction created: [{}] with partition = [{}] offset=[{}]", auction, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.debug("Unable to send message: [{}] due to : {}", auction, ex.getMessage());
                System.out.println("Unable to send message: [" + auction + "] due to : " + ex.getMessage());
            }

        });
    }

    public void sendAuctionUpdate(KafkaAuctionUpdateDTO update) {
        ListenableFuture<SendResult<String, KafkaAuctionUpdateDTO>> future = auctionUpdateKafkaTemplate.send(auctionUpdateTopic, update);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, KafkaAuctionUpdateDTO> result) {
                System.out.println("auction created: [" + update + "] with partition = [" + result.getRecordMetadata().partition() + "] offset=[" + result.getRecordMetadata().offset() +"]");
//                log.debug("auction created: [{}] with partition = [{}] offset=[{}]", auction, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
//                log.debug("Unable to send message: [{}] due to : {}
                System.out.println("Unable to send message: [" + update + "] due to : " + ex.getMessage());
            }

        });
    }
}
