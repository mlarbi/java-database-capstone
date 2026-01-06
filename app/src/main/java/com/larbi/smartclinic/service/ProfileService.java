package com.larbi.smartclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.larbi.smartclinic.model.Doctor;
import com.larbi.smartclinic.model.Person;
import com.larbi.smartclinic.repository.PersonRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProfileService {
	@Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void updateProfile(Long personId, ProfileUpdateRequest request) {
        // 1. Fetch the entity (Hibernate loads data from all relevant tables)
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 2. Update fields in the 'person' table
        if (request.getName() != null) {
            person.setName(request.getName());
        }

        // 3. Update fields in the 'person_auth' table (Secondary Table)
        if (request.getNewPassword() != null) {
            person.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        }

        // 4. Update subclass-specific fields if it's a Doctor
        if (person instanceof Doctor doctor && request.getSpecialization() != null) {
            doctor.setSpecialization(request.getSpecialization());
        }

        // No need for personRepository.save()! 
        // At the end of the @Transactional method, Hibernate flushes changes.
    }}
