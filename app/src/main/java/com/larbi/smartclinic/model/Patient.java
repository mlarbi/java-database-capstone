package com.larbi.smartclinic.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patient")
public class Patient{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference // To handle bidirectional JSON serialization
    private List<Appointment> appointments = new ArrayList<>();

	@NotNull(message = "Name required")
	@Column(nullable=false)
    @Size(min = 3, max = 100)
    private String name;

    @NotNull(message = "Email is required") 
    @Size(min = 6, max = 100)
    @Email( message = "Please provide a valid email address")
    private String email;

	@NotNull(message = "Password is required")
    @Column(nullable=false)
    @Size(min = 3, max = 255)
    private String password;
    
    @NotNull(message="Phone number is required")
    @Column(name = "phone", nullable = false)
    @Pattern(regexp = "\\d{10}", message="Invalid phone number format")
    @Size(min = 10, max = 15)
    private String phone;
 	
	@NotNull(message = "Address required")
	@Column(name = "address", nullable = false)
	@Size(min = 3, max = 255)
	private String address;

	@Column(name = "date_of_birth")
	@Past(message = "Date of birth must be in the past")
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
   
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	} 
}
