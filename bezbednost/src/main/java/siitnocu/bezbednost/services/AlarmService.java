package siitnocu.bezbednost.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siitnocu.bezbednost.data.SystemAlarm;
import siitnocu.bezbednost.dto.DeviceAlarmDTO;
import siitnocu.bezbednost.repositories.DeviceAlarmRepository;
import siitnocu.bezbednost.repositories.SystemAlarmRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmService {

	@Autowired
	private SystemAlarmRepository systemAlarmRepository;

	@Autowired
	private DeviceAlarmRepository deviceAlarmRepository;

	public List<SystemAlarm> getAllSystemAlarms() {
		return systemAlarmRepository.findAll();
	}

	public List<DeviceAlarmDTO> getAllDevicesAlarms() {
		return deviceAlarmRepository.findAll().stream()
				.map(alarm -> new DeviceAlarmDTO(alarm.getId(), alarm.getMessage(), alarm.getDate(),
						alarm.getDevice().getType(), alarm.getDevice().getEstate().getAddress())

				).collect(Collectors.toList());
	}
}
