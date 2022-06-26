package siitnocu.bezbednost.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siitnocu.bezbednost.data.DeviceAlarm;
import siitnocu.bezbednost.data.SystemAlarm;
import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.dto.DeviceAlarmDTO;
import siitnocu.bezbednost.services.AlarmService;
import siitnocu.bezbednost.services.CustomLogger;

import java.util.List;

@RestController
@RequestMapping(value = "/api/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    CustomLogger customLogger;

    Logger logger = LoggerFactory.getLogger(CustomLogger.class);

    @GetMapping("/system")
    @PreAuthorize("hasAuthority('READ_SYSTEM_ALARMS')")
    public List<SystemAlarm> getAllSystemAlarms() {
        logger.info(customLogger.info("Requesting system alarms "));
        return this.alarmService.getAllSystemAlarms();
    }

    @GetMapping("/devices")
    @PreAuthorize("hasAuthority('READ_DEVICE_ALARMS')")
    public List<DeviceAlarmDTO> getAllDevicesAlarmsAlarms() {
        logger.info(customLogger.info("Requesting devices alarms"));
        return this.alarmService.getAllDevicesAlarms();
    }

}
