package com.example.dronepizza.repository;

import com.example.dronepizza.model.Levering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LeveringRepository extends JpaRepository<Levering, Long> {
    List<Levering> findByFaktiskLeveringIsNull();
    List<Levering> findByDroneIsNull();
}
