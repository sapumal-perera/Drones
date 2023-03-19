package com.example.drones;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneManagerImpl implements DroneManager {

    Gson gsonBuilder = new GsonBuilder().create();

    @Autowired
    private DroneServiceImpl droneDataService;

    @Override
    public Drone registerDrone(Drone drone) {
        return droneDataService.registerDrone(drone);
    }

    @Override
    public ResponseDTO loadMedicationsToDrone(String serialNumber, Medication medication) {
        Medication drone =  droneDataService.loadMedicationsToDrone(serialNumber, medication);
        if(drone != null) {
            return new ResponseDTO(200, "Medication loaded successfully");
        } else {
            return new ResponseDTO(500, "Error occurred");
        }
    }

    @Override
    public ResponseDTO getLoadedMedications(String serialNumber) {
        List<Medication> drone = droneDataService.getLoadedMedications(serialNumber);
        if(drone.size() > 0) {
            return new ResponseDTO(200, "Medication loaded successfully");
        } else {
            return new ResponseDTO(500, "Error occurred");
        }
    }

    @Override
    public List<Drone> getAvailableDrones() {
        return droneDataService.getAvailableDrones();
    }

    @Override
    public ResponseDTO getDroneBatteryLevel(String serialNumber) {
        int droneBattery = droneDataService.getDroneBatteryLevel(serialNumber);
        if(droneBattery != -1) {
            return new ResponseDTO(200, Integer.toString(droneBattery));
        } else {
            return new ResponseDTO(500, "Error occurred");
        }

    }

    @Override
    public ResponseDTO loadDrone(String serialNumber, List<Medication> medications) {
        Drone drone = droneDataService.getDroneBySerialNumber(serialNumber);
        double totalLoadOfWeight = 0;
        String responseJson;
        double weightLimit = drone.getWeightLimit();
        for(Medication med: medications) {
            if (!med.getName().matches("^[a-zA-Z0-9_-]+$")) {
                return new ResponseDTO(400, "The medication name contains other characters besides letters, numbers, hyphens, and underscores.");

            } else if (!med.getCode().matches("^[A-Z0-9_]+$")) {
                return new ResponseDTO(400, "The medication code contains other characters besides  upper case letters, underscore and numbers.");
            } else {
                med.setDrone(drone);
                totalLoadOfWeight += med.getWeight();
            }
        }
        if(totalLoadOfWeight > weightLimit) {
            return new ResponseDTO(400, "The medication load exceeds the weight limit.");
        }
        List<Medication> meds = droneDataService.loadDrone(serialNumber, medications);
        if(meds.size() > 0) {
            return new ResponseDTO(200, "medications loaded successfully");
        } else {
            return new ResponseDTO(500, "Error occurred when loading medications");
        }
    }
}