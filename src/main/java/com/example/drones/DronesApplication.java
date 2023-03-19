package com.example.drones;

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
    private DroneService droneService;

    @PostMapping
    public ResponseEntity<?> registerDrone(@RequestBody Drone drone) {
        droneService.registerDrone(drone);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{serialNumber}/medications")
    public ResponseEntity<?> loadDrone(@PathVariable String serialNumber,
                                       @RequestBody List<Medication> medications) {

        String loadedDrones = droneService.loadDrone(serialNumber, medications);
        return ResponseEntity.ok(loadedDrones);
    }

    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<List<Medication>> getLoadedMedications(@PathVariable String serialNumber) {
        List<Medication> medications = droneService.getLoadedMedications(serialNumber);
        return ResponseEntity.ok(medications);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        List<Drone> availableDrones = droneService.getAvailableDrones();
        return ResponseEntity.ok(availableDrones);
    }

    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable String serialNumber) {
        int batteryLevel = droneService.getDroneBatteryLevel(serialNumber);
        return ResponseEntity.ok(batteryLevel);
    }
}
