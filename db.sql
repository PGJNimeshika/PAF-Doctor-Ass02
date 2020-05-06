CREATE DATABASE apidb;

USE apidb;

CREATE TABLE doctor (
    doctor_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    doctor_license_no VARCHAR(255),
    doctor_name VARCHAR(255),
    doctor_address VARCHAR(255),
    doctor_phone VARCHAR(20),
    doctor_gender VARCHAR(10),
    doctor_email VARCHAR(255),
    doctor_specialization VARCHAR(255)
);


CREATE TABLE doctor_logins (
    doctor_id INT,
    doctor_username VARCHAR(255) PRIMARY KEY,
    doctor_password VARCHAR(40),
    doctor_last_login DATETIME,
    docotor_session VARCHAR(40),
    FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id)
);

CREATE TABLE doctor_schedule (
    schedule_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    schedule_date DATE,
    doctor_id INT,
    time_slot VARCHAR(255)
);

CREATE TABLE patient (
    patient_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(255),
    patient_age SMALLINT
);

CREATE TABLE appointment (
    appoint_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    patient_id INT, 
    appoint_date DATE,
    appoint_time TIME,
    appoint_status VARCHAR(20),
    doctor_id INT,
    FOREIGN KEY(patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id)
);

CREATE TABLE prescription (
    pres_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    patient_id INT,
    pres_date DATE,
    pres_time TIME,
    descript TEXT,
    doctor_id INT,
    appoint_id INT,
    FOREIGN KEY(patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id),
    FOREIGN KEY(appoint_id) REFERENCES appointment(appoint_id)
);

INSERT INTO doctor (doctor_license_no, doctor_name, doctor_address, doctor_phone, doctor_gender, doctor_email, doctor_specialization)
VALUES ('GMOA1', 'Nimeshika', 'Colombo', '0777123456', 'female', 'test@test.com', 'eye specialist'),
       ('GMOA2', 'Jehani', 'Piliyandala', '0711234569', 'female', 'one@test.com', 'Cardiologist'),
       ('GMOA2', 'Pavithra', 'Piliyandala', '0711234569', 'female', 'one@test.com', 'Physician'),
       ('GMOA2', 'Purnima', 'Piliyandala', '0711234569', 'female', 'one@test.com', 'Surgeon');

INSERT INTO doctor_logins(doctor_id, doctor_username, doctor_password, doctor_last_login, docotor_session) 
VALUES ('1', 'nimeshika', sha1('abc123'), NOW(), ''),
       ('2', 'jehani', sha1('abc123'), NOW(), ''),
       ('3', 'pavithra', sha1('abc123'), NOW(), ''),
       ('4', 'purnima', sha1('abc123'), NOW(), '');


INSERT INTO doctor_schedule (schedule_date, doctor_id, time_slot)
VALUES (CURRENT_DATE(), '1', '1600-1700'),
       (CURRENT_DATE(), '2', '2000-2200'),
       (CURRENT_DATE(), '3', '1000-1200'),
       (CURRENT_DATE(), '4', '1400-1200');


INSERT INTO patient (patient_name, patient_age)
VALUES ('Isira', 28),
       ('Kasun', 25),
       ('pooja', 21),
       ('pini', 16);

INSERT INTO appointment (patient_id, appoint_date, appoint_time, appoint_status, doctor_id)
VALUES ('1', CURRENT_DATE(), CURTIME(), 'pending','1'),
       ('2', CURRENT_DATE(), '20:00:00', 'accept','2'),
       ('3', CURRENT_DATE(), '10:00:00', 'pending','3'),
       ('4', CURRENT_DATE(), '10:00:00', 'accept', '4');

INSERT INTO prescription (patient_id, pres_date, pres_time, descript, doctor_id, appoint_id)
VALUES  ('1', CURRENT_DATE(), CURTIME(), 'need to check again', '1', '1'),
        ('2', CURRENT_DATE(), CURTIME(), 'give prescription', '2', '2'),
        ('3', CURRENT_DATE(), CURTIME(), 'prescriptions for two weeks', '3', '3'),
        ('4', CURRENT_DATE(), CURTIME(), 'need to check again', '4', '4');