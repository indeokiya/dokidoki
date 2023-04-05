package com.dokidoki.streaming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LogCounter {

    @Autowired
    @Qualifier("KafkaStreamsConfigProps")
    private StreamsBuilder builder;

    // final Serde<JsonNode> jsonSerde = Serdes.serdeFrom(new JsonSerializer(), new JsonDeserializer());

    @Bean
    public void process() {
        KStream<String, String> clicks = builder.stream("aggregate.click.log")
                .map(((key, value) -> {
                    try {
                        System.out.println("click | key >> " + key + ", value >> " + value + ", getId >> " + getAuctionIdByJson(String.valueOf(value)));
                        return KeyValue.pair(getAuctionId(String.valueOf(key)), "click");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }));

        KStream<String, String> bids = builder.stream("aggregate.bid.log")
            .map(((key, value) -> {
                try {
                    System.out.println("bid | key >> " + key + ", value >> " + value + ", getId >> " + getAuctionId(String.valueOf(key)));
                    return KeyValue.pair(getAuctionId(String.valueOf(key)), "bid");
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }));

        KGroupedStream<String, String> groupedClicks = clicks.groupByKey(Grouped.with(Serdes.String(), Serdes.String()));

        KGroupedStream<String, String> groupedBids = bids.groupByKey(Grouped.with(Serdes.String(), Serdes.String()));


        KTable<Windowed<String>, Long> clickTable = groupedClicks
                .windowedBy(TimeWindows.of(Duration.ofSeconds(30)))
                .count();

        KTable<Windowed<String>, Long> bidTable = groupedBids
                .windowedBy(TimeWindows.of(Duration.ofSeconds(30)))
                .count();

        KStream<String, String> clickResultStream = clickTable.toStream()
                .map(((windowedkey, value) -> {
//                    System.out.println("resultClickStream | windowedkey >> " + windowedkey + ", val >> " + value);
                    String resultKey = windowedkey.key();
                    long resultValue = value;
                    return KeyValue.pair(resultKey, "click" + " " + windowedkey + " " + resultValue);
                }));

        KStream<String, String> bidResultStream = bidTable.toStream()
                .map(((windowedkey, value) -> {
                    System.out.println("resultbidStream | windowedkey >> " + windowedkey + ", val >> " + value);
                    String resultKey = windowedkey.key();
                    long resultValue = value;
                    return KeyValue.pair(resultKey, "bid" + " " + windowedkey + " " + resultValue);
                }));

        clickResultStream.to("test-result", Produced.with(Serdes.String(), Serdes.String()));
        bidResultStream.to("test-result", Produced.with(Serdes.String(), Serdes.String()));
    }

    private static String getAuctionIdByJson(String value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(value);
        String uri = jsonNode.get("uri").asText();
        String[] uriArr = uri.split("/");
        return uriArr[2];
    }

    private static String getAuctionId(String value) throws JsonProcessingException {
        String[] uriArr = value.split("/");
        return uriArr[2];
    }
}
