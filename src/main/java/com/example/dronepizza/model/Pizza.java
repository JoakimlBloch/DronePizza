package com.example.dronepizza.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pizzaId;

    private String titel;
    private int pris;

    @OneToMany(mappedBy = "pizza")
    @JsonIgnore
    private List<Levering> leveringer;

    // Tom konstroktør
    public Pizza() {}

    // Konstruktør for simplere kode i InitData
    public Pizza(String titel, int pris) {
        this.titel = titel;
        this.pris = pris;
    }

    // Getters & setters
    public Long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public List<Levering> getLeveringer() {
        return leveringer;
    }

    public void setLeveringer(List<Levering> leveringer) {
        this.leveringer = leveringer;
    }
}
