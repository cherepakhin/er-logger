package ru.domru.logger.kafka.receiver.controller.token;

public class IllegalArgumentFormatException extends IllegalArgumentException {
    private final String argumentType;
    private final String argumentValue;

    public IllegalArgumentFormatException(String argumentType, String argumentValue) {
        super();

        this.argumentType = argumentType;
        this.argumentValue = argumentValue;
    }
}
