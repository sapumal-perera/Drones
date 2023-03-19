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

    /**
     * addMedication
     * @param medication
     * @return
     */
    @PostMapping("/new")
    public ResponseEntity<Medication> addMedication(@RequestBody Medication medication) {
        Medication newMedication = medicationService.createMedication(medication);
        return ResponseEntity.ok(newMedication);
    }

    /**
     * getMedication
     * @param serialNum
     * @return
     */
    @GetMapping("/{serialNum}")
    public ResponseEntity<List<Medication>> getMedication(@PathVariable("serialNum") String serialNum) {
        List<Medication> medication = medicationService.getMedicationByDroneSerialNumber(serialNum);
        return ResponseEntity.ok(medication);
    }

    /**
     * updateMedication
     * @param id
     * @param medication
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable("id") Long id, @RequestBody Medication medication) {
        Medication updatedMedication = medicationService.updateMedication(id, medication);
        return ResponseEntity.ok(updatedMedication);
    }

    /**
     * deleteMedication
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedication(@PathVariable("id") Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.ok().build();
    }
}
