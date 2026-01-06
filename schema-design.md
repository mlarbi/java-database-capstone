## MySQL Database Design
The MySQL database schema consists of the following tables:

### Table: person -- This is the main table that stores common information for all types of users (patients, doctors, admins).
- person_id: INT, Primary Key, Auto Increment
- person_type: VARCHAR(20), Not Null  -- 'patient', 'doctor', 'admin'
- email: VARCHAR(100) not null, unique
- first_name: VARCHAR(50), Not Null
- last_name: VARCHAR(50), Not Null
- middle_name: VARCHAR(50)
- phone_number: VARCHAR(15)
- address_line1: VARCHAR(100)
- address_line2: VARCHAR(100)
- city: VARCHAR(50)
- state: VARCHAR(50)
- zip_code: VARCHAR(10)
- country: VARCHAR(50)
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP

### Table: person_auth -- This table stores authentication information for all users. It has a one-to-one relationship with the person table.
- person_id: INT, Primary Key, Foreign Key → person(person_id)
- email: VARCHAR(100), Not Null
- password_hash: VARCHAR(255), Not Null
- unique(person_id, email)  

### Table: patient
- person_id: INT, Primary Key, Foreign Key → person(person_id)
- date_of_birth: DATE, Not Null
- emergency_contact_name: VARCHAR(100)
- emergency_contact_phone: VARCHAR(15)
- insurance_provider: VARCHAR(100)
- insurance_policy_number: VARCHAR(50)
- allergies: TEXT
- medical_history: TEXT
- current_medications: TEXT
- preferred_pharmacy: VARCHAR(100)

### Table: doctor
- person_id: INT, Primary Key, Foreign Key → person(person_id)
- specialty: VARCHAR(255), Not Null -- 'Cardiology', 'Dermatology', 'Pediatrics', etc. separated by commas for multiple specialties
- license_number: VARCHAR(50)
- clinic_address: VARCHAR(255)

### Table: appointment
- appointment_id: INT, Primary Key, Auto Increment
- patient_id: INT, Foreign Key → patient(person_id)
- doctor_id: INT, Not Null, Foreign Key → doctor(person_id)
- appt_date: DATE, Not Null
- start_time: TIME, Not Null
- end_time: TIME, Not Null
- is_dr_blocked_slot: BOOLEAN, Default false
- reason: VARCHAR(255)
- status: VARCHAR(50), Default 'scheduled' -- 'scheduled', 'completed', 'cancelled', 'no-show'
- doctor_notes: TEXT    

### Table: billing_info
- billing_id: INT, Primary Key, Auto Increment
- patient_id: INT, Not Null, Foreign Key → patient(person_id)
- insurance_provider: VARCHAR(100)
- insurance_policy_number: VARCHAR(50)
- billing_address: VARCHAR(255)
- billing_amount: DECIMAL(10,2), Not Null
- due_date: DATE, Not Null, Default CURRENT_DATE + INTERVAL 30 DAY  

### Table: payment
- payment_id: INT, Primary Key, Auto Increment
- billing_id: INT, Not Null, Foreign Key → billing_info(billing_id)
- patient_id: INT, Not Null, Foreign Key → patient(person_id)
- appointment_id: INT, Not Null, Foreign Key → appointment(appointment_id)
- amount: DECIMAL(10,2), Not Null
- payment_date: TIMESTAMP, Default CURRENT_TIMESTAMP
- payment_method: VARCHAR(50), Not Null


## MongoDB Collection Design

###### Collection: prescription
- prescription_id: INT, Primary Key, Auto Increment
- patient_id: INT, Foreign Key → patient(person_id)
- doctor_id: INT, Foreign Key → doctor(person_id)
- appointment_id: INT, Foreign Key → appointment(appointment_id), Nullable
- medication_name: VARCHAR(100), Not Null
- dosage: VARCHAR(50), Not Null
- frequency: VARCHAR(50), Not Null
- prescribed_date: DATE, Default CURRENT_DATE
- instructions: TEXT
- doctor_notes: TEXT
- refill_count: INT, Default 0
- pharmacy: {
    name: VARCHAR(100),
    location: VARCHAR(255)
}

```json
{
  "_id": "ObjectId('64abc123456')",
  "patientName": "John Smith",
  "appointmentId": 51,
  "medication": "Paracetamol",
  "dosage": "500mg",
  "frequency": "Every 6 hours",
  "prescribedDate": "2024-06-01",
  "instructions": "Take with food to avoid stomach upset.",
  "doctorNotes": "Take 1 tablet every 6 hours.",
  "refillCount": 2,
  "pharmacy": {
    "name": "Walgreens SF",
    "location": "Market Street"
  }
}