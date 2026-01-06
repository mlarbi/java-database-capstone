package com.larbi.smartclinic.model;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "person")
@SecondaryTable(name = "person_auth", pkJoinColumns = @PrimaryKeyJoinColumn(name = "person_id"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
@DynamicUpdate
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(name = "person_type", insertable = false, updatable = false)
    private String personType;

    @NotNull(message = "Email is required") 
	@Column(name = "email", table = "person_auth", unique = true)
    @Size(min = 6, max = 100)
    @Email( message = "Please provide a valid email address")
    private String email;

	@NotNull(message = "Password is required")
    @Column(name = "password", table = "person_auth")
    @Size(min = 3, max = 255)
    private String password;

	@NotNull(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 50)
    private String firstName;
    
    @NotNull(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 50)
    private String lastName;
    
    @Column(name = "middle_name")
    @Size(min = 3, max = 50)
    private String middleName;
    
    @NotNull(message="Phone number is required")
    @Column(name = "phone_number")
    @Pattern(regexp = "\\d{10}", message="Invalid phone number format")
    @Size(min = 10, max = 15)
    private String phoneNumber;
    
    @Size(min = 3, max = 100)
    @Column(name = "address_line1")  
    private String addressLine1;
    
    @Column(name = "address_line2")
    @Size(min = 3, max = 100)
    private String addressLine2;
    
    @Column(name = "city")      
    @Size(min = 3, max = 50)
    private String city;
    
    @Column(name = "state")
    @Size(min = 3, max = 50)
    private String state;
    
    @Column(name = "zip_code")
    @Size(min =5, max = 10)
    private String zipCode;
    
    @Column(name = "country")
    @Size(min = 3, max = 100)
    private String country;
    
    @Column(name = "created_at")
    private Timestamp createdAt;
    
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}