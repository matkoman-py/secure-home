package com.example.myhome.domain;

import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name="DEVICE")
public class Device {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "path")
    private String pathToFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estate_id", nullable = false)
    private Estate estate;

    public Device(Long id, DeviceType type, String ipAddress, String pathToFile, Estate estate) {
        this.id = id;
        this.type = type;
        this.ipAddress = ipAddress;
        this.pathToFile = pathToFile;
        this.estate = estate;
    }

    public Device() {
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

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public void writeMessage(String message){
        File csvOutputFile = new File(getPathToFile()+".csv");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile,true))) {
            List<String[]> dataLines = new ArrayList<>();
            dataLines.add(new String[]
                    { message, new Date().toString(), getIpAddress() });
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
