package ru.domru.logger.kafka.receiver.controller.token;

public class ApiBadCredentialsException extends RuntimeException {

    private final ApiErrorType errorType;

    public ApiBadCredentialsException(ApiErrorType errorType) {
        this.errorType = errorType;
    }

    public ApiBadCredentialsException(ApiErrorType errorType, Exception ex) {
        super(ex);
        this.errorType = errorType;
    }

    public ApiErrorType getErrorType() {
        return errorType;
    }
}
