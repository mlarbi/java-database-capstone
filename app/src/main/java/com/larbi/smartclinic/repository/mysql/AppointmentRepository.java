package com.larbi.smartclinic.repository.mysql;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.larbi.smartclinic.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
