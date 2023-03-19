package com.example.drones.controllers;

import com.example.drones.services.MedicationService;
import com.example.drones.models.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @PostMapping
    public ResponseEntity<Medication> addMedication(@RequestBody Medication medication) {
        Medication newMedication = medicationService.createMedication(medication);
        return ResponseEntity.ok(newMedication);
    }

    @GetMapping("/{serialNum}")
    public ResponseEntity<List<Medication>> getMedication(@PathVariable("serialNum") String serialNum) {
        List<Medication> medication = medicationService.getMedicationByDroneSerialNumber(serialNum);
        return ResponseEntity.ok(medication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable("id") Long id, @RequestBody Medication medication) {
        Medication updatedMedication = medicationService.updateMedication(id, medication);
        return ResponseEntity.ok(updatedMedication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedication(@PathVariable("id") Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.ok().build();
    }
}
