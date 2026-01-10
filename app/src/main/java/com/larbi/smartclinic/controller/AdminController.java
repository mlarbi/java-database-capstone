package com.larbi.smartclinic.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.larbi.smartclinic.model.Admin;
import com.larbi.smartclinic.service.Service;

@RestController
@RequestMapping("${api.path}" + "admin")
public class AdminController {
    @Autowired
    private Service service;

    @PostMapping()
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody Admin admin) {
        return service.validateAdmin(admin);
    }

    public AdminController(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

//    Define the adminLogin Method
//    Annotate this method with @PostMapping.
//    The method should accept an Admin object in the request body.
//    It should call validateAdmin method from Service to perform the admin login validation.
//    Return the response from the validateAdmin method, which provides the result of the admin login validation.
//    Response
//    The method returns a ResponseEntity<Map<String, String>>.
//    If the admin credentials are correct, the response will include a token.
//    If the credentials are incorrect, the response will contain an error message.

//    Steps Summary
//    Set up the controller: Annotate with @RestController and @RequestMapping.
//    Inject the Service: Autowire the Service class for validation logic.
//    Define the login endpoint: Use @PostMapping to handle login requests, returning appropriate responses.
}
