package com.larbi.smartclinic.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends Person {

    // mappedBy refers to the "patient_id" field in the Appointment entity
    @OneToMany(mappedBy = "patient_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference // To handle bidirectional JSON serialization
    private List<Appointment> appointments = new ArrayList<>();

    @Column(name = "insurance_provider")
    private String insuranceProvider;
    @Column(name = "insurance_policy_number")
    private String insurancePolicyNumber;
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;
    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;
    @Column(name = "medical_history")   
    private String medicalHistory;
    @Column(name = "allergies")
    private String allergies;
    @Column(name = "current_medications")
    private String currentMedications;
    @Column(name = "preferred_pharmacy")
    private String preferredPharmacy;
    
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
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}
	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	public String getCurrentMedications() {
		return currentMedications;
	}
	public void setCurrentMedications(String currentMedications) {
		this.currentMedications = currentMedications;
	}
	public String getPreferredPharmacy() {
		return preferredPharmacy;
	}
	public void setPreferredPharmacy(String preferredPharmacy) {
		this.preferredPharmacy = preferredPharmacy;
	} 
}
