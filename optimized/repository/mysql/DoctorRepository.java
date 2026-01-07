package com.larbi.smartclinic.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.larbi.smartclinic.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
