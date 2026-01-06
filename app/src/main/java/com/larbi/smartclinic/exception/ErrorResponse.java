package com.larbi.smartclinic.exception;

public record ErrorResponse(
	    int status,
	    String message,
	    long timestamp
	) {}