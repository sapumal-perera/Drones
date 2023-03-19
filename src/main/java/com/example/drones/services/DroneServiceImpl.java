package com.example.drones.services;

        import com.example.drones.repositories.MedicationRepository;
        import com.example.drones.models.Drone;
        import com.example.drones.models.DroneState;
        import com.example.drones.models.Medication;
        import com.example.drones.repositories.DroneRepository;
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import jakarta.transaction.Transactional;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import java.util.List;

@Service
@Transactional
public class DroneServiceImpl implements DroneService {

    Gson gsonBuilder = new GsonBuilder().create();

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public Drone registerDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public Medication loadMedicationsToDrone(Medication medication) {
        return medicationRepository.save(medication);
    }

    @Override
    public List<Medication> getLoadedMedications(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return drone.getMedications();
    }

    @Override
    public Drone getDroneBySerialNumber(String serialNum) {
        return droneRepository.findBySerialNumber(serialNum);
    }

    @Override
    public List<Drone> getAvailableDrones() {
        return droneRepository.findByState(DroneState.IDLE);
    }

    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public int getDroneBatteryLevel(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        if(drone != null) {
            return drone.getBatteryCapacity();
        } else {
            return -1;
        }
    }

    @Override
    public List<Medication> loadDrone(List<Medication> medications) {
        return medicationRepository.saveAll(medications);
    }
}