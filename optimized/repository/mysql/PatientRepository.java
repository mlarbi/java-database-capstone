package com.larbi.smartclinic.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.larbi.smartclinic.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
