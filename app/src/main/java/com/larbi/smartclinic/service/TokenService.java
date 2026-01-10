package com.larbi.smartclinic.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.larbi.smartclinic.repository.mysql.AdminRepository;
import com.larbi.smartclinic.repository.mysql.DoctorRepository;
import com.larbi.smartclinic.repository.mysql.PatientRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenService {
	@Autowired
	private final AdminRepository adminRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;


	@Value("${smartclinic.jwt.secret}")
    private String secretKey;

    @Value("${smartclinic.jwt.expiration}")
    private long jwtExpiration;
    
	public TokenService(AdminRepository adminRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
		this.adminRepository = adminRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
	}

	public String generateToken(String identifier, String role) {
		Map<String, Object> info = new HashMap<>();
		info.put("role", role);
		return this.generateToken(identifier, info);
	}
	
	public String generateToken(String identifer, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(identifer) //username or email
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

	// Convert the string secret from properties into a cryptographic SecretKey
    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

	public String extractIdentifier(String token) {
	    return extractAllClaims(token).getSubject();
	}

	public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
	    final Claims claims = extractAllClaims(token);
	    return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
	    return Jwts.parser()
	            .verifyWith(getSigningKey()) // Uses the SecretKey to verify signature
	            .build()
	            .parseSignedClaims(token)    // If expired or tampered, throws exception here
	            .getPayload();
	}
  
	public boolean isTokenValid(String token, String userIdentifier) {
	    final String identifier = extractIdentifier(token); //email or username
	    return (identifier.equals(userIdentifier)) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
	    return extractAllClaims(token).getExpiration().before(new Date());
	}

	public boolean validateToken(String token, String user) {
		try {
			String identifier = extractIdentifier(token);
			switch (user.toLowerCase()) {
				case "admin":
					return adminRepository.findByUsername(identifier) != null;
				case "doctor":
					return doctorRepository.findByEmail(identifier) != null;
				case "patient":
					return patientRepository.findByEmail(identifier) != null;
				default:
					return false;
			}
		} catch (Exception e) {
			return false; // Invalid token
		}
	}

	
}
