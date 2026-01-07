package com.larbi.smartclinic.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull (message = "Doctor required")
	@JsonBackReference // to prevent infinite recursion during JSON serialization
    private Doctor doctor;

    @ManyToOne
    @NotNull (message = "Patient required")
	@JsonBackReference // to prevent infinite recursion during JSON serialization
    private Patient patient;


	@NotNull(message = "Appointment date and time required")
    @Column(nullable = false)
	@Future(message = "Appointment date and time must be in the future.")
    private LocalDateTime appointmentTime;
    
	@NotNull(message = "Status required")
    @Column(nullable = false)
    private int status; // 0 = Scheduled, 1 = Completed, 2 = Doctor_Blocked_Slot

	@Column(name = "is_dr_blocked_slot")
    private Boolean isDrBlockedSlot = false;

    @Column(name = "reason")
    @Size(min = 3, max = 255)
    private String reason;

    @ElementCollection
    private List<String> statusList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

}
