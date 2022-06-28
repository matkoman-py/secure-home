package com.example.myhome.service;

import com.example.myhome.domain.*;
import com.example.myhome.repository.DeviceAlarmRepository;
import com.example.myhome.repository.DeviceRepository;
import com.example.myhome.repository.MessageRepository;
import com.example.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DeviceAlarmRepository deviceAlarmRepository;

    public List<Device> getDevicesForUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        List<Device> devicesFromUserEstates = new ArrayList<>();

        for(Estate e : user.getEstates()){
            devicesFromUserEstates.addAll(e.getDevices());
        }

        return devicesFromUserEstates;
    }

    public List<DeviceDTO> getDevicesDtoForUser() {
        return getDevicesForUser()
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getMessagesFromUserDevices(String message, String dateAfterString, String dateBeforeString, String pathToFile) throws ParseException {
        Date dateAfter, dateBefore;
        if(dateAfterString.equals("")){
            dateAfter = new Date(Long.MIN_VALUE);
        }
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateAfter = dateFormat.parse(dateAfterString);
        }

        if(dateBeforeString.equals("")){
            dateBefore = new Date(Long.MAX_VALUE);
        }
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateBefore = dateFormat.parse(dateBeforeString);
        }

        List<Device> devices = getDevicesForUser();
        List<Message> messagesFromDevices = new ArrayList<>();
        devices = devices.stream().filter(dev -> dev.getPathToFile().startsWith(pathToFile)).collect(Collectors.toList());

        for(Device d : devices){
            messagesFromDevices.addAll(messageRepository.search(dateAfter, dateBefore, message, d.getPathToFile()));
        }

        return messagesFromDevices.stream().map(m -> {
            Device d = deviceRepository.findByPathToFile(m.getDevice()).get();
            return new MessageDTO(m.getId(), m.getDate(),
                    m.getMessage(), d.getType(), d.getEstate().getAddress());
        }).collect(Collectors.toList());
    }

    public List<DeviceAlarmDTO> getAllAlarmsForUser() {
        List<Device> devices = getDevicesForUser();
        List<DeviceAlarm> alarms = new ArrayList<>();

        for(Device device : devices){
            alarms.addAll(deviceAlarmRepository.findByDevice(device));
        }

        return alarms.stream()
                .map(alarm -> new DeviceAlarmDTO(alarm.getId(), alarm.getMessage(), alarm.getDate(),
                        alarm.getDevice().getType(), alarm.getDevice().getEstate().getAddress())

                ).collect(Collectors.toList());
    }

    public ReportDTO getReport(String dateAfterString, String dateBeforeString) throws ParseException {
        Date dateAfter, dateBefore;
        if(dateAfterString.equals("")){
            dateAfter = new Date(Long.MIN_VALUE);
        }
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateAfter = dateFormat.parse(dateAfterString);
        }

        if(dateBeforeString.equals("")){
            dateBefore = new Date(Long.MAX_VALUE);
        }
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateBefore = dateFormat.parse(dateBeforeString);
        }

        List<Device> devices = getDevicesForUser();

        ReportDTO report = new ReportDTO();


        for(Device device : devices){
            List<DeviceAlarm> deviceAlarms = new ArrayList<>();
            deviceAlarms.addAll(deviceAlarmRepository.findByDevice(device));
            deviceAlarms = deviceAlarms.stream().filter(alarm -> alarm.getDate().before(dateBefore) && alarm.getDate().after(dateAfter))
                    .collect(Collectors.toList());
            if(report.getDeviceAlarms().containsKey(device.getEstate().getAddress())){
                report.getDeviceAlarms().get(device.getEstate().getAddress()).put(device.getPathToFile(), deviceAlarms.size());
                continue;
            }
            report.getDeviceAlarms().put(device.getEstate().getAddress(), new HashMap<>());
            report.getDeviceAlarms().get(device.getEstate().getAddress()).put(device.getPathToFile(), deviceAlarms.size());
        }

        return report;
    }
}
