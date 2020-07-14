package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TokenSubscriber {
    private Long id;
    @JsonProperty("extid")
    private String extId;
    @JsonProperty("is_guest")
    private boolean isGuest;
    private String type;
    private List<TokenGroup> groups;

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

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TokenGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<TokenGroup> groups) {
        this.groups = groups;
    }
}
