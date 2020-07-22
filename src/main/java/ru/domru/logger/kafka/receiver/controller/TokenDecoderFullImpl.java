package ru.domru.logger.kafka.receiver.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.domru.logger.kafka.receiver.controller.token.ApiBadCredentialsException;
import ru.domru.logger.kafka.receiver.controller.token.ApiErrorType;
import ru.domru.logger.kafka.receiver.controller.token.Token;

import java.util.HashMap;
import java.util.Map;

public class TokenDecoderFullImpl implements ITokenDecoder {
    ObjectMapper mapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
    );

    @Override
    public Map<String, Object> getInfoFromToken(String tokenString) {
        HashMap<String, Object> info = new HashMap<>();
        Token token = deserializeToken(tokenString);

        Map<String, Object> device = new HashMap<>();
        device.put("id", token.getPrincipal().getExtId());
        device.put("platform", token.getPrincipal().getPlatform().getExtId());
        info.put("device", device);

        Map<String, Object> subscriber = new HashMap<>();
        subscriber.put("id", token.getPrincipal().getSubscriber().getExtId());
        info.put("subscriber", subscriber);

        return info;
    }

    Token deserializeToken(String tokenString) {
        Token requestToken = new Token(
                tokenString,
                mapper
        );
        try {
            requestToken.deserialize();
            return requestToken;
        } catch (Exception ex) {
            throw new ApiBadCredentialsException(ApiErrorType.TOKEN_NOT_VALID, ex);
        }
    }

}
