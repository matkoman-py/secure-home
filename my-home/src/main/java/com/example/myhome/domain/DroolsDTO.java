package com.example.myhome.domain;

public class DroolsDTO {
    private String deviceType;
    private Integer value;
    private boolean alarm;

    public DroolsDTO() {
    }

    public DroolsDTO(String deviceType, Integer value, boolean isAlarm) {
        this.deviceType = deviceType;
        this.value = value;
        this.alarm = isAlarm;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
}
