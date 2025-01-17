package com.example.dronepizza.controller;

import com.example.dronepizza.model.Drone;
import com.example.dronepizza.model.Station;
import com.example.dronepizza.model.enums.DroneStatus;
import com.example.dronepizza.repository.DroneRepository;
import com.example.dronepizza.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DroneController droneController;

    @Autowired
    private StationRepository stationRepository;

    private Station testStation;

    @BeforeEach
    void setUp() {
        // Set up en station i systemet
        testStation = new Station();
        testStation.setNavn("TestStation");
        testStation.setLatitude(55.676);
        testStation.setLongitude(12.568);
        stationRepository.save(testStation);

        // Sørge for der ingen drones er i systemet
        droneRepository.deleteAll();
    }

    @Test
    void testGetAllDrones() throws Exception {
        // Gem 2 droner i systemet
        Drone drone1 = new Drone();
        drone1.setSerialUuid(UUID.randomUUID());
        drone1.setDriftsStatus(DroneStatus.I_DRIFT);
        drone1.setStation(testStation);
        droneRepository.save(drone1);

        Drone drone2 = new Drone();
        drone2.setSerialUuid(UUID.randomUUID());
        drone2.setDriftsStatus(DroneStatus.I_DRIFT);
        drone2.setStation(testStation);
        droneRepository.save(drone2);

        mockMvc.perform(get("/drones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testAddDrone() throws Exception {
        mockMvc.perform(post("/drones/add"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driftsStatus").value(DroneStatus.I_DRIFT.toString()));
    }

    @Test
    void testEnableDrone() throws Exception {
        // Opret ny drone
        Drone drone = new Drone();
        drone.setSerialUuid(UUID.randomUUID());
        drone.setDriftsStatus(DroneStatus.UDE_AF_DRIFT);
        droneRepository.save(drone);

        // Aktiver dronen
        mockMvc.perform(post("/drones/enable")
                        .param("droneId", String.valueOf(drone.getDroneId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driftsStatus").value(DroneStatus.I_DRIFT.toString()));
    }

    @Test
    void testAddDroneWithoutStation() throws Exception {
        // Fjern alle stationer
        stationRepository.deleteAll();

        // Prøv at lave en ny drone (burde returnerer BAD_REQUEST)
        mockMvc.perform(post("/drones/add"))
                .andExpect(status().isBadRequest());
    }

}
