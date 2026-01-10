package com.larbi.smartclinic.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.larbi.smartclinic.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	@Query("SELECT p FROM Patient p WHERE LOWER(p.email) = LOWER(:email)")
	Patient findByEmail(String email);
	
	@Query("SELECT p FROM Patient p WHERE LOWER(p.email) = LOWER(:email) OR LOWER(p.phone) = LOWER(:phone)")
	Patient findByEmailOrPhone(String email, String phone);
	
}
