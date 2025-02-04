
    create sequence countries_seq start with 1 increment by 50;

    create sequence school_class_seq start with 1 increment by 50;

    create sequence school_seq start with 1 increment by 50;

    create sequence students_seq start with 1 increment by 50;

    create sequence teacher_seq start with 1 increment by 50;

    create table countries (
        iso2code varchar(2),
        land_code integer check ((land_code<=9999) and (land_code>=1)),
        top_level_domain varchar(4),
        id bigint not null,
        "country name" varchar(64),
        primary key (id)
    );

    create table school (
        country_id bigint,
        id bigint not null,
        zip_code varchar(16) not null,
        city varchar(64) not null,
        str_number varchar(64) not null,
        director varchar(96),
        homepage varchar(255) not null,
        school_name varchar(255) not null,
        primary key (id)
    );

    create table school_class (
        floor integer not null,
        id bigint not null,
        school_id bigint,
        school_class_name varchar(128) not null,
        department varchar(255) not null check (department in ('ARCHITECTURE_DESIGN','FASHION_DESIGN','GRAPHIC_DESIGN','INDUSTRIAL_DESIGN','MULTIMEDIA_DESIGN','INTERACTION_DESIGN','CYBERSECURITY','WEB_DEVELOPMENT','INFORMATICS','ARTIFICIAL_INTELLIGENCE')),
        primary key (id)
    );

    create table student_emails (
        student_id bigint not null,
        email_addresses varchar(255)
    );

    create table students (
        area_code varchar(4),
        country_code integer not null,
        country_id bigint,
        id bigint not null,
        school_id bigint,
        student_class_id bigint,
        mobile_number varchar(16),
        zip_code varchar(16) not null,
        last_name varchar(32) not null,
        student_key varchar(40) not null,
        city varchar(64) not null,
        first_name varchar(64) not null,
        str_number varchar(64) not null,
        username varchar(64),
        password varchar(128) not null,
        birthday varchar(255),
        gender varchar(255) not null check (gender in ('MALE','FEMALE','DIVERS')),
        religion varchar(255) not null check (religion in ('CHRISTIANITY','ISLAM','JUDAISM','HINDUISM','BUDDHISM','SIKHISM','OTHER','NONE_SPECIFIED')),
        primary key (id)
    );

    create table teacher (
        mobile_country_code integer not null,
        school_mobile_country_code integer,
        country_id bigint,
        id bigint not null,
        school_id bigint,
        mobile_number varchar(16) not null,
        school_mobile_number varchar(16),
        zip_code varchar(16) not null,
        last_name varchar(32) not null,
        teacher_key varchar(40) not null,
        city varchar(64) not null,
        first_name varchar(64) not null,
        str_number varchar(64) not null,
        username varchar(64),
        password varchar(128) not null,
        birthday varchar(255),
        gender varchar(255) not null check (gender in ('MALE','FEMALE','DIVERS')),
        mobile_area_code varchar(255) not null,
        religion varchar(255) not null check (religion in ('CHRISTIANITY','ISLAM','JUDAISM','HINDUISM','BUDDHISM','SIKHISM','OTHER','NONE_SPECIFIED')),
        school_mobile_area_code varchar(255),
        teaching_subject varchar(255) not null check (teaching_subject in ('AM','DE','E','WIR','PRE','NVS','POS','BWM','DBI','AINF','COPR')),
        primary key (id)
    );

    create table teacher_classes (
        school_class_id bigint not null,
        teacher_id bigint not null
    );

    create table teacher_emails (
        teacher_id bigint not null,
        email_addresses varchar(255)
    );

    alter table if exists school 
       add constraint FK_address_2_countries 
       foreign key (country_id) 
       references countries;

    alter table if exists school_class 
       add constraint FK2br5afl4106t79kv6m2bgwu8b 
       foreign key (school_id) 
       references school;

    alter table if exists student_emails 
       add constraint FK_student_emails_2_student 
       foreign key (student_id) 
       references students;

    alter table if exists students 
       add constraint FK_address_2_countries 
       foreign key (country_id) 
       references countries;

    alter table if exists students 
       add constraint FK21tt8xq83kbgwd98k4dybhp1b 
       foreign key (school_id) 
       references school;

    alter table if exists students 
       add constraint FK_student_2_class 
       foreign key (student_class_id) 
       references school_class;

    alter table if exists teacher 
       add constraint FK_address_2_countries 
       foreign key (country_id) 
       references countries;

    alter table if exists teacher 
       add constraint FKrg46bnmgbcccayv14naymqg3r 
       foreign key (school_id) 
       references school;

    alter table if exists teacher_classes 
       add constraint FKmfx29u14l6fhmx9js6vppbwu4 
       foreign key (school_class_id) 
       references school_class;

    alter table if exists teacher_classes 
       add constraint FKcv5l92f60xvm42dxsktcw951c 
       foreign key (teacher_id) 
       references teacher;

    alter table if exists teacher_emails 
       add constraint FK_teacher_email_2_teachers 
       foreign key (teacher_id) 
       references teacher;
