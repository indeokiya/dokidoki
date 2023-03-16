package com.dokidoki.bid.kafka.service;

import com.dokidoki.bid.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.bid.kafka.dto.KafkaAuctionRegisterDTO;
import com.dokidoki.bid.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.bid.kafka.dto.KafkaBidDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaBidProducer {

    @Value(value = "${spring.kafka.auctionEndConfig.topic}")
    private String auctionEndTopic;

    @Value(value = "${spring.kafka.BidConfig.topic}")
    private String BidTopic;

    private final KafkaTemplate<String, KafkaAuctionEndDTO> auctionEndKafkaTemplate;
    private final KafkaTemplate<String, KafkaBidDTO> BidKafkaTemplate;
    @Autowired
    public KafkaBidProducer(
            KafkaTemplate<String, KafkaAuctionEndDTO> auctionEndKafkaTemplate,
            KafkaTemplate<String, KafkaBidDTO> BidKafkaTemplate) {
        this.auctionEndKafkaTemplate = auctionEndKafkaTemplate;
        this.BidKafkaTemplate = BidKafkaTemplate;
    }

    public void sendAuctionEnd(KafkaAuctionEndDTO auction) {
        ListenableFuture<SendResult<String, KafkaAuctionEndDTO>> future = auctionEndKafkaTemplate.send(auctionEndTopic, auction);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, KafkaAuctionEndDTO> result) {
                System.out.println("auction ended: [" + auction + "] with partition = [" + result.getRecordMetadata().partition() + "] offset=[" + result.getRecordMetadata().offset() +"]");
//                log.debug("auction created: [{}] with partition = [{}] offset=[{}]", auction, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
//                log.debug("Unable to send message: [{}] due to : {}", auction, ex.getMessage());
                System.out.println("Unable to send message: [" + auction + "] due to : " + ex.getMessage());
            }

        });
    }

    public void sendBid(KafkaBidDTO bid) {
        ListenableFuture<SendResult<String, KafkaBidDTO>> future = BidKafkaTemplate.send(BidTopic, bid);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, KafkaBidDTO> result) {
                System.out.println("bid : [" + bid + "] with partition = [" + result.getRecordMetadata().partition() + "] offset=[" + result.getRecordMetadata().offset() +"]");
//                log.debug("auction created: [{}] with partition = [{}] offset=[{}]", auction, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
//                log.debug("Unable to send message: [{}] due to : {}
                System.out.println("Unable to send message: [" + bid + "] due to : " + ex.getMessage());
            }

        });
    }
}
