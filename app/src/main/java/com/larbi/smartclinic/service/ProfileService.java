package com.larbi.smartclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.larbi.smartclinic.repository.mysql.PersonRepository;

@Service
public class ProfileService {
	@Autowired
    private PersonRepository personRepository;
}
