package com.example.drones;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/drones")
public class DronesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DronesApplication.class, args);
    }

    @Autowired
    private DroneManager droneDataManager;
    Gson gsonBuilder = new GsonBuilder().create();
    @PostMapping
    public ResponseEntity<?> registerDrone(@RequestBody Drone drone) {
        droneDataManager.registerDrone(drone);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{serialNumber}/medications")
    public ResponseEntity<?> loadDrone(@PathVariable String serialNumber,
                                       @RequestBody List<Medication> medications) {
        ResponseDTO loadedDronesRes = droneDataManager.loadDrone(serialNumber, medications);
        String responseJson = gsonBuilder.toJson(loadedDronesRes);
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<?> getLoadedMedications(@PathVariable String serialNumber) {
        ResponseDTO medications = droneDataManager.getLoadedMedications(serialNumber);
        String responseJson = gsonBuilder.toJson(medications);
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        List<Drone> availableDrones = droneDataManager.getAvailableDrones();
        return ResponseEntity.ok(availableDrones);
    }

    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<String> getDroneBatteryLevel(@PathVariable String serialNumber) {
        ResponseDTO batteryLevel = droneDataManager.getDroneBatteryLevel(serialNumber);
        String responseJson = gsonBuilder.toJson(batteryLevel);
        return ResponseEntity.ok(responseJson);
    }
}
