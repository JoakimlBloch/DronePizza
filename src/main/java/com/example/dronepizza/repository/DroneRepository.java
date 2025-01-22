package com.example.dronepizza.repository;

import com.example.dronepizza.model.Drone;
import com.example.dronepizza.model.enums.DroneStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByDriftsStatus(DroneStatus status);
}
