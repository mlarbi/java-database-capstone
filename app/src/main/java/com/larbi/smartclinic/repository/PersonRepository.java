package com.larbi.smartclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.larbi.smartclinic.model.AuthDTO;
import com.larbi.smartclinic.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	@Query("""
	        SELECT new com.larbi.smartclinic.model.AuthDTO(p.id, p.passwordHash, p.personType)
	        FROM Person p
	        WHERE p.email = :email
	    """)
	    Optional<AuthDTO> findAuthInfoByUsername(@Param("email") String email);
}
