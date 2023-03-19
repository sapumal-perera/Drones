package com.example.drones;

import java.util.List;
import java.util.Optional;

public interface MedicationService {

     List<Medication> getAllMedications();
     Medication getMedicationById(Long id);
     Medication createMedication(Medication medication);
     Medication updateMedication(Long id, Medication medicationDetails);

     void deleteMedication(Long id);

}
