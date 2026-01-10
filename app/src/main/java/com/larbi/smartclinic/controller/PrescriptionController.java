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
import com.larbi.smartclinic.service.AppointmentService;
import com.larbi.smartclinic.service.DoctorService;
import com.larbi.smartclinic.service.PrescriptionService;
import com.larbi.smartclinic.service.Service;
import com.larbi.smartclinic.service.TokenService;

@RestController
@RequestMapping("${api.path}" + "prescription")
public class PrescriptionController {

	@Autowired
	private Service service;
	
	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private TokenService tokenService;

	PrescriptionController(Service service, PrescriptionService prescriptionService, TokenService tokenService) {
		this.service = service;
		this.prescriptionService = prescriptionService;
		this.tokenService = tokenService;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public PrescriptionService getPrescriptionService() {
		return prescriptionService;
	}

	public void setPrescriptionService(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}


//	1. Save Prescription
//	Method: @PostMapping("/{token}")
//	Parameters
//	token: The authentication token for the doctor
//	prescription: The prescription details to be saved (passed in the request body)
//	Process
//	The token is validated to ensure that the request is made by a doctor using service.validateToken()
//	If the token is valid, the prescription is saved using prescriptionService.savePrescription()
//	Response
//	If the token is invalid: Returns an error message with appropriate HTTP status
//	If the prescription is successfully saved: Returns a success message

	public ResponseEntity<Map<String, Object>> savePrescription(@RequestBody Prescription prescription, @PathVariable String token) {
		ResponseEntity<Map<String, Object>> retval;
		try {
			if (!service.validateToken(token, "doctor")) {
				retval = ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));
			} else if (!service.validatePrescription(prescription)) {
				retval = ResponseEntity.badRequest().body(Map.of("message", "Invalid prescription data"));
			} else {
				retval = prescriptionService.savePrescription(prescription);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			retval = ResponseEntity.status(500).body(Map.of("message", "Failed to save prescription"));
		}
		return retval;
	}


//	2. Get Prescription by Appointment ID
//	Method: @GetMapping("/{appointmentId}/{token}")
//	Parameters
//	appointmentId: The ID of the appointment to retrieve the prescription for
//	token: The authentication token for the doctor
//	Process
//	The token is validated using service.validateToken() to ensure the request is from a valid doctor
//	If the token is valid, the prescription is retrieved for the given appointmentId using prescriptionService.getPrescription()
//	Response
//	If the token is invalid: Returns an error message with appropriate HTTP status
//	If the prescription is found, returns the prescription details
//	If no prescription is found, returns a message indicating no prescription exists for that appointment
//	Explanation of the ValidationFailed Class
//	The ValidationFailed class is a custom exception handler that handles validation errors in a Spring Boot application. It is annotated with @RestControllerAdvice, which makes it a global exception handler for REST controllers.
//
//	This class handles the MethodArgumentNotValidException which occurs when a validation fails during the binding of request parameters or request body fields to the method parameters in a controller. Typically, this happens when input data does not meet the constraints defined by annotations such as @NotNull, @Size, @Email, and so on, in the model class.

	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put("message", error.getDefaultMessage());
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@GetMapping("/{appointmentId}/{token}")
	public ResponseEntity<Map<String, Object>> getPrescription(@PathVariable Long appointmentId, @PathVariable String token) {
		ResponseEntity<Map<String, Object>> retval;
		try {
			if (!service.validateToken(token, "doctor")) {
				retval = ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));
			} else {
				retval = prescriptionService.getPrescription(appointmentId);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to retrieve prescription"));
		}
	}

//	Key Points
//	@RestControllerAdvice
//
//	This annotation is a combination of @ControllerAdvice and @ResponseBody, which makes it a global exception handler that can return the response directly as JSON (or any other format) in case of errors.
//	Exception Handler Method
//
//	The @ExceptionHandler(MethodArgumentNotValidException.class) annotation specifies that this method will handle exceptions of type MethodArgumentNotValidException. This exception is thrown when a validation error occurs on the request body (such as when data in a @RequestBody doesn't match the required constraints).
//	Handling Validation Errors
//
//	The method handleValidationException is invoked when a MethodArgumentNotValidException is thrown.
//	Inside the method, the exception object (ex) provides access to the binding result of the validation errors, which includes the field errors (for example, which fields failed validation).
//	Creating the Error Response
//
//	The FieldError object contains information about the specific field that failed validation and the corresponding error message.
//	We loop through all the field errors in the exception and map them to a Map<String, String>, where the key is "message" and the value is the actual error message associated with the field.
//	Returning the Response
//
//	After processing all validation errors, the method returns a ResponseEntity with an HTTP status of BAD_REQUEST (400) and a body containing the validation error messages.

}
