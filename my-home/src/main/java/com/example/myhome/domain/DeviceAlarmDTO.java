package com.example.myhome.domain;

import java.util.Date;

public class DeviceAlarmDTO {
    private Long id;
    private String message;
    private Date date;
    private DeviceType type;
    private String address;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public DeviceType getType() {
        return type;
    }
    public void setType(DeviceType type) {
        this.type = type;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public DeviceAlarmDTO(Long id, String message, Date date, DeviceType type, String address) {
        super();
        this.id = id;
        this.message = message;
        this.date = date;
        this.type = type;
        this.address = address;
    }
    public DeviceAlarmDTO() {
        super();
    }
}
