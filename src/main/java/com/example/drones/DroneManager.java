package com.example.drones;

import java.util.List;

public interface DroneManager {

    Drone registerDrone(Drone drone);

    ResponseDTO loadMedicationsToDrone(String serialNumber, Medication medication);

    ResponseDTO getLoadedMedications(String serialNumber);

    List<Drone> getAvailableDrones();

    ResponseDTO getDroneBatteryLevel(String serialNumber);

    ResponseDTO loadDrone(String serialNumber, List<Medication> medications);
}

