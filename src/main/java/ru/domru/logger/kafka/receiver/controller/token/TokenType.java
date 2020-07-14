package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TokenType implements SearchableEnum {
    @JsonProperty("device")
    DEVICE(1),
    @JsonProperty("service")
    SERVICE(2),
    @JsonProperty("subscriber")
    SUBSCRIBER(4);

    private final long value;

    private static EnumHelper<TokenType> helper = new EnumHelper<>(TokenType.class);

    TokenType(long value) {
        this.value = value;
    }

    public static TokenType fromValue(String text) {
        return helper.fromValue(text);
    }

    @Override
    public long getKey() {
        return value;
    }
}
