package com.example.dronepizza.controller;

import com.example.dronepizza.model.Drone;
import com.example.dronepizza.model.Levering;
import com.example.dronepizza.model.Pizza;
import com.example.dronepizza.model.enums.DroneStatus;
import com.example.dronepizza.repository.DroneRepository;
import com.example.dronepizza.repository.LeveringRepository;
import com.example.dronepizza.repository.PizzaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deliveries")
public class LeveringController {

    // Dependency injection
    private LeveringRepository leveringRepository;
    private DroneRepository droneRepository;
    private PizzaRepository pizzaRepository;

    public LeveringController(LeveringRepository leveringRepository, DroneRepository droneRepository, PizzaRepository pizzaRepository) {
        this.leveringRepository = leveringRepository;
        this.droneRepository = droneRepository;
        this.pizzaRepository = pizzaRepository;
    }

    // Returnerer en liste af alle leveringer, der IKKE er leveret endnu
    @GetMapping
    public List<Levering> getAllDeliveries() {
        return leveringRepository.findByFaktiskLeveringIsNull();
    }

    // Opret en levering
    @PostMapping("/add")
    public ResponseEntity<Levering> addDelivery(@RequestParam Long pizzaId) {
        // Find pizza med unik ID i system og tjek om den eksisterer
        Optional<Pizza> pizzaToAddToDelivery = pizzaRepository.findById(pizzaId);
        if (pizzaToAddToDelivery.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Pizza findes ikke
        }

        // Opret levering, tilføj pizza og forventet leveringstid
        Pizza pizza = pizzaToAddToDelivery.get();
        Levering levering = new Levering();
        levering.setPizza(pizza);
        levering.setAdresse("Hardcodevej 1");
        levering.setForventetLevering(LocalDateTime.now().plusMinutes(30));
        levering.setFaktiskLevering(null); // Faktisk leveringstidspunkt er udefineret ved oprettelse af ny levering
        levering.setDrone(null); // Drone er udefineret ved oprettelse

        // Gem levering i system
        leveringRepository.save(levering);

        return new ResponseEntity<>(levering, HttpStatus.OK); // Succes
    }

    @GetMapping("/queue")
    public List<Levering> getDeliveriesWithoutDrones() {
        return leveringRepository.findByDroneIsNull();
    }

    @PostMapping("/schedule")
    public ResponseEntity<Levering> scheduleDelivery(@RequestParam Long leveringId, @RequestParam Long droneId) {
        Optional<Levering> leveringToSchedule = leveringRepository.findById(leveringId);
        if (leveringToSchedule.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Levering levering = leveringToSchedule.get();
        if (levering.getDrone() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Drone> droneToAssign = droneRepository.findById(droneId);
        if (droneToAssign.isEmpty() || droneToAssign.get().getDriftsStatus() != DroneStatus.I_DRIFT) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Drone drone = droneToAssign.get();
        levering.setDrone(drone);
        leveringRepository.save(levering);

        return new ResponseEntity<>(levering, HttpStatus.OK);
    }

    @PostMapping("/finish")
    public ResponseEntity<Levering> finishDelivery(@RequestParam Long leveringId) {
        // Find levering med unik ID og tjek om den eksisterer
        Optional<Levering> leveringToFinish = leveringRepository.findById(leveringId);
        if (leveringToFinish.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Levering findes ikke
        }

        // Tjek om levering har en drone for at kunne markeres som done
        Levering levering = leveringToFinish.get();
        if (levering.getDrone() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Leveringen har ikke en drone
        }

        // Set faktisk leveringstid og gem levering
        levering.setFaktiskLevering(LocalDateTime.now());
        leveringRepository.save(levering);

        return new ResponseEntity<>(levering, HttpStatus.OK); // Succes
    }

}
