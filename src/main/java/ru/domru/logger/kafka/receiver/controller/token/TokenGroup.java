package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenGroup {
    private Long id;
    @JsonProperty("extid")
    private String extId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }
}
