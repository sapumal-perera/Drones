package com.example.drones;
import java.util.List;

public interface DroneService {

    Drone registerDrone(Drone drone);

    Medication loadMedicationsToDrone(String serialNumber, Medication medication);

    List<Medication> getLoadedMedications(String serialNumber);

    List<Drone> getAvailableDrones();

    Drone getDroneBySerialNumber(String serialNum);

    int getDroneBatteryLevel(String serialNumber);

    List<Medication> loadDrone(String serialNumber, List<Medication> medications);
}
