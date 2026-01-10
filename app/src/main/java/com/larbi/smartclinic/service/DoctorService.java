package com.larbi.smartclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.larbi.smartclinic.model.Appointment;
import com.larbi.smartclinic.model.Doctor;
import com.larbi.smartclinic.model.Login;
import com.larbi.smartclinic.repository.mysql.AppointmentRepository;
import com.larbi.smartclinic.repository.mysql.DoctorRepository;

@Service
public class DoctorService {

    @Autowired 
    DoctorRepository doctorRepository;

    @Autowired 
    AppointmentRepository appointmentRepository;

    @Autowired 
    TokenService tokenService;  

    public List<String> getDoctorAvailability(Long doctorId, LocalDate date) {
        // Fetch appointments for the doctor on the specified date
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDate(doctorId, date);
        
        // Define all possible time slots (e.g., 9 AM to 5 PM)
        List<String> allTimeSlots = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00");
        
        // Filter out booked slots
        List<String> availableSlots = new ArrayList<>(allTimeSlots);
        for (Appointment appointment : appointments) {
            String timeSlot = appointment.getAppointmentTime().toLocalTime().toString();
            availableSlots.remove(timeSlot);
        }
        
        return availableSlots;
    }

    public int saveDoctor(Doctor doctor) {
        // Check if doctor already exists by email
        if (doctorRepository.findByEmail(doctor.getEmail()) != null) {
            return -1; // Doctor already exists
        }     
        try {
            doctorRepository.save(doctor);
            return 1; // Success
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Internal error
        }
    }
    
    public int updateDoctor(Doctor doctor) {
        Optional<Doctor> existingDoctorOpt = doctorRepository.findById(doctor.getId());
        
        if (!existingDoctorOpt.isPresent()) {
            return -1; // Doctor not found
        }
        
        try {
            Doctor existingDoctor = existingDoctorOpt.get();
            existingDoctor.setName(doctor.getName());
            existingDoctor.setEmail(doctor.getEmail());
            existingDoctor.setPassword(doctor.getPassword());
            existingDoctor.setPhone(doctor.getPhone());
            existingDoctor.setSpecialty(doctor.getSpecialty());
            existingDoctor.setLicenseNumber(doctor.getLicenseNumber());
            doctorRepository.save(existingDoctor);
            return 1; // Success
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Internal error
        }
    }

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public int deleteDoctor(long id) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);
        
        if (!doctorOpt.isPresent()) {
            return -1; // Doctor not found
        }
        
        try {
            // Delete all appointments associated with the doctor
            appointmentRepository.deleteAllByDoctorId(id);
            // Delete the doctor
            doctorRepository.deleteById(id);
            return 1; // Success
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Internal error
        }
    }

    public ResponseEntity<Map<String, String>> validateDoctor(Login login) {
        Doctor doctor = doctorRepository.findByEmail(login.getIdentifier());
        
        if (doctor == null || !doctor.getPassword().equals(login.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
     
        Map<String, Object> info = new HashMap<>();
        info.put("role", "DOCTOR");
//        info.put("clinic", "North_Wing");
//        info.put("can_delete", true);

        String token = tokenService.generateToken(doctor.getEmail(), info);       
         Map<String, String> response = new HashMap<>();
        response.put("token", token);
        
        return ResponseEntity.ok(response);
    }

    public Map<String, Object> findDoctorByName(String name) {
        List<Doctor> doctors = doctorRepository.findByNameLike("%" + name + "%");
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);
        return response;
    }

    public Map<String, Object> filterDoctorsByNameSpecilityandTime(String name, String specialty, String amOrPm) {
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);
        List<Doctor> filteredDoctors = filterDoctorByTime(doctors, amOrPm);
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", filteredDoctors);
        return response;
    }

    public Map<String, Object> filterDoctorByNameAndTime(String name, String amOrPm) {
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCase(name);
        List<Doctor> filteredDoctors = filterDoctorByTime(doctors, amOrPm);
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", filteredDoctors);
        return response;
    }

    public Map<String, Object> filterDoctorByNameAndSpecility(String name, String specialty) {
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);
        return response;
    }

    public Map<String, Object> filterDoctorByTimeAndSpecility(String specialty, String amOrPm) {
        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);
        List<Doctor> filteredDoctors = filterDoctorByTime(doctors, amOrPm);
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", filteredDoctors);
        return response;
    }

    public Map<String, Object> filterDoctorBySpecility(String specialty) {
        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);
        return response;
    }

    public Map<String, Object> filterDoctorsByTime(String amOrPm) {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Doctor> filteredDoctors = filterDoctorByTime(doctors, amOrPm);
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", filteredDoctors);
        return response;
    }

    private List<Doctor> filterDoctorByTime(List<Doctor> doctors, String amOrPm) {
        List<Doctor> filteredDoctors = new ArrayList<>();
        for (Doctor doctor : doctors) {
            for (String timeSlot : doctor.getAvailableTimes()) {
                if ((amOrPm.equalsIgnoreCase("AM") && timeSlot.endsWith("AM")) ||
                    (amOrPm.equalsIgnoreCase("PM") && timeSlot.endsWith("PM"))) {
                    filteredDoctors.add(doctor);
                    break; // No need to check other time slots for this doctor
                }
            }
        }
        return filteredDoctors;
    }

}
