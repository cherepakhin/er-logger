package ru.domru.logger.kafka.receiver.controller;

import java.util.Map;

public interface ITokenDecoder {
    Map<String, Object> getInfoFromToken(String tokenString) throws Exception;
}
