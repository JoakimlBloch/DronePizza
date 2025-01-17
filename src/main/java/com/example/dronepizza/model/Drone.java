package com.example.dronepizza.model;

import com.example.dronepizza.model.enums.DroneStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long droneId;

    @Column(unique = true, nullable = false)
    private UUID serialUuid;

    @Enumerated(EnumType.STRING)
    private DroneStatus driftsStatus;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @JsonIgnore
    @OneToMany(mappedBy = "drone")
    private List<Levering> leveringer;

    // Tom konstruktør
    public Drone() {}

    // Konstruktør
    public Drone(UUID serialUuid, DroneStatus driftsStatus, Station station) {
        this.serialUuid = serialUuid;
        this.driftsStatus = driftsStatus;
        this.station = station;
    }

    // Getters and setters
    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public UUID getSerialUuid() {
        return serialUuid;
    }

    public void setSerialUuid(UUID serialUuid) {
        this.serialUuid = serialUuid;
    }

    public DroneStatus getDriftsStatus() {
        return driftsStatus;
    }

    public void setDriftsStatus(DroneStatus driftsStatus) {
        this.driftsStatus = driftsStatus;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Levering> getLeveringer() {
        return leveringer;
    }

    public void setLeveringer(List<Levering> leveringer) {
        this.leveringer = leveringer;
    }
}
