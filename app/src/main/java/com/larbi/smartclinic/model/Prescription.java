package com.larbi.smartclinic.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document
public class Prescription {
   @Id
    private String id;
    
    @NotNull
    @Size(min = 3, max = 100)
    private String patientName;
    
    @NotNull
    private Long appointmentId;
    
    @NotNull
    @Size(min = 3, max = 100)
    private String medication;

    @NotNull
    @Size(min = 3, max = 100)
    private String dosage;

    @NotNull
    @Size(min = 3, max = 100)   
    private String frequency;   

    private String instructions;
    
    @Size(max = 200)
    private String doctorNotes;

}
