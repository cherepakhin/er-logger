package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceGroup {
    private Long id;
    @JsonProperty("platform_id")
    private Integer platformId;
    @JsonProperty("content_preset_group_id")
    private Integer contentPresetGroupId;
    private String criteria;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getContentPresetGroupId() {
        return contentPresetGroupId;
    }

    public void setContentPresetGroupId(Integer contentPresetGroupId) {
        this.contentPresetGroupId = contentPresetGroupId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
