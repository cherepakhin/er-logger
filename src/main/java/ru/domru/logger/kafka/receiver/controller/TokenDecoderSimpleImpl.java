package ru.domru.logger.kafka.receiver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TokenDecoderSimpleImpl implements ITokenDecoder {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Map<String, Object> getInfoFromToken(String tokenString) throws IOException {
        Map<String, Object> token = deserilizeToken(tokenString);
        HashMap<String, Object> info = new HashMap<>();
        Map<String, Object> device = new HashMap<>();
        String data = (String) token.get("data");
        Map<String, Object> map = mapper.readValue(data, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> principal = (Map<String, Object>) map.get("principal");
        device.put("id", principal.get("id"));
        Map<String, Object> platform = (Map<String, Object>) principal.get("platform");
        device.put("platform", platform.get("extid"));
        info.put("device", device);

        Map<String, Object> subscriberMap = (Map<String, Object>) principal.get("subscriber");
        Map<String, Object> subscriber = new HashMap<>();
        subscriber.put("id", subscriberMap.get("extid"));
        info.put("subscriber", subscriber);
        return info;
    }

    Map<String, Object> deserilizeToken(String tokenString) throws IOException {
        byte[] tokenBytes = Base64.getDecoder().decode(tokenString.getBytes());
        return mapper.readValue(tokenBytes, new TypeReference<Map<String, Object>>() {
        });
    }

}
