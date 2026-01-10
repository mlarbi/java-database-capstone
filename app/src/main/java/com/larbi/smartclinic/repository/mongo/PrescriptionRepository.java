package com.larbi.smartclinic.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.larbi.smartclinic.model.Prescription;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
	// Spring generates: { "appointmentId" : ?0 }
	List<Prescription> findByAppointmentId(Long appointmentId);
}
