package com.larbi.smartclinic.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.larbi.smartclinic.model.Appointment;
import com.larbi.smartclinic.model.AppointmentDTO;
import com.larbi.smartclinic.model.Patient;
import com.larbi.smartclinic.repository.mysql.AppointmentRepository;
import com.larbi.smartclinic.repository.mysql.PatientRepository;


@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private TokenService tokenService;
	
    public int createPatient(Patient patient) {
        int retval = 0;
        try {
            this.patientRepository.save(patient);
            retval = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return retval;
    }

    public ResponseEntity<Map<String, Object>> getPatientAppointment(Long id, String token) {
        ResponseEntity<Map<String, Object>> retval = null;
        try {
            String email = tokenService.extractIdentifier(token);
            Patient patient = patientRepository.findByEmail(email);
            if (patient == null || !patient.getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized access"));
            }
            List<Appointment> appointments = appointmentRepository.findByPatientId(id);
            
            List<AppointmentDTO> appointmentDTOs = appointments.stream()
                    .map(appt -> new AppointmentDTO(appt.getId(), appt.getDoctor().getId(), appt.getDoctor().getName(), id, patient.getName(), email, patient.getPhone(), patient.getAddress(),  appt.getAppointmentTime(), appt.getStatus()))
                    .collect(Collectors.toList());
            retval = ResponseEntity.ok(Map.of("appointments", appointmentDTOs));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to retrieve appointments"));
        }
        return retval;
    }

    public ResponseEntity<Map<String, Object>> filterByCondition(String condition, Long id) {
        ResponseEntity<Map<String, Object>> retval = null;
        try {
            int status = condition.equalsIgnoreCase("past") ? 1 : 0;
            List<Appointment> appointments = appointmentRepository.findByPatient_IdAndStatusOrderByAppointmentTimeAsc(id, status);
            List<AppointmentDTO> appointmentDTOs = appointments.stream()
                    .map(appt -> new AppointmentDTO(appt.getId(), appt.getDoctor().getId(), appt.getDoctor().getName(), appt.getPatient().getId(), appt.getPatient().getName(), appt.getPatient().getEmail(), appt.getPatient().getPhone(), appt.getPatient().getAddress(),  appt.getAppointmentTime(), appt.getStatus()))
                    .collect(Collectors.toList());
            retval = ResponseEntity.ok(Map.of("appointments", appointmentDTOs));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to filter appointments"));
        }
        return retval;
    }

    public ResponseEntity<Map<String, Object>> filterByDoctor(String name, Long patientId) {
        ResponseEntity<Map<String, Object>> retval = null;
        try {
            List<Appointment> appointments = appointmentRepository.filterByDoctorNameAndPatientId(name, patientId);
            List<AppointmentDTO> appointmentDTOs = appointments.stream()
                    .map(appt -> new AppointmentDTO(appt.getId(), appt.getDoctor().getId(), appt.getDoctor().getName(), appt.getPatient().getId(), appt.getPatient().getName(), appt.getPatient().getEmail(), appt.getPatient().getPhone(), appt.getPatient().getAddress(),  appt.getAppointmentTime(), appt.getStatus()))
                    .collect(Collectors.toList());
            retval = ResponseEntity.ok(Map.of("appointments", appointmentDTOs));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to filter appointments by doctor"));
        }
        return retval;
    }

    public ResponseEntity<Map<String, Object>> filterByDoctorAndCondition(String condition, String name, long patientId) {
        ResponseEntity<Map<String, Object>> retval = null;
        try {
            int status = condition.equalsIgnoreCase("past") ? 1 : 0;
            List<Appointment> appointments = appointmentRepository.filterByDoctorNameAndPatientIdAndStatus(name, patientId, status);
            List<AppointmentDTO> appointmentDTOs = 
            		appointments.stream()
                    .map(appt -> new AppointmentDTO(appt.getId(), appt.getDoctor().getId(), appt.getDoctor().getName(), appt.getPatient().getId(), appt.getPatient().getName(), appt.getPatient().getEmail(), appt.getPatient().getPhone(), appt.getPatient().getAddress(),  appt.getAppointmentTime(), appt.getStatus()))
                    .collect(Collectors.toList());
            retval = ResponseEntity.ok(Map.of("appointments", appointmentDTOs));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to filter appointments by doctor and condition"));
        }
        return retval;
    }

    public ResponseEntity<Map<String, Object>> getPatientDetails(String token) {
        ResponseEntity<Map<String, Object>> retval = null;
        try {
            String email = tokenService.extractIdentifier(token);
            Patient patient = patientRepository.findByEmail(email);
            if (patient == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Patient not found"));
            }
            Map<String, Object> patientDetails = Map.of(
                    "id", patient.getId(),
                    "name", patient.getName(),
                    "email", patient.getEmail(),
                    "phone", patient.getPhone()
            );
            retval = ResponseEntity.ok(Map.of("patient", patientDetails));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to retrieve patient details"));
        }
        return retval;
    }

}
