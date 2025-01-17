package com.example.dronepizza.repository;

import com.example.dronepizza.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza,Long> {}
