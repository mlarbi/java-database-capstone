package com.larbi.smartclinic.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.larbi.smartclinic.service.DoctorService;
import com.larbi.smartclinic.service.Service;
import com.larbi.smartclinic.service.TokenService;

@RestController
@RequestMapping("${api.path}" + "doctor")
public class DoctorController {
	
    @Autowired
    private Service service;
 
    @Autowired
    private DoctorService doctorService;
    
   public DoctorController(Service service, DoctorService doctorService) {
        this.service = service;
        this.doctorService = doctorService;
    }   

   @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(@PathVariable String user, @PathVariable Long doctorId, @PathVariable String date, @PathVariable String token) {
        ResponseEntity<Map<String, Object>> retval;
        try {
        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, user);
            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
                retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
            } else {
                List<String> availabilityList = doctorService.getDoctorAvailability(doctorId, LocalDate.parse(date));
                retval = ResponseEntity.status(HttpStatus.OK).body(Map.of("availability", availabilityList));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to retrieve doctor's availability"));
        }
        return retval;
    }   
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDoctors() {
        ResponseEntity<Map<String, Object>> retval;
        try {
            List<Doctor> doctors = doctorService.getDoctors();
            retval = ResponseEntity.status(HttpStatus.OK).body(Map.of("doctors", doctors));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to retrieve doctors"));
        }       
        return retval;
    }

    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> saveDoctor(@RequestBody Doctor doctor, @PathVariable String token) {
        ResponseEntity<Map<String, String>> retval = null;
        try {
        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "admin");
            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
                 retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
            } else {
            	int saveResult = doctorService.saveDoctor(doctor) ;
            	if (saveResult == -1) {
            		retval = ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Doctor already exists"));
            	} else if (saveResult == 0) {
                    retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to save doctor"));       		
            	} else if (saveResult == 1) {
            		retval = ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Doctor added to db"));
            	}
             }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to save doctor"));
        }
        return retval;
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> validateDoctor(@RequestBody Login login) {
        ResponseEntity<Map<String, String>> retval;
        try {
            retval = doctorService.validateDoctor(login);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to validate doctor"));
        }
        return retval;
    }   

    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateDoctor(@RequestBody Doctor doctor, @PathVariable String token) {
        ResponseEntity<Map<String, String>> retval = null;
        try {
        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "admin");
            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
                 retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
            } else {
             	int updateResult = doctorService.updateDoctor(doctor) ;
            	if (updateResult == -1) {
            		retval = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Doctor not found"));
            	} else if (updateResult == 0) {
                    retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to save doctor"));       		
            	} else if (updateResult == 1) {
            		retval = ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "Doctor updated"));
            	}
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to update doctor"));
        }
        return retval;
    }

    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> deleteDoctor(@PathVariable int id, @PathVariable String token) {
        ResponseEntity<Map<String, String>> retval = null;
        try {
        	ResponseEntity<Map<String, String>> tokenValidateResult = service.validateToken(token, "admin");
            if (tokenValidateResult.getStatusCode() != HttpStatus.OK) {
                 retval = ResponseEntity.status(tokenValidateResult.getStatusCode()).body(Map.of("message", "Unauthorized"));
            } else {
             	int deleteResult = doctorService.deleteDoctor(id) ;
            	if (deleteResult == -1) {
            		retval = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Doctor not found with id"));
            	} else if (deleteResult == 0) {
                    retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Some internal error occurred"));       		
            	} else if (deleteResult == 1) {
            		retval = ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "Doctor deleted successfully"));
            	}
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Some internal error occurred"));
        }
        return retval;
    }
    
    @GetMapping("/filter/{name}/{time}/{speciality}")
    public ResponseEntity<Map<String, Object>> filterDoctors(@PathVariable String name, @PathVariable String time, @PathVariable String speciality) {
        ResponseEntity<Map<String, Object>> retval;
        try {
            Map<String, Object> filteredDoctors = service.filterDoctor(name, speciality, time);
            retval = ResponseEntity.ok(filteredDoctors);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            retval = ResponseEntity.status(500).body(Map.of("message", "Failed to filter doctors"));
        }
        return retval;      
    }
    
    public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public DoctorService getDoctorService() {
		return doctorService;
	}

	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
  
}
