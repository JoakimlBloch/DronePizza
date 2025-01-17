package com.example.dronepizza.controller;

import com.example.dronepizza.model.Levering;
import com.example.dronepizza.repository.LeveringRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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

    @Test
    void testGetAllDeliveries() throws Exception {

    }

    @Test
    void testAddDelivery() throws Exception {

    }

    @Test
    void testQueueDelivery() throws Exception {

    }

    @Test
    void testScheduleDelivery() throws Exception {

    }

    @Test
    void testFinishDelivery() throws Exception {

    }

}
