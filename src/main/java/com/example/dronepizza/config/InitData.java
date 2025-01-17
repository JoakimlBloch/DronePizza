package com.example.dronepizza.config;

import com.example.dronepizza.model.Drone;
import com.example.dronepizza.model.Levering;
import com.example.dronepizza.model.Pizza;
import com.example.dronepizza.model.Station;
import com.example.dronepizza.model.enums.DroneStatus;
import com.example.dronepizza.repository.PizzaRepository;
import com.example.dronepizza.repository.StationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class InitData implements CommandLineRunner {

    // Dependency injection
    private final PizzaRepository pizzaRepository;
    private final StationRepository stationRepository;

    public InitData(PizzaRepository pizzaRepository, StationRepository stationRepository) {
        this.pizzaRepository = pizzaRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Opretter stationer tæt på København
        Station station1 = new Station("Amagerbro", 55.40,12.36); // Amagerbro (55°40N 12°36E)
        Station station2 = new Station("Vesterbro", 55.40,12.33); // Vesterbro (55°40N 12°33E)
        Station station3 = new Station("Nørrebro", 55.4140, 12.33); // Nørrebro (55°41'40N 12°33E)

        // Gemmer stationerne i databasen
        stationRepository.save(station1);
        stationRepository.save(station2);
        stationRepository.save(station3);

        // Opretter pizzaer
        Pizza pizza1 = new Pizza("Margarita", 80);
        Pizza pizza2 = new Pizza("Pepperoni", 89);
        Pizza pizza3 = new Pizza("Hawaii", 91);
        Pizza pizza4 = new Pizza("The Best Pizza in Denver", 70);
        Pizza pizza5 = new Pizza("Capricciosa", 95);

        // Gemmer pizzaerne i databasen
        pizzaRepository.save(pizza1);
        pizzaRepository.save(pizza2);
        pizzaRepository.save(pizza3);
        pizzaRepository.save(pizza4);
        pizzaRepository.save(pizza5);
    }

}
