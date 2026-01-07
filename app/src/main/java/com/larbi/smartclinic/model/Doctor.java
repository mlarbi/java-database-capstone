package com.larbi.smartclinic.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "doctor")
public class Doctor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // mappedBy refers to the "doctor_id" field in the Appointment entity
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference // To handle bidirectional JSON serialization
    private List<Appointment> appointments = new ArrayList<>();

	@NotNull(message = "Name required")
	@Column(nullable=false)
    @Size(min = 3, max = 100)
    private String name;

    @Email (message = "Valid email required")
	@Column(unique = true, nullable=false)
    @NotNull
    private String email;

    @Size(min = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
	
	@NotNull(message = "Phone number required")
	@Size(min = 3, max = 255)
    @Pattern(regexp = "\\d{10}", message = "10-digit phone number required")
    private String phone;

 	@NotNull(message = "Specialties  required - multiple specialties separated by commas.")
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	public List<String> getAvailableTimes() {
		return availableTimes;
	}

	public void setAvailableTimes(List<String> availableTimes) {
		this.availableTimes = availableTimes;
	}
    
}
