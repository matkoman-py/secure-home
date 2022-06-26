package com.example.myhome.repository;

import com.example.myhome.domain.SystemAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemAlarmRepository extends JpaRepository<SystemAlarm, Long> {
}
