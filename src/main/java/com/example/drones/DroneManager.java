package com.example.drones;

import java.util.List;

public interface DroneManager {

    ResponseDTO registerDrone(Drone drone);

    ResponseDTO addMedicationsToDrone(String serialNumber, Medication medication);

    List<Medication> getLoadedMedications(String serialNumber);

    List<Drone> getAvailableDrones();

    List<Drone> getAllDrones();

    ResponseDTO getDroneBatteryLevel(String serialNumber);

    ResponseDTO unloadMedication(String serialNumber);

    void logBatteryCapacity();
}

