package com.larbi.smartclinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@DiscriminatorValue("PATIENT")
@PrimaryKeyJoinColumn(name = "person_id") // Maps Patient's ID to Person's ID
public class Patient extends Person {

    // mappedBy refers to the "patientid" PROPERTY in the Appointment entity
    @OneToMany(mappedBy = "patientId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference // To handle bidirectional JSON serialization
    private List<Appointment> appointments = new ArrayList<>();

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    @Past (message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    
    @Column(name = "insurance_provider")
    private String insuranceProvider;
    
    @Column(name = "insurance_policy_number")
    @Size(min = 3, max = 100)
    private String insurancePolicyNumber;
    
    @Column(name = "emergency_contact_name")
    @Size(min = 3, max = 100)
   private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone")
    @Pattern(regexp = "\\d{10}", message="Invalid phone number format")
    @Size(min = 10, max = 15)
    private String emergencyContactPhone;
    @Column(name = "medical_history")   
    private String medicalHistory;
    @Column(name = "allergies")
    private String allergies;
    @Column(name = "current_medications")
    private String currentMedications;
    
    @Column(name = "preferred_pharmacy")
    @Size(min = 3, max = 100)
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
