package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.NonValidToken;
import siitnocu.bezbednost.data.SystemAlarm;

public interface SystemAlarmRepository extends JpaRepository<SystemAlarm, Long> {
}
