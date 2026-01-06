package com.larbi.smartclinic.model;

public record AuthDTO(
	    Long personId,
	    String passwordHash,
	    String personType // This tells us which subclass it is
	) {}