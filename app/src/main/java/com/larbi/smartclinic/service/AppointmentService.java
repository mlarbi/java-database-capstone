package com.larbi.smartclinic.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.larbi.smartclinic.model.Appointment;
import com.larbi.smartclinic.repository.mysql.AppointmentRepository;
import com.larbi.smartclinic.repository.mysql.DoctorRepository;
import com.larbi.smartclinic.repository.mysql.PatientRepository;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private PatientRepository patientRepsotory;
	
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private TokenService tokenService;

	public int  bookAppointment(Appointment appointment) {
		int retval = 0;
		try {
			this.appointmentRepository.save(appointment);
			retval = 1;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return retval;
	}
	
	public ResponseEntity<Map<String, String>>  updateAppointment(Appointment appointment){
		ResponseEntity<Map<String, String>> retval;
		try {
			Optional<Appointment> apptmt = this.appointmentRepository.findById(appointment.getId());
			if (apptmt.isPresent()) {
				Appointment existingAppointment = apptmt.get();
				existingAppointment.setAppointmentTime(appointment.getAppointmentTime());
				existingAppointment.setStatus(appointment.getStatus());
				this.appointmentRepository.save(existingAppointment);
			}
			retval = ResponseEntity.ok(Map.of("message", "Appointment updated successfully"));
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			retval = ResponseEntity.status(500).body(Map.of("message", "Failed to update appointment"));
		}
		return retval;
	}
	
	void validateAppointment(Appointment appointment){
		// check if doctor is available at the given time
		// check if patient has another appointment at the same time
	}

	public ResponseEntity<Map<String, String>>  cancelAppointment(Long id, String token){
		ResponseEntity<Map<String, String>> retval = null;

		try{

			if (!tokenService.validateToken(token, "patient")) {
				retval =  ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));
			}

			if (retval == null) {
				Optional<Appointment> appointment = appointmentRepository.findById(id);
				if (appointment.isEmpty()) {
					retval = ResponseEntity.status(404).body(Map.of("message", "Appointment not found"));
				}else{
					Appointment appt = appointment.get();
					appointmentRepository.delete(appt);
					retval = ResponseEntity.ok(Map.of("message", "Appointment cancelled successfully"));
				}			
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			retval = ResponseEntity.status(500).body(Map.of("message", "Failed to cancel appointment"));
		}

		return retval;
	}

	public Map<String, Object> getAppointments(Long doctorId, LocalDate date, String patientName, String token) {
		Map<String, Object> response = new HashMap<>();

		// Validate token and ensure it's for a doctor
		if (!tokenService.validateToken(token, "doctor")) {
			response.put("message", "Unauthorized");
			return response;
		}

		try {
			List<Appointment> appointments;
			if (patientName != null && !patientName.isEmpty()) {
				appointments = appointmentRepository.findByDoctorIdAndPatient_NameContainingIgnoreCaseAndAppointmentTimeBetween(
						doctorId, patientName, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
			} else {
				appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
						doctorId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
			}
			response.put("appointments", appointments);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			response.put("message", "Failed to retrieve appointments");
		}

		return response;
	}
	
	public AppointmentRepository getAppointmentRepository() {
		return appointmentRepository;
	}

	public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	public PatientRepository getPatientRepsotory() {
		return patientRepsotory;
	}

	public void setPatientRepsotory(PatientRepository patientRepsotory) {
		this.patientRepsotory = patientRepsotory;
	}

	public DoctorRepository getDoctorRepository() {
		return doctorRepository;
	}

	public void setDoctorRepository(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
	
	
}
