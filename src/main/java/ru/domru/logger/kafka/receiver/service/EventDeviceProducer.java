package ru.domru.logger.kafka.receiver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Map;

public class EventDeviceProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventDeviceProducer.class);

    final String kafkaTopic;
    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;


    public EventDeviceProducer(KafkaTemplate<String, Map<String, Object>> kafkaTemplate, String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(List<Map<String, Object>> events) {
        for(Map<String, Object> event :events) {
            kafkaTemplate.send(kafkaTopic, event);
        }
    }
}
