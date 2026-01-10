package com.larbi.smartclinic.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.larbi.smartclinic.model.Doctor;
import com.larbi.smartclinic.model.Login;
import com.larbi.smartclinic.model.Patient;
import com.larbi.smartclinic.service.AppointmentService;
import com.larbi.smartclinic.service.DoctorService;
import com.larbi.smartclinic.service.PatientService;
import com.larbi.smartclinic.service.Service;

@RestController
@RequestMapping("/patient")
public class PatientController {
	   @Autowired
	    private Service service;
	    
	    @Autowired
	    private PatientService patientService;

		public Service getService() {
			return service;
		}

		public void setService(Service service) {
			this.service = service;
		}

		public PatientService getPatientService() {
			return patientService;
		}

		public void setPatientService(PatientService patientService) {
			this.patientService = patientService;
		}

//		1. Get Patient Details
//		Method: @GetMapping("/{token}")
//		Parameters
//		token: The authentication token for the patient.
//		Process: Validates the token using service.validateToken(). If the token is valid, fetches the patient details using patientService.getPatientDetails().
//		Response
//		If token is invalid: Returns an error message with appropriate HTTP status.
//		If successful: Returns the patient's details.
		
	public PatientController(Service service, PatientService patientService) {
		this.service = service;
		this.patientService = patientService;
	}

		@GetMapping("/{token}")	
		public ResponseEntity<Map<String, Object>> getPatientDetails(@PathVariable String token) {
	        ResponseEntity<Map<String, Object>> retval;
	        try {
	        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "patient");
	            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
	                retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
	            } else {
	                retval = patientService.getPatientDetails(token);
	            }
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to retrieve patient details"));
	        }
	        return retval;
	    }		
		
//		2. Create a New Patient
//		Method: @PostMapping()
//		Parameters
//		patient: The patient details to be created
//		Process
//		Validates if the patient already exists by checking email or phone number
//		If the validation passes, calls patientService.createPatient() to create a new patient record
//		Response
//		Success: "Signup successful"
//		Conflict: "Patient with email id or phone no already exist"
//		Internal error: "Internal server error"
			
		public ResponseEntity<Map<String, Object>> createPatient(@RequestBody Patient patient) {
	        ResponseEntity<Map<String, Object>> retval = null;
	        try {
            	int saveResult = patientService.createPatient(patient) ;
            	if (saveResult == -1) {
            		retval = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Patient with email id or phone no already exist"));
            	} else if (saveResult == 0) {
                    retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to create patient"));       		
            	} else if (saveResult == 1) {
            		retval = ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Signup successful"));
            	}
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	            retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Internal server error"));
	        }
	        return retval;
	    }	
			
//		3. Patient Login
//		Method: @PostMapping("/login")
//		Parameters
//		login: The login credentials (email, password)
//		Process: Calls service.validatePatientLogin() to validate the patient's login credentials
//		Response: Returns the result of the login validation (success or failure)
		
		
		@PostMapping("/login")
		public ResponseEntity<Map<String, String>> validatePatientLogin(@RequestBody Login login) {
	        ResponseEntity<Map<String, String>> retval;
	        try {
	            retval = service.validatePatientLogin(login);
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to validate patient login"));
	        }
	        return retval;
	    }


//		4. Get Patient Appointments
//		Method: @GetMapping("/{id}/{token}")
//		Parameters
//		id: The ID of the patient
//		token: The authentication token for the patient
//		Process
//		Validates the token using service.validateToken()
//		If valid, fetches the patient's appointments using patientService.getPatientAppointment()
//		Response
//		Returns the list of patient appointments or an error message
		@GetMapping("/{id}/{token}")
		public ResponseEntity<Map<String, Object>> getPatientAppointment(@PathVariable Long id, @PathVariable String token) {
	        ResponseEntity<Map<String, Object>> retval;
	        try {
	        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "patient");
	            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
	                retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
	            } else {
	                retval = patientService.getPatientAppointment(id, token);
	            }
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to retrieve appointments"));
	        }
	        return retval;
	    }	

//		5. Filter Patient Appointments
//		Method: @GetMapping("/filter/{condition}/{name}/{token}")
//		Parameters
//		condition: The condition to filter appointments (for example, "upcoming", "past")
//		name: The name or description for filtering (for example, doctor name, appointment type)
//		token: The authentication token for the patient
//		Process
//		Validates the token using service.validateToken()
//		If valid, calls service.filterPatient() to filter the patient's appointments based on the given criteria
//		Response
//		Returns the filtered appointments or an error message
		@GetMapping("/filter/{condition}/{name}/{token}")
		public ResponseEntity<Map<String, Object>> filterPatient(@PathVariable String condition, @PathVariable String name, @PathVariable String token) {
	        ResponseEntity<Map<String, Object>> retval;
	        try {
	        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "patient");
	            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
	                 retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
	            } else {
	                retval = service.filterPatient(condition, name, token);
	            }
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to filter patient appointments"));
	        }
	        return retval;
	    }

}
