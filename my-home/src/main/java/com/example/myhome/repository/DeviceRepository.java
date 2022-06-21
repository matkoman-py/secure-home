package com.example.myhome.repository;

import com.example.myhome.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByPathToFile(String pathToFile);
}
