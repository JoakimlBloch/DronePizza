package com.example.dronepizza.repository;

import com.example.dronepizza.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {}
