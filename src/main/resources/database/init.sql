create table if not exists doctor (
        id serial not null primary key,
        father_name varchar(255),
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        uuid uuid not null
    );

create table if not exists patient (
        id serial not null primary key,
        birthday date,
        father_name varchar(255),
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        uuid uuid not null
    );

create table if not exists ticket (
        id serial not null primary key,
        end_time timestamp(6) not null,
        is_free boolean not null,
        start_time timestamp(6) not null,
        doctor_id integer not null,
        patient_id integer,
		foreign key(doctor_id) references doctor(id),
		foreign key(patient_id) references patient(id),
		constraint for_doctor unique (doctor_id, start_time, end_time),
		constraint for_patient unique (patient_id, start_time, end_time)
    );