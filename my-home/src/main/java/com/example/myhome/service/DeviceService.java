package com.example.myhome.service;

import com.example.myhome.domain.*;
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

    public List<Message> getMessagesFromUserDevices(String message, String dateAfterString, String dateBeforeString, String pathToFile) throws ParseException {
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
        for(Device d : devices){
            messagesFromDevices.addAll(messageRepository.search(dateAfter, dateBefore, message, d.getId(), pathToFile));
        }
        return messagesFromDevices;
    }

}
