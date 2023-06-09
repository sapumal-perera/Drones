package com.example.drones.services;

import com.example.drones.models.Medication;

import java.util.List;

public interface MedicationService {

     List<Medication> getAllMedications();
     List<Medication> getMedicationByDroneSerialNumber(String id);
     Medication createMedication(Medication medication);
     Medication updateMedication(Long id, Medication medicationDetails);

     void deleteMedication(Long id);

}
