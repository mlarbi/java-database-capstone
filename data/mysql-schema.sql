create table person(
    person_id int auto_increment primary key,
    person_type varchar(20) not null, -- 'patient', 'doctor', 'admin'
    email varchar(100) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    middle_name varchar(50),
    phone_number varchar(15),
    address_line1 varchar(100),
    address_line2 varchar(100),
    city varchar(50),
    state varchar(50),
    zip_code varchar(10),
    country varchar(50),
    created_at timestamp default current_timestamp,
    unique(email)
);

create table person_auth(
    person_id int not null primary key,
    email varchar(100) not null,
    password_hash varchar(255) not null,
    foreign key (person_id) references person(person_id),
    unique(person_id, email)
);

create table patient(
    person_id int not null primary key,
    date_of_birth date,
    emergency_contact_name varchar(100),
    emergency_contact_phone varchar(15),
    insurance_provider varchar(100),
    insurance_policy_number varchar(50),
    medical_history text,
    allergies text,
    current_medications text,
    preferred_pharmacy varchar(100),
    foreign key (person_id) references person(person_id)
);

create table doctor(
    person_id int not null primary key,
    specialty varchar(100) not null,
    license_number varchar(50),
    clinic_address varchar(255),
    foreign key (person_id) references person(person_id)
);

create table appointment(
    appointment_id int auto_increment primary key,
    patient_id int,
    doctor_id int not null,
    appt_date date not null,
    start_time time not null,
    end_time time not null,
    is_dr_blocked_slot boolean default false,
    reason varchar(255),
    status varchar(50) default 'scheduled', -- 'scheduled', 'completed', 'cancelled', 'no-show'
    created_at timestamp default current_timestamp,
    doctor_notes text,
    foreign key (patient_id) references patient(person_id),
    foreign key (doctor_id) references doctor(person_id)
);


create table prescription(
    prescription_id int auto_increment primary key,
    patient_id int not null,
    doctor_id int not null, 
    appointment_id int,  -- a prescription may or may not be linked to an appointment
    medication_name varchar(100) not null,
    dosage varchar(50) not null,
    frequency varchar(50) not null,
    prescribed_date date default current_date,
    instructions text,
    foreign key (patient_id) references patient(person_id),
    foreign key (doctor_id) references doctor(person_id),
    foreign key (appointment_id) references appointment(appointment_id)
);

create table billing_info(
    billing_id int auto_increment primary key,
    patient_id int not null,
    insurance_provider varchar(100),
    insurance_policy_number varchar(50),
    billing_address varchar(255),
    billing_amount decimal(10,2) not null,
    due_date date not null default current_date + interval 30 day,
    foreign key (patient_id) references patient(person_id)
);
create table payment(
    payment_id int auto_increment primary key,
    billing_id int not null,
    patient_id int not null,
    appointment_id int,
    amount decimal(10,2) not null,
    payment_date timestamp default current_timestamp,
    payment_method varchar(50) not null,
    foreign key (appointment_id) references appointment(appointment_id),
    foreign key (billing_id) references billing_info(billing_id),
    foreign key (patient_id) references patient(person_id)
);

