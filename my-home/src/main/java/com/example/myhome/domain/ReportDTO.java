package com.example.myhome.domain;

import java.util.HashMap;

public class ReportDTO {
    private HashMap<String, HashMap<String, Integer>> deviceAlarms = new HashMap<>();

    public ReportDTO() {
    }

    public HashMap<String, HashMap<String, Integer>> getDeviceAlarms() {
        return deviceAlarms;
    }

    public void setDeviceAlarms(HashMap<String, HashMap<String, Integer>> deviceAlarms) {
        this.deviceAlarms = deviceAlarms;
    }

    public ReportDTO(HashMap<String, HashMap<String, Integer>> deviceAlarms) {
        this.deviceAlarms = deviceAlarms;
    }
}
