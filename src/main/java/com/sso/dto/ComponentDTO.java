package com.sso.dto;

public class ComponentDTO {
    private String uuid;
    private String name;
    private String code;
    private String icon;

    public ComponentDTO(){};
    public ComponentDTO(String name, String code, String icon) {
        this.name = name;
        this.code = code;
        this.icon = icon;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
