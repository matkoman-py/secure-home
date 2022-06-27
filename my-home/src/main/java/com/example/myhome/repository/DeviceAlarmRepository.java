package com.example.myhome.repository;

import com.example.myhome.domain.Device;
import com.example.myhome.domain.DeviceAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceAlarmRepository extends JpaRepository<DeviceAlarm, Long> {
    List<DeviceAlarm> findByDevice(Device device);
}
