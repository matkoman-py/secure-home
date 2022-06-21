package com.example.myhome.controller;

import com.example.myhome.domain.Device;
import com.example.myhome.domain.DeviceDTO;
import com.example.myhome.domain.Message;
import com.example.myhome.service.DeviceService;
import com.example.myhome.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/devices", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public List<DeviceDTO> getDevicesForUser() {
        return deviceService.getDevicesDtoForUser();
    }

    @GetMapping("/messages")
    public List<Message> getAllMessagesForUser(
            @RequestParam(value = "message", defaultValue = "") String message,
            @RequestParam(value = "dateAfter", defaultValue = "") String dateAfter,
            @RequestParam(value = "dateBefore", defaultValue = "") String dateBefore,
            @RequestParam(value = "pathToFile", defaultValue = "") String pathToFile

    ) throws ParseException {
        return deviceService.getMessagesFromUserDevices(message, dateAfter, dateBefore, pathToFile);
    }
}
