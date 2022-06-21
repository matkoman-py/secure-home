package com.example.myhome.domain;

import javax.persistence.*;

public class DeviceDTO {


    private Long id;
    private DeviceType type;
    private String ipAddress;
    private String pathToFile;
    private String estate;

    public DeviceDTO(Long id, DeviceType type, String ipAddress, String pathToFile, String estate) {
        this.id = id;
        this.type = type;
        this.ipAddress = ipAddress;
        this.pathToFile = pathToFile;
        this.estate = estate;
    }

    public DeviceDTO(Device device) {
        this.id = device.getId();
        this.type = device.getType();
        this.ipAddress = device.getIpAddress();
        this.pathToFile = device.getPathToFile();
        this.estate = device.getEstate().getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public DeviceDTO() {
    }
}
