package com.example.dronepizza.controller;

import com.example.dronepizza.model.Drone;
import com.example.dronepizza.model.Levering;
import com.example.dronepizza.model.Pizza;
import com.example.dronepizza.model.enums.DroneStatus;
import com.example.dronepizza.repository.DroneRepository;
import com.example.dronepizza.repository.LeveringRepository;
import com.example.dronepizza.repository.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LeveringRepository leveringRepository;

    @Autowired
    private LeveringController leveringController;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    private Pizza testPizza;
    private Drone testDrone;

    // Set up test data
    @BeforeEach
    void setUp() {
        testPizza = new Pizza();
        testPizza.setTitel("Margherita");
        testPizza.setPris(99);
        pizzaRepository.save(testPizza);

        testDrone = new Drone();
        testDrone.setSerialUuid(UUID.randomUUID());
        testDrone.setDriftsStatus(DroneStatus.I_DRIFT);
        droneRepository.save(testDrone);
    }

    @Test
    void testGetAllDeliveries() throws Exception {
        // Gem 2 leveringer i systemet
        Levering levering = new Levering();
        levering.setPizza(testPizza);
        levering.setDrone(testDrone);
        leveringRepository.save(levering);

        Levering levering2 = new Levering();
        levering2.setPizza(testPizza);
        levering2.setDrone(testDrone);
        leveringRepository.save(levering2);

        // Hent alle leveringer
        mockMvc.perform(get("/deliveries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testAddDelivery() throws Exception {
        // Create a new delivery
        mockMvc.perform(MockMvcRequestBuilders.post("/deliveries/add")
                        .param("pizzaId", String.valueOf(testPizza.getPizzaId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pizza.pizzaId").value(testPizza.getPizzaId()))
                .andExpect(jsonPath("$.adresse").value("Hardcodevej 1"));
    }

    @Test
    void testFinishDelivery() throws Exception {
        // Tilføj levering til systemet
        Levering levering = new Levering();
        levering.setPizza(testPizza);
        levering.setDrone(testDrone);
        leveringRepository.save(levering);
        Long leveringId = levering.getLeveringId();

        // Afslut leveringen
        mockMvc.perform(post("/deliveries/finish")
                        .param("leveringId", String.valueOf(leveringId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.faktiskLevering").isNotEmpty());
    }

}
