package com.larbi.smartclinic.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.larbi.smartclinic.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	@Query("""
	        SELECT COUNT(a) > 0 FROM Appointment a 
	        WHERE a.doctor.id = :doctorId 
	        AND (:startTime < a.endTime AND :endTime > a.startTime)
	    """)
	    boolean isDoctorBooked(
	        @Param("doctorId") Long doctorId, 
	        @Param("startTime") LocalDateTime startTime, 
	        @Param("endTime") LocalDateTime endTime
	    );
}
