package com.larbi.smartclinic.repository.mysql;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.larbi.smartclinic.model.AuthDTO;
import com.larbi.smartclinic.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	@Query("""
	        SELECT new com.larbi.smartclinic.model.AuthDTO(p.personId, p.password, p.personType)
	        FROM Person p
	        WHERE p.email = :email
	    """)
	    Optional<AuthDTO> findAuthInfoByUsername(@Param("email") String email);
}
