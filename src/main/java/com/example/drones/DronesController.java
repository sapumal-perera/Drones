package com.example.drones;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
public class DronesController {


    @Autowired
    private DroneManager droneDataManager;
    Gson gsonBuilder = new GsonBuilder().create();
    @PostMapping
    public ResponseEntity<?> registerDrone(@RequestBody Drone drone) {
        ResponseDTO loadedDronesRes = droneDataManager.registerDrone(drone);
        droneDataManager.logBatteryCapacity();
        return ResponseEntity.ok(loadedDronesRes);
    }

    @PutMapping("/{serialNumber}/medication")
    public ResponseEntity<?> addMedicationsToDrone(@PathVariable String serialNumber,
                                                   @RequestBody Medication medications) {
        ResponseDTO loadedDronesRes = droneDataManager.addMedicationsToDrone(serialNumber, medications);
        String responseJson = gsonBuilder.toJson(loadedDronesRes);
        return ResponseEntity.ok(responseJson);
    }

    @Scheduled(fixedRate = 5000)
    public void logBatteryCapacity() {
        droneDataManager.logBatteryCapacity();
    }

    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<?> getLoadedMedications(@PathVariable String serialNumber) {
        List<Medication> medications = droneDataManager.getLoadedMedications(serialNumber);
        String responseJson = gsonBuilder.toJson(medications);
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        List<Drone> availableDrones = droneDataManager.getAvailableDrones();
        return ResponseEntity.ok(availableDrones);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Drone>> getAllDrones() {
        List<Drone> availableDrones = droneDataManager.getAllDrones();
        return ResponseEntity.ok(availableDrones);
    }

    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<String> getDroneBatteryLevel(@PathVariable String serialNumber) {
        ResponseDTO batteryLevel = droneDataManager.getDroneBatteryLevel(serialNumber);
        String responseJson = gsonBuilder.toJson(batteryLevel);
        return ResponseEntity.ok(responseJson);
    }
}
