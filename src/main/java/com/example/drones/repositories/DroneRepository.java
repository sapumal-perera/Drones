package com.example.drones.repositories;

import com.example.drones.models.Drone;
import com.example.drones.models.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    Drone findBySerialNumber(String serialNumber);
    List<Drone> findByState(DroneState state);
}