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
import jakarta.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends Person {

    // mappedBy refers to the "doctor_id" field in the Appointment entity
    @OneToMany(mappedBy = "doctor_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference // To handle bidirectional JSON serialization
    private List<Appointment> appointments = new ArrayList<>();

	@NotNull(message = "Specialties  required")
    @Column(name = "specialty", nullable = false)
    private String specialty;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "clinic_address")
    private String clinicAddress;
    
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
