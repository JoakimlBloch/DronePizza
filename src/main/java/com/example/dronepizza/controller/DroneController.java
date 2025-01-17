package com.example.dronepizza.controller;

import com.example.dronepizza.model.Drone;
import com.example.dronepizza.model.Station;
import com.example.dronepizza.model.enums.DroneStatus;
import com.example.dronepizza.repository.DroneRepository;
import com.example.dronepizza.repository.StationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/drones")
public class DroneController {

    // Dependency injection
    private DroneRepository droneRepository;
    private StationRepository stationRepository;

    public DroneController(DroneRepository droneRepository, StationRepository stationRepository) {
        this.droneRepository = droneRepository;
        this.stationRepository = stationRepository;
    }

    // Returnerer en liste af alle droner i systemet
    @GetMapping
    public List<Drone> getDrones() {
        return droneRepository.findAll();
    }

    // Opret en drone
    @PostMapping("/add")
    public ResponseEntity<Drone> addDrone() {

        // Tjek om der findes en station i DB
        List<Station> stations = stationRepository.findAll();
        if (stations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Fejl, ingen stationer
        }

        // Find station med færrest droner
        Station station = stations.stream()
                .min(Comparator.comparingInt(s -> s.getDrones().size()))
                .orElseThrow(() -> new RuntimeException("Ingen stationer tilgængelige"));

        // Opret drone
        Drone drone = new Drone();
        drone.setSerialUuid(UUID.randomUUID());
        drone.setDriftsStatus(DroneStatus.I_DRIFT);
        drone.setStation(station);

        // Gem drone i system
        droneRepository.save(drone);

        return new ResponseEntity<>(drone, HttpStatus.OK);
    }

    // Endpoints til at ændre driftsstatus på droner
    @PostMapping("/enable")
    public ResponseEntity<Drone> enableDrone(@RequestParam Long droneId) {
        // Tjek om drone eksisterer
        Optional<Drone> droneToChange = droneRepository.findById(droneId);
        if (droneToChange.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Drone findes ikke
        }

        // Sæt driftsstatus til i drift
        Drone drone = droneToChange.get();
        drone.setDriftsStatus(DroneStatus.I_DRIFT);
        droneRepository.save(drone);

        return new ResponseEntity<>(drone, HttpStatus.OK); // Succes
    }

    @PostMapping("/disable")
    public ResponseEntity<Drone> disableDrone(@RequestParam Long droneId) {
        // Tjek om drone eksisterer
        Optional<Drone> droneToChange = droneRepository.findById(droneId);
        if (droneToChange.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Drone findes ikke
        }

        // Sæt driftsstatus til ude af drift
        Drone drone = droneToChange.get();
        drone.setDriftsStatus(DroneStatus.UDE_AF_DRIFT);
        droneRepository.save(drone);

        return new ResponseEntity<>(drone, HttpStatus.OK); // Succes
    }

    @PostMapping("/retire")
    public ResponseEntity<Drone> retireDrone(@RequestParam Long droneId) {
        // Tjek om drone eksisterer
        Optional<Drone> droneToChange = droneRepository.findById(droneId);
        if (droneToChange.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Drone findes ikke
        }

        // Sæt driftsstatus til udfaset
        Drone drone = droneToChange.get();
        drone.setDriftsStatus(DroneStatus.UDFASET);
        droneRepository.save(drone);

        return new ResponseEntity<>(drone, HttpStatus.OK); // Succes
    }

}
