package com.example.drones;
import java.util.List;

public interface DroneService {

    Drone registerDrone(Drone drone);

    Medication loadMedicationsToDrone(Medication medication);

    List<Medication> getLoadedMedications(String serialNumber);

    List<Drone> getAvailableDrones();

    List<Drone> getAllDrones();

    Drone getDroneBySerialNumber(String serialNum);

    int getDroneBatteryLevel(String serialNumber);

    List<Medication> loadDrone(List<Medication> medications);
}
