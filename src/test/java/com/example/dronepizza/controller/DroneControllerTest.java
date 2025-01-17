package com.example.dronepizza.controller;

import com.example.dronepizza.model.Drone;
import com.example.dronepizza.model.enums.DroneStatus;
import com.example.dronepizza.repository.DroneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class DroneControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DroneController droneController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(droneController).build();
    }

    @Test
    void testGetAllDrones() throws Exception {

    }

    @Test
    void testAddDrone() throws Exception {

    }

    @Test
    void testEnableDrone() throws Exception {

    }

    @Test
    void testDisableDrone() throws Exception {

    }

    @Test
    void testRetireDrone() throws Exception {

    }

}
