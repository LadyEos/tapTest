insert into settings (global_passing_score) values (350);

insert into programs (name, program_key, passing_score) values ('Humanities', 'l', 160);
insert into programs (name, program_key, passing_score) values ('Science', 's', 160);

insert into subjects (name, file_order, program_id) values ('English', 1, null);
insert into subjects (name, file_order, program_id) values ('Math', 2, 2);
insert into subjects (name, file_order, program_id) values ('Science', 3, 2);
insert into subjects (name, file_order, program_id) values ('Japanese', 4, 1);
insert into subjects (name, file_order, program_id) values ('Geography/History', 5, 1);
