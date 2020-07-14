package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TokenPrincipal {

    private Long id;
    private TokenType type;
    @JsonProperty("device_group")
    private DeviceGroup deviceGroup = new DeviceGroup();
    @JsonProperty("extid")
    private String extId;
    private TokenSubscriber subscriber;
    private TokenPlatform platform;
    private TokenGroup group;
    private Object attrs;
    private List<TokenGroup> groups;
    private TokenOperator operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public DeviceGroup getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public TokenSubscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(TokenSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    public TokenPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(TokenPlatform platform) {
        this.platform = platform;
    }

    public TokenGroup getGroup() {
        return group;
    }

    public void setGroup(TokenGroup group) {
        this.group = group;
    }

    public Object getAttrs() {
        return attrs;
    }

    public void setAttrs(Object attrs) {
        this.attrs = attrs;
    }

    public List<TokenGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<TokenGroup> groups) {
        this.groups = groups;
    }

    public TokenOperator getOperator() {
        return operator;
    }

    public void setOperator(TokenOperator operator) {
        this.operator = operator;
    }
}
