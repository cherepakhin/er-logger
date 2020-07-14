package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public enum ApiErrorType implements SearchableEnum {

    @JsonProperty("unknown-error")
    UNKNOWN(1),

    @JsonProperty("handler-not-found-error")
    HANDLER_NOT_FOUND(2),

    @JsonProperty("bind-error")
    BIND(3),

    @JsonProperty("invalid-data-access-api-usage-error")
    INVALID_DATA_ACCESS_API_USAGE(4),

    @JsonProperty("servlet-request-binding-error")
    SERVLET_REQUEST_BINDING(5),

    @JsonProperty("missing-servlet-request-part-error")
    MISSING_SERVLET_REQUEST_PART(6),

    @JsonProperty("missing-path-variable-error")
    MISSING_PATH_VARIABLE(7),

    @JsonProperty("message-not-writable-error")
    MESSAGE_NOT_WRITABLE(8),

    @JsonProperty("async-request-timeout-error")
    ASYNC_REQUEST_TIMEOUT(9),

    @JsonProperty("message-not-readable-error")
    MESSAGE_NOT_READABLE(10),

    @JsonProperty("method-argument-not-valid-error")
    METHOD_ARGUMENT_NOT_VALID(11),

    @JsonProperty("missing-servlet-request-parameter-error")
    MISSING_SERVLET_REQUEST_PARAMETER(12),

    @JsonProperty("type-mismatch-error")
    TYPE_MISMATCH(13),

    @JsonProperty("request-method-not-supported-error")
    REQUEST_METHOD_NOT_SUPPORTED(14),

    @JsonProperty("media-type-not-supported-error")
    MEDIA_TYPE_NOT_SUPPORTED(15),

    @JsonProperty("access-denied-error")
    ACCESS_DENIED(16),

    @JsonProperty("entity-not-found-error")
    ENTITY_NOT_FOUND(17),

    @JsonProperty("constraint-violation-error")
    CONSTRAINT_VIOLATION(18),

    @JsonProperty("method-argument-type-mismatch-error")
    METHOD_ARGUMENT_TYPE_MISMATCH(19),

    @JsonProperty("data-integrity-violation-error")
    DATA_INTEGRITY_VIOLATION(20),

    @JsonProperty("data-access-error")
    DATA_ACCESS(21),

    @JsonProperty("field-argument-not-valid-error")
    FIELD_ARGUMENT_NOT_VALID(22),

    @JsonProperty("global-argument-not-valid-error")
    GLOBAL_ARGUMENT_NOT_VALID(23),

    @JsonProperty("null-pointer-error")
    NULL_POINTER(24),

    @JsonProperty("illegal-argument-error")
    ILLEGAL_ARGUMENT(25),

    @JsonProperty("illegal-state-error")
    ILLEGAL_STATE(26),

    @JsonProperty("item-not-found")
    ITEM_NOT_FOUND(27),

    @JsonProperty("request-to-api-error")
    REQUEST_TO_API_ERROR(28),

    @JsonProperty("recommendation-error")
    RECOMMENDATION(29),

    @JsonProperty("token-expired")
    TOKEN_EXPIRED(30),

    @JsonProperty("token-required")
    TOKEN_REQUIRED(31),

    @JsonProperty("not-valid-token")
    TOKEN_NOT_VALID(32),

    @JsonProperty("billing-api-error")
    BILLING(33),

    @JsonProperty("internal-billing-api-error")
    INTERNAL_BILLING(34),

    @JsonProperty("promocode-api-error")
    PROMOCODE(35);

    private final long value;
    private static EnumHelper<ApiErrorType> helper = new EnumHelper<>(ApiErrorType.class);

    ApiErrorType(long value) {
        this.value = value;
    }

    public static ApiErrorType fromKey(Long key) {
        return Optional.ofNullable(helper.fromKeyValue(key)).orElse(ApiErrorType.UNKNOWN);
    }

    @Override
    public long getKey() {
        return value;
    }
}
