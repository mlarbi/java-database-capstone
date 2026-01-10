package com.larbi.smartclinic.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.larbi.smartclinic.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	Doctor findByEmail(String email);
	
	@Query ("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Doctor> findByNameLike(String name);
	
	@Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(d.specialty) LIKE LOWER(CONCAT('%', :specialty, '%'))")
	List<Doctor> findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(String name, String specialty);
	
	@Query("SELECT d FROM Doctor d WHERE LOWER(d.specialty) LIKE LOWER(CONCAT('%', :specialty, '%'))")
	List<Doctor> findBySpecialtyIgnoreCase(String specialty);

	@Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Doctor> findByNameContainingIgnoreCase(String name);
	
}
