package com.example.dronepizza.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;

    private double latitude;
    private double longitude;

    @Column(nullable = true)
    private String navn;

    @JsonIgnore
    @OneToMany(mappedBy = "station")
    private List<Drone> drones;

    // Tom konstruktør
    public Station() {}

    // Konstruktør for simplere kode i InitData
    public Station(String navn, double latitude, double longitude) {
        this.navn = navn;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public void setDrones(List<Drone> drones) {
        this.drones = drones;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
