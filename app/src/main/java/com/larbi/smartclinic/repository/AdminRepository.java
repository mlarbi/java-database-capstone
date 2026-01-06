package com.larbi.smartclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.larbi.smartclinic.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
