package com.example.drones.models;
import jakarta.persistence.*;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int weight;
    private String code;
    private String image;
    private String droneSerialNumber;


    public Medication(String name, int weight, String code, String image, String drone) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.droneSerialNumber = drone;
    }

    public Medication() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDrone() {
        return droneSerialNumber;
    }

    public void setDrone(String drone) {
        this.droneSerialNumber = drone;
    }
}

