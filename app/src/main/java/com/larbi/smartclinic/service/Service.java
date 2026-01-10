package com.larbi.smartclinic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.larbi.smartclinic.model.Admin;
import com.larbi.smartclinic.model.Appointment;
import com.larbi.smartclinic.model.Doctor;
import com.larbi.smartclinic.model.Login;
import com.larbi.smartclinic.model.Patient;
import com.larbi.smartclinic.repository.mysql.AdminRepository;
import com.larbi.smartclinic.repository.mysql.DoctorRepository;
import com.larbi.smartclinic.repository.mysql.PatientRepository;

@org.springframework.stereotype.Service
public class Service {
	
	private final TokenService tokenService;
	private final AdminRepository adminRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	private final DoctorService doctorService;
	private final PatientService patientService;
	
	public Service(TokenService tokenService, AdminRepository adminRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, DoctorService doctorService, PatientService patientService) {
		this.tokenService = tokenService;
		this.adminRepository = adminRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.doctorService = doctorService;
		this.patientService = patientService;		
	}

	public ResponseEntity<Map<String, String>> validateToken(String token, String user) {
		ResponseEntity<Map<String, String>> retval;
		try {
			if (!tokenService.validateToken(token, user)) {
				retval = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid or expired token"));
			} else {
				retval = ResponseEntity.ok(Map.of("message", "Token is valid"));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to validate token"));
		}
		return retval;
	}

	public ResponseEntity<Map<String, String>> validateAdmin(Admin receivedAdmin) {
		ResponseEntity<Map<String, String>> retval;
		try {
			Admin admin = adminRepository.findByUsername(receivedAdmin.getUsername());
			if (admin == null || !admin.getPassword().equals(receivedAdmin.getPassword())) {
				retval = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid username or password"));
			} else {
				String token = tokenService.generateToken(admin.getUsername(), "admin");
				retval = ResponseEntity.ok(Map.of("token", token));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to validate admin"));
		}
		return retval;
	}

	public Map<String, Object> filterDoctor(String name, String specialty, String time) {
		Map<String, Object> response = new HashMap<>();
		try {
			response = doctorService.filterDoctorsByNameSpecilityandTime(name, specialty, time);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			response.put("message", "Failed to filter doctors");
		}
		return response;
	}

	public int validateAppointment(Appointment appointment) {
		int retval = 0;
		try {
			Optional<Doctor> doctorOpt = doctorRepository.findById(appointment.getDoctor().getId());
			if (doctorOpt.isEmpty()) {
				retval = -1; // Doctor doesn't exist
			} else {
				Doctor doctor = doctorOpt.get();
				List<String> availableSlots = doctorService.getDoctorAvailability(doctor.getId(), appointment.getAppointmentTime().toLocalDate());
				if (availableSlots.contains(appointment.getAppointmentTime().toString())) {
					retval = 1; // Time is valid
				} else {
					retval = 0; // Time is unavailable
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			retval = 0; // Treat exceptions as unavailable time
		}
		return retval;
	}

	public boolean validatePatient(Patient patient) {
		boolean retval = false;
		try {
			Patient existingPatient = patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone());
			retval = existingPatient == null; // true if patient does not exist, false if patient exists
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			retval = false; // Treat exceptions as patient already exists
		}
		return retval;
	}

	public ResponseEntity<Map<String, String>> validatePatientLogin(Login login) {
		ResponseEntity<Map<String, String>> retval;
		try {
			Patient patient = patientRepository.findByEmail(login.getIdentifier());
			if (patient == null || !patient.getPassword().equals(login.getPassword())) {
				retval = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password"));
			} else {
				String token = tokenService.generateToken(patient.getEmail(), "patient");
				retval = ResponseEntity.ok(Map.of("token", token));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to validate patient login"));
		}
		return retval;
	}

	public ResponseEntity<Map<String, Object>> filterPatient(String condition, String name, String token) {
		ResponseEntity<Map<String, Object>> retval;
		try {
			String email = tokenService.extractIdentifier(token);
			Patient patient = patientRepository.findByEmail(email);
			if (patient == null) {
				retval = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Patient not found"));
			} else {
				Long patientId = patient.getId();
				if (condition != null && !condition.isEmpty() && name != null && !name.isEmpty()) {
					retval = patientService.filterByDoctorAndCondition(condition, name, patientId);
				} else if (condition != null && !condition.isEmpty()) {
					retval = patientService.filterByCondition(condition, patientId);
				} else if (name != null && !name.isEmpty()) {
					retval = patientService.filterByDoctor(name, patientId);
				} else {
					retval = ResponseEntity.badRequest().body(Map.of("message", "No filter criteria provided"));
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to filter patient appointments"));
		}
		return retval;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public AdminRepository getAdminRepository() {
		return adminRepository;
	}

	public DoctorRepository getDoctorRepository() {
		return doctorRepository;
	}

	public PatientRepository getPatientRepository() {
		return patientRepository;
	}

	public DoctorService getDoctorService() {
		return doctorService;
	}

	public PatientService getPatientService() {
		return patientService;
	}
	
	
}
