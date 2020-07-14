package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenPlatform {
    private long id;
    @JsonProperty("extid")
    private String extId;
    private String title;
    private TokenOperator operator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TokenOperator getOperator() {
        return operator;
    }

    public void setOperator(TokenOperator operator) {
        this.operator = operator;
    }
}
