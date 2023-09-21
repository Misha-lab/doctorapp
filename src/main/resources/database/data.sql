insert into doctor (id, uuid, father_name, first_name, last_name)
values(default, gen_random_uuid(), 'Николаевич', 'Николай', 'Николаев');

insert into doctor (id, uuid, father_name, first_name, last_name)
values(default, gen_random_uuid(), 'Петрович', 'Пётр', 'Петров');

insert into doctor (id, uuid, father_name, first_name, last_name)
values(default, gen_random_uuid(), null, 'Артём', 'Артёмов');

insert into doctor (id, uuid, father_name, first_name, last_name)
values(default, gen_random_uuid(), 'Антонович', 'Антон', 'Антонов');


insert into patient (id, uuid, father_name, first_name, last_name, birthday)
values(default, gen_random_uuid(), 'Евгеньевич', 'Евгений', 'Евгеньев', '22.02.2002');

insert into patient (id, uuid, father_name, first_name, last_name, birthday)
values(default, gen_random_uuid(), null, 'Александр', 'Александров', '15.05.2005');

insert into patient (id, uuid, father_name, first_name, last_name, birthday)
values(default, gen_random_uuid(), 'Андреевич', 'Андрей', 'Андреев', '10.02.2001');


insert into ticket (id, start_time, end_time, doctor_id, patient_id, is_free)
values(default, '2023-09-24T08:00:00', '2023-09-24T08:20:00', 3, 4, false);

INSERT INTO ticket (id, start_time, end_time, doctor_id, patient_id, is_free) 
VALUES(default, '2023-09-24T08:00:00', '2023-09-24T08:20:00', 1, 2, false);

insert into ticket (id, start_time, end_time, doctor_id, patient_id, is_free)
values(default, '2023-09-24T08:20:00', '2023-09-24T08:40:00', 3, null, true);

INSERT INTO ticket (id, start_time, end_time, doctor_id, patient_id, is_free) 
VALUES(default, '2023-09-24T08:40:00', '2023-09-24T09:00:00', 5, 1, false);

insert into ticket (id, start_time, end_time, doctor_id, patient_id, is_free)
values(default, '2023-09-24T15:30:00', '2023-09-24T16:00:00', 6, 4, false);