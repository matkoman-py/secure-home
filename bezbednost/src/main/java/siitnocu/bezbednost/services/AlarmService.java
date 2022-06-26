package siitnocu.bezbednost.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siitnocu.bezbednost.data.DeviceAlarm;
import siitnocu.bezbednost.data.SystemAlarm;
import siitnocu.bezbednost.repositories.DeviceAlarmRepository;
import siitnocu.bezbednost.repositories.SystemAlarmRepository;

import java.util.List;

@Service
public class AlarmService {

    @Autowired
    private SystemAlarmRepository systemAlarmRepository;

    @Autowired
    private DeviceAlarmRepository deviceAlarmRepository;


    public List<SystemAlarm> getAllSystemAlarms() {
        return systemAlarmRepository.findAll();
    }

    public List<DeviceAlarm> getAllDevicesAlarms() {
        return deviceAlarmRepository.findAll();
    }
}
