package com.larbi.smartclinic.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.larbi.smartclinic.model.Prescription;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {

}
