package com.larbi.smartclinic.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@DiscriminatorValue("DOCTOR")
@PrimaryKeyJoinColumn(name = "person_id") // Maps Doctor's ID to Person's ID
public class Doctor extends Person {

    // mappedBy refers to the "doctor_id" field in the Appointment entity
    @OneToMany(mappedBy = "doctorId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference // To handle bidirectional JSON serialization
    private List<Appointment> appointments = new ArrayList<>();

	@NotNull(message = "Specialties  required")
    @Column(name = "specialty", nullable = false)
    @Size(min = 3, max = 255)
    private String specialty;

    @Column(name = "license_number")
    @Size(min = 3, max = 50)
    private String licenseNumber;

    @Column(name = "clinic_address")
    @Size(min = 3, max = 255)
    private String clinicAddress;
    
    @ElementCollection
    private List<String> availableTimes;
    
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getClinicAddress() {
		return clinicAddress;
	}
	public void setClinicAddress(String clinicAddress) {
		this.clinicAddress = clinicAddress;
	}
    
}
