package com.example.myhome.repository;

import com.example.myhome.domain.DeviceAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceAlarmRepository extends JpaRepository<DeviceAlarm, Long> {
}
