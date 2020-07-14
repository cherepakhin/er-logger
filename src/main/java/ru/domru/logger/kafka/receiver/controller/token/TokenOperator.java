package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenOperator {
    private int id;
    @JsonProperty("extid")
    private String extId;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
