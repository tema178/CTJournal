create table authorities (id bigint generated by default as identity, authority varchar(255), username varchar(255), primary key (id));
create table climbing_sessions (id bigint not null, end_time bigint not null, start_time bigint not null, primary key (id));
create table exercise_sessions (id bigint generated by default as identity, workout_id bigint, primary key (id));
create table exercises (dtype varchar(31) not null, id bigint generated by default as identity, comment varchar(255), name varchar(255), number_of_sets integer not null, repeats smallint, duration_seconds smallint, exercise_session_id bigint, primary key (id));
create table grades (id bigint generated by default as identity, australian varchar(255), french varchar(255), norway varchar(255), south_africa varchar(255), uiaa varchar(255), usa varchar(255), primary key (id));
create table locations (id bigint generated by default as identity, favourite boolean not null, name varchar(255), region varchar(255), user_id varchar(255), primary key (id));
create table routes (id bigint generated by default as identity, attempts smallint not null, attempts_for_red_point smallint not null, comment varchar(255), difficulty_level tinyint check (difficulty_level between 0 and 4), name varchar(255), rating tinyint not null, send_style tinyint check (send_style between 0 and 5), type tinyint check (type between 0 and 4), workout_id bigint, grade_id bigint, primary key (id));
create table user_to_teacher (student_id varchar(255) not null, teacher_id varchar(255) not null);
create table users (username varchar(255) not null, enabled boolean not null, password varchar(255), primary key (username));
create table workouts (id bigint generated by default as identity, comment varchar(255), date timestamp(6), difficulty_level tinyint check (difficulty_level between 0 and 4), end_time bigint not null, start_time bigint not null, location_id bigint, username varchar(255) not null, primary key (id));
alter table if exists workouts drop constraint if exists UK_q664tmbeibytu0r5ff8l5cxxp;
alter table if exists authorities add constraint FKhjuy9y4fd8v5m3klig05ktofg foreign key (username) references users;
alter table if exists climbing_sessions add constraint FKntiww102k55sy72lqfgfv2f45 foreign key (id) references workouts;
alter table if exists exercise_sessions add constraint FKou7qya0btas92ah4vphr3t86f foreign key (workout_id) references workouts;
alter table if exists exercises add constraint FKqb7imga89rprjv09ql9364n6r foreign key (exercise_session_id) references exercise_sessions;
alter table if exists locations add constraint FKdmtxqhxbtg4qt8q6u01hxj52i foreign key (user_id) references users;
alter table if exists routes add constraint FKcb1e43gnk9pw1kwr2s7fg4lf5 foreign key (workout_id) references climbing_sessions;
alter table if exists routes add constraint FKjs2tpbqmrlss37vlgwslegady foreign key (grade_id) references grades;
alter table if exists user_to_teacher add constraint FK21fxedgpwr6iub8mh37wgpvr6 foreign key (teacher_id) references users;
alter table if exists user_to_teacher add constraint FKbg2srevt8qcfm38e6w6umfc7u foreign key (student_id) references users;
alter table if exists workouts add constraint FKkiswai27id4rggu0wfp0swfc3 foreign key (location_id) references locations;
alter table if exists workouts add constraint workoutToUser foreign key (username) references users;