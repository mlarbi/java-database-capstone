package com.larbi.smartclinic.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.larbi.smartclinic.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
