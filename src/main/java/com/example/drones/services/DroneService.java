package com.example.drones.services;
import com.example.drones.models.Drone;
import com.example.drones.models.Medication;

import java.util.List;

public interface DroneService {

    Drone registerDrone(Drone drone);

    Medication loadMedicationsToDrone(Medication medication);

    List<Drone> getAvailableDrones();

    List<Drone> getAllDrones();

    Drone getDroneBySerialNumber(String serialNum);

    int getDroneBatteryLevel(String serialNumber);

    List<Medication> loadDrone(List<Medication> medications);
}
