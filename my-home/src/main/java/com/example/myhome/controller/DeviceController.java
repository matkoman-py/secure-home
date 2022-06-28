package com.example.myhome.controller;

import com.example.myhome.domain.*;
import com.example.myhome.service.CustomLogger;
import com.example.myhome.service.DeviceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/devices", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

	@Autowired
	CustomLogger customLogger;
	
	Logger logger = LoggerFactory.getLogger(CustomLogger.class);

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public List<DeviceDTO> getDevicesForUser() {
    	logger.info(customLogger.info("User requests all devices"));
        return deviceService.getDevicesDtoForUser();
    }

    @GetMapping("/messages")
    public List<MessageDTO> getAllMessagesForUser(
            @RequestParam(value = "message", defaultValue = "") String message,
            @RequestParam(value = "dateAfter", defaultValue = "") String dateAfter,
            @RequestParam(value = "dateBefore", defaultValue = "") String dateBefore,
            @RequestParam(value = "pathToFile", defaultValue = "") String pathToFile
    ) throws ParseException {
    	logger.info(customLogger.info("Users requests messages"));
        return deviceService.getMessagesFromUserDevices(message, dateAfter, dateBefore, pathToFile);
    }

    @GetMapping("/alarms")
    public List<DeviceAlarmDTO> getAllAlarmsForUser() throws ParseException {
    	logger.info(customLogger.info("Users requests alarms"));
    	return deviceService.getAllAlarmsForUser();
    }

    @GetMapping("/report")
    public ReportDTO getReport(
            @RequestParam(value = "dateAfter", defaultValue = "") String dateAfter,
            @RequestParam(value = "dateBefore", defaultValue = "") String dateBefore
    ) throws ParseException {
    	logger.info(customLogger.info("Users requests reports"));
        return deviceService.getReport(dateAfter, dateBefore);
    }
}
