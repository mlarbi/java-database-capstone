package com.larbi.smartclinic.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
@DiscriminatorValue("ADMIN")
// If the admin table's PK is also called 'person_id', use this:
@PrimaryKeyJoinColumn(name = "person_id")  // Maps Admin's ID to Person's ID
public class Admin extends Person{
	private String adminDepartment;

	public String getAdminDepartment() {
		return adminDepartment;
	}

	public void setAdminDepartment(String adminDepartment) {
		this.adminDepartment = adminDepartment;
	}
}
