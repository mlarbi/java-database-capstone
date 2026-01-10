package com.larbi.smartclinic.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.larbi.smartclinic.model.Appointment;
import com.larbi.smartclinic.service.AppointmentService;
import com.larbi.smartclinic.service.Service;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private Service service;
    
    @Autowired
    private AppointmentService appointmentService;

    public AppointmentController(Service service, AppointmentService appointmentService) {
        this.service = service;
        this.appointmentService = appointmentService;
    }

    public ResponseEntity<Map<String, Object>> getAppointments(String date, String patientName, String token) {
        ResponseEntity<Map<String, Object>> retval;
        try {
        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "doctor");
            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
                retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
             } else {
                retval = appointmentService.getAppointment(date, patientName);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to retrieve appointments"));
        }
        return retval;
    }

    @PostMapping("/{token}")
    public ResponseEntity<Map<String, Object>> bookAppointment(@RequestBody Appointment appointment, @PathVariable String token) {
        ResponseEntity<Map<String, Object>> retval;
        try {
        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "patient");
            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
                retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
            } else if (!service.validateAppointment(appointment)) {
                retval = ResponseEntity.badRequest().body(Map.of("message", "Invalid appointment data"));
            } else {
                retval = appointmentService.bookAppointment(appointment);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to book appointment"));
        }
        return retval;
    }

    @PutMapping("/{token}")
    public ResponseEntity<Map<String, Object>> updateAppointment(@RequestBody Appointment appointment, @PathVariable String token) {
        ResponseEntity<Map<String, Object>> retval;
        try {
        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "patient");
            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
                retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
            } else {
            	switch (service.validateAppointment(appointment)) {
            	case -1:
            			retval = ResponseEntity.badRequest().body(Map.of("message", "Invalid appointment data"));
            			break;
            	case 0:
        			retval = ResponseEntity.badRequest().body(Map.of("message", "Invalid appointment data"));
            			break;
            	case 1:
            		ResponseEntity<Map<String, String>> updateResult = appointmentService.updateAppointment(appointment);
            		retval 	= ResponseEntity.status(updateResult.getStatusCode()).body(updateResult.getBody().);
            			break;
            		default:
            			break;
            	}
                
            } else {
                retval = 
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to update appointment"));
        }
        return retval;
    }
    
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, Object>> cancelAppointment(@PathVariable int id, @PathVariable String token) {
        ResponseEntity<Map<String, Object>> retval;
        try {
        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "patient");
            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
                retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
            } else {
                retval = appointmentService.cancelAppointment(id);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to cancel appointment"));
        }
        return retval;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public AppointmentService getAppointmentService() {
        return appointmentService;
    }

    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }   

}
