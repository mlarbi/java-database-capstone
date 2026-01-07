package com.larbi.smartclinic.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.larbi.smartclinic.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
