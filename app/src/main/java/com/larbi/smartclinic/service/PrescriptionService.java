package com.larbi.smartclinic.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.larbi.smartclinic.model.Prescription;
import com.larbi.smartclinic.repository.mongo.PrescriptionRepository;

@Service
public class PrescriptionService {
	
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
    public ResponseEntity<Map<String, String>> savePrescription(Prescription prescription) {
        ResponseEntity<Map<String, String>> retval;
        try {
            this.prescriptionRepository.save(prescription);
            retval = ResponseEntity.status(201).body(Map.of("message", "Prescription saved"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to save prescription"));
        }
        return retval;
    }

    public ResponseEntity<Map<String, Object>> getPrescription(Long appointmentId) {
        ResponseEntity<Map<String, Object>> retval;
        try {
            List<Prescription> prescriptions = this.prescriptionRepository.findByAppointmentId(appointmentId);
            
            retval = ResponseEntity.ok(Map.of("prescription", prescriptions));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to retrieve prescription"));
        }
        return retval;
    }
}
