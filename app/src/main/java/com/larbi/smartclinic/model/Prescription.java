package com.larbi.smartclinic.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document
public class Prescription {
   @Id
    private String id;
    
    @NotNull (message = "Patient name required")
    @Column(nullable=false)
    @Size(min = 3, max = 100)
    private String patientName;
    
    @NotNull (message = "Appointment Id required")
    @Column(nullable=false)
    private Long appointmentId;
    
    @NotNull (message = "Medication name required")
    @Column(nullable=false)
    @Size(min = 3, max = 100)
    private String medication;

    @NotNull(message = "Dosage required")
    @Column(nullable=false)
    @Size(min = 3, max = 100)
    private String dosage;

    @NotNull (message = "Dosage required")
    @Column(nullable=false)
    @Size(min = 3, max = 100)   
    private String frequency;   

    @Size(max = 255)
    private String instructions;
    
    @Size(max = 200)
    private String doctorNotes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getDoctorNotes() {
		return doctorNotes;
	}

	public void setDoctorNotes(String doctorNotes) {
		this.doctorNotes = doctorNotes;
	}

}
