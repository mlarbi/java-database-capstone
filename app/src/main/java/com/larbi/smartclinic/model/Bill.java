package com.larbi.smartclinic.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 * The Bill class represents the billing information for a patient in the smart clinic system.
 * It contains details about the patient's insurance, billing address, amount due, and due date.
 */

@Entity
@Table(name = "billing_info")   
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billing_id")
    private Long billingId;

	@NotNull(message = "Patient ID is required")
    @Column(name = "patient_id", nullable = false)
    private Long patientId;
	
    @Column(name = "insurance_provider")
    @Size(min = 3, max = 255)
    private String insuranceProvider;

    @Column(name = "insurance_policy_number")
    @Size(min = 3, max = 50)
    private String insurancePolicyNumber;
    
    @Column(name = "billing_address")
    @Size(min = 3, max = 255)
    private String billingAddress;

	@NotNull(message = "Billing amount is required")
    @Column(name = "billing_amount", nullable = false)
	@Digits(integer = 8, fraction = 2, message = "Amount must have up to 8 integer digits and 2 decimals")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
	private BigDecimal billingAmount;

	@NotNull(message = "Due date is required")
	@FutureOrPresent
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate; 
	
	public Long getBillingId() {
		return billingId;
	}
	public void setBillingId(Long billingId) {
		this.billingId = billingId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getInsuranceProvider() {
		return insuranceProvider;
	}
	public void setInsuranceProvider(String insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
	}
	public String getInsurancePolicyNumber() {
		return insurancePolicyNumber;
	}
	public void setInsurancePolicyNumber(String insurancePolicyNumber) {
		this.insurancePolicyNumber = insurancePolicyNumber;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public BigDecimal getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(BigDecimal billingAmount) {
		this.billingAmount = billingAmount;
	}
}
