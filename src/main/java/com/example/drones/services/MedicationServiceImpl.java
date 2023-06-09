package com.example.drones.services;

import com.example.drones.repositories.MedicationRepository;
import com.example.drones.models.Medication;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    @Override
    public List<Medication> getMedicationByDroneSerialNumber(String serialNum) {
        return medicationRepository.findByDroneSerialNumber(serialNum);
    }


    @Override
    public Medication createMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    @Override
    public Medication updateMedication(Long id, Medication medicationDetails) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medication id: " + id));
        medication.setName(medicationDetails.getName());
        medication.setWeight(medicationDetails.getWeight());
        medication.setCode(medicationDetails.getCode());
        medication.setImage(medicationDetails.getImage());
        return medicationRepository.save(medication);
    }

    @Override
    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}
