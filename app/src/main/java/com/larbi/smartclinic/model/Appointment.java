package com.larbi.smartclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "doctor_id", nullable = false)
//	@JsonBackReference // to prevent infinite recursion during JSON serialization
//    private Doctor doctor;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "patient_id") // Nullable for "blocked" slots
//	@JsonBackReference // to prevent infinite recursion during JSON serialization
//    private Patient patient;

    @Column(name = "patient_id", nullable = true) // Allow null for doctor blocked slots
    private Long patientId;

	@NotNull(message = "Doctor ID is required")
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

	@NotNull(message = "Appointment date is required")
    @Column(name = "appt_date", nullable = false)
	@Future(message = "Appointment date must be in the future.")
    private LocalDate appointmentDate;

	@NotNull(message = "Start time is required")
	@Column(name = "start_time", nullable = false)
	private LocalTime startTime;

	@NotNull(message = "End time is required")
	@Column(name = "end_time", nullable = false)
	private LocalTime endTime;

	@Column(name = "is_dr_blocked_slot")
    private Boolean isDrBlockedSlot = false;

    @Column(name = "reason")
    @Size(min = 3, max = 255)
    private String reason;

    @Column(name = "status")  
    @Size(min = 3, max = 50)
    private String status;

    @ElementCollection
    private List<String> statusList;

	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsDrBlockedSlot() {
		return isDrBlockedSlot;
	}
	public void setIsDrBlockedSlot(Boolean isDrBlockedSlot) {
		this.isDrBlockedSlot = isDrBlockedSlot;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
		public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	
}
