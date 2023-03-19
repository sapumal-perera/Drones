package com.example.drones.datamanagers;

import com.example.drones.datamanagers.DroneManager;
import com.example.drones.models.Drone;
import com.example.drones.models.DroneState;
import com.example.drones.models.Medication;
import com.example.drones.models.ResponseDTO;
import com.example.drones.services.DroneServiceImpl;
import com.example.drones.services.MedicationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DroneManagerImpl implements DroneManager {

    Gson gsonBuilder = new GsonBuilder().create();
    final int FLEET_LIMIT = 10;
    final int BATTERY_LIMIT = 25;
    final double WEIGHT_LIMIT = 500;
    final int SERIAL_CHARACTER_LIMIT = 100;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private DroneServiceImpl droneDataService;

    /**
     * registerDrone
     * @param drone
     * @return
     */
    @Override
    public ResponseDTO registerDrone(Drone drone) {
        if (droneDataService.getAllDroneCount() == FLEET_LIMIT) {
            return new ResponseDTO(200, "Drone fleet is full. Unable add new Drones");
        } else if (!validateSerialNumber(drone.getSerialNumber())) {
            return new ResponseDTO(400, "Entered serial number is incorrect");
        }  else if(!validateWightLimit(drone.getWeightLimit())) {
            return new ResponseDTO(400, "The medication load exceeds the weight limit.");
        } else {
            try {
                droneDataService.registerDrone(drone);
                return new ResponseDTO(200, "Drone registered successfully");
            } catch (Exception e) {
                return new ResponseDTO(500, "Error occurred while adding the drone" + e.toString());
            }
        }
    }

    /**
     * addMedicationsToDrone
     * @param serialNumber
     * @param medication
     * @return
     */
    @Override
    public ResponseDTO addMedicationsToDrone(String serialNumber, Medication medication) {
        Drone drone = droneDataService.getDroneBySerialNumber(serialNumber);
        if(drone != null) {
            if (!isMedicinesValid(medication)) {
                return new ResponseDTO(400, "The medication name contains other characters besides letters, numbers, hyphens, and underscores.");
            }  else if(getAvailableMedicationWeight(drone) < medication.getWeight()) {
                return new ResponseDTO(400, "The medication load exceeds the weight limit.");
            } else if (!isDroneBatteryAvailable(drone)) {
                return new ResponseDTO(400, "Drone battery level is low. unable to load medication");
            } else {
                drone.setState(DroneState.LOADING);
                medication.setDrone(drone.getSerialNumber());
                try {
                    droneDataService.loadMedicationsToDrone(medication);
                    return new ResponseDTO(200, "Medication loaded successfully");
                } catch (Exception e) {
                    return new ResponseDTO(500, "Error occurred while adding the medication" + e.toString());
                }
            }
        } else {
            return new ResponseDTO(400, "Drone not found");
        }
    }

    /**
     * getLoadedMedications
     * @param serialNumber
     * @return
     */
    @Override
    public List<Medication> getLoadedMedications(String serialNumber) {
        return medicationService.getMedicationByDroneSerialNumber(serialNumber);
    }

    /**
     * getAvailableDrones
     * @return
     */
    @Override
    public List<Drone> getAvailableDrones() {
        return droneDataService.getAvailableDrones();
    }

    /**
     * getAllDrones
     * @return
     */
    @Override
    public List<Drone> getAllDrones() {
        return droneDataService.getAllDrones();
    }

    /**
     * getDroneBatteryLevel
     * @param serialNumber
     * @return
     */
    @Override
    public ResponseDTO getDroneBatteryLevel(String serialNumber) {
        int droneBattery = droneDataService.getDroneBatteryLevel(serialNumber);
        if(droneBattery != -1) {
            return new ResponseDTO(200, "Battery capacity: " + Integer.toString(droneBattery));
        } else {
            return new ResponseDTO(500, "Drone not found. Please check the serial number");
        }

    }

    /**
     * unloadMedication
     * @param serialNumber
     * @return
     */
    @Override
    public ResponseDTO unloadMedication(String serialNumber) {
        try {
            Drone drone = droneDataService.getDroneBySerialNumber(serialNumber);
            List<Medication> medicationsForDrone = medicationService.getMedicationByDroneSerialNumber(drone.getSerialNumber());
            if(drone != null && medicationsForDrone !=null && medicationsForDrone.size() > 0) {
                drone.setState(DroneState.IDLE);
                for (Medication med: medicationsForDrone) {
                    try{
                        medicationService.deleteMedication(med.getId());
                    } catch (Exception e) {
                        return new ResponseDTO(500, "Error occurred");
                    }
                }
            } else {
                return new ResponseDTO(200, "No medicine available to unload");
            }
            return new ResponseDTO(200, "Medicine unloaded successfully");
        } catch (Exception e){
            return new ResponseDTO(500, "Drone not found. Please check the serial number");
        }
    }

    /**
     * logBatteryCapacity
     */
    public void logBatteryCapacity() {
        try {
            File file = new File("drone_battery_log.txt");
            FileWriter writer = new FileWriter(file, true);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            List<Drone> drones = droneDataService.getAllDrones();
            for (Drone drone : drones) {
                String log = String.format("%s - Drone %s battery capacity: %d%%\n",
                        formatter.format(date), drone.getSerialNumber(), drone.getBatteryCapacity());
                writer.write(log);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * isMedicinesValid
     * @param medication
     * @return
     */
    public boolean isMedicinesValid(Medication medication) {
        return medication.getName().matches("^[a-zA-Z0-9_-]+$") || medication.getCode().matches("^[A-Z0-9_]+$");
    }

    /**
     * isDroneBatteryAvailable
     * @param drone
     * @return
     */
    public boolean isDroneBatteryAvailable(Drone drone) {
        return drone.getBatteryCapacity() >= BATTERY_LIMIT;
    }

    /**
     * getAvailableMedicationWeight
     * @param drone
     * @return
     */
    public double getAvailableMedicationWeight(Drone drone) {
        List <Medication> medications = medicationService.getMedicationByDroneSerialNumber(drone.getSerialNumber());
        double loadedWeight = 0;
        if(medications.size() > 0) {
            for(Medication med: medications) {
                loadedWeight += med.getWeight();
            }
        } else {
            return drone.getWeightLimit();
        }
        return drone.getWeightLimit() - loadedWeight;
    }

    /**
     * validateWightLimit
     * @param weightLomit
     * @return
     */
    public boolean validateWightLimit(double weightLomit) {
        return weightLomit <= WEIGHT_LIMIT;
    }

    /**
     * validateSerialNumber
     * @param serialNum
     * @return
     */
    public boolean validateSerialNumber(String serialNum) {
        return serialNum.length() <= SERIAL_CHARACTER_LIMIT;
    }

}