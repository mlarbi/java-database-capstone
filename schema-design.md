## MySQL Database Design
The MySQL database schema consists of the following tables:

### Table: person
- person_id: INT, Primary Key, Auto Increment
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

### Table: person_auth
- person_id: INT, Primary Key, Foreign Key → person(person_id)
- email: VARCHAR(100), Not Null
- password_hash: VARCHAR(255), Not Null
- is_admin: BOOLEAN, Default false
- is_patient: BOOLEAN, Default false
- is_doctor: BOOLEAN, Default false 
- unique(person_id, email)  


### Table: patient
- person_id: INT, Primary Key, Foreign Key → person(person_id)
- date_of_birth: DATE
- emergency_contact_name: VARCHAR(100)
- emergency_contact_phone: VARCHAR(15)
- insurance_provider: VARCHAR(100)
- insurance_policy_number: VARCHAR(50)

### Table: doctor
- person_id: INT, Primary Key, Foreign Key → person(person_id)
- specialty: VARCHAR(100)

### Table: appointment
- appointment_id: INT, Primary Key, Auto Increment
- patient_id: INT, Foreign Key → patient(person_id)
- doctor_id: INT, Foreign Key → doctor(person_id)
- appointment_date: DATETIME, Not Null
- reason: VARCHAR(255)
- status: VARCHAR(50), Default 'scheduled'
- doctor_notes: TEXT    

### Table: doctor_calendar
- calendar_id: INT, Primary Key, Auto Increment
- doctor_id: INT, Not Null, Foreign Key → doctor(person_id)
- calendar_date: DATE, Not Null
- start_time: TIME, Not Null
- end_time: TIME, Not Null
- is_available: BOOLEAN, Default true
- unique(doctor_id, calendar_date, start_time, end_time)

### Table: prescription
- prescription_id: INT, Primary Key, Auto Increment
- patient_id: INT, Foreign Key → patient(person_id)
- doctor_id: INT, Foreign Key → doctor(person_id)
- appointment_id: INT, Foreign Key → appointment(appointment_id), Nullable
- medication_name: VARCHAR(100), Not Null
- dosage: VARCHAR(50), Not Null
- frequency: VARCHAR(50), Not Null
- prescribed_date: DATE, Default CURRENT_DATE
- instructions: TEXT

### Table: appointment_history
- history_id: INT, Primary Key, Auto Increment
- appointment_id: INT, Foreign Key → appointment(appointment_id)
- status: VARCHAR(50), Not Null
- changed_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- notes: TEXT   

### Table: clinic_location
- location_id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- address_line1: VARCHAR(100)
- address_line2: VARCHAR(100)
- city: VARCHAR(50)
- state: VARCHAR(50)
- zip_code: VARCHAR(10)
- country: VARCHAR(50)
- phone_number: VARCHAR(15) 

### Table: payment
- payment_id: INT, Primary Key, Auto Increment
- appointment_id: INT, Foreign Key → appointment(appointment_id)
- amount: DECIMAL(10,2), Not Null
- payment_date: TIMESTAMP, Default CURRENT_TIMESTAMP
- payment_method: VARCHAR(50)

### Table: billing_info
- billing_id: INT, Primary Key, Auto Increment
- patient_id: INT, Foreign Key → patient(person_id)
- insurance_provider: VARCHAR(100)
- insurance_policy_number: VARCHAR(50)
- billing_address: VARCHAR(255)
- billing_amount: DECIMAL(10,2), Not Null
- due_date: DATE, Not Null, Default CURRENT_DATE + INTERVAL 30 DAY  

## MongoDB Collection Design
