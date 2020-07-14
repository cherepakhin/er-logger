package ru.domru.logger.kafka.receiver.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.domru.logger.kafka.receiver.controller.ITokenDecoder;
import ru.domru.logger.kafka.receiver.controller.TokenDecoderFullImpl;
import ru.domru.logger.kafka.receiver.controller.TokenDecoderSimpleImpl;
import ru.domru.logger.kafka.receiver.service.EventDeviceProducer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EventDeviceConfig {
    @Value("${kafka.host}")
    private String kafkaHost;

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Value("${usesimpledecoder}")
    private int useSimpleDecoder;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        return props;
    }

    @Bean
    public ProducerFactory<String, Map<String, Object>> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Map<String, Object>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public EventDeviceProducer eventDeviceService(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        return new EventDeviceProducer(kafkaTemplate, kafkaTopic);
    }

    @Bean
    ITokenDecoder tokeDecoder() {
        if (useSimpleDecoder != 0) {
            return new TokenDecoderSimpleImpl();
        } else {
            return new TokenDecoderFullImpl();
        }
    }
}
