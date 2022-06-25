package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.DeviceAlarm;

public interface DeviceAlarmRepository extends JpaRepository<DeviceAlarm, Long> {
}
