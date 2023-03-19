package com.example.drones;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DroneServiceImpl implements DroneService {
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public Drone registerDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public Medication loadMedicationsToDrone(String serialNumber, Medication medication) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        medication.setDrone(drone);
        return medicationRepository.save(medication);
    }

    @Override
    public List<Medication> getLoadedMedications(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return drone.getMedications();
    }

    @Override
    public List<Drone> getAvailableDrones() {
        return droneRepository.findByState(DroneState.IDLE);
    }

    @Override
    public int getDroneBatteryLevel(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return drone.getBatteryCapacity();
    }

    @Override
    public String loadDrone(String serialNumber, List<Medication> medications) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        double totalLoadOfWeight = 0;
        double weightLimit = drone.getWeightLimit();
        for(Medication med: medications) {
            if (!med.getName().matches("^[a-zA-Z0-9_-]+$")) {
                return "The medication name contains other characters besides letters, numbers, hyphens, and underscores.";
            } else if (!med.getCode().matches("^[A-Z0-9_]+$")) {
                return "The medication code contains other characters besides  upper case letters, underscore and numbers.";
            } else {
                med.setDrone(drone);
                totalLoadOfWeight += med.getWeight();
            }
        }
        if(totalLoadOfWeight > weightLimit) {
            return "The medication load exceeds the weight limit.";
        }
        List<Medication> meds =  medicationRepository.saveAll(medications);
        if(meds.size() > 0) {
            return "medications loaded successfully";
        } else {
            return "Error occurred when loading medications";
        }
    }
}