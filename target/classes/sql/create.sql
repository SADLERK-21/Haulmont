create table if not exists DOCTOR
(
    ID          BIGINT identity,
    FIRST_NAME  VARCHAR(50) not null,
    SECOND_NAME VARCHAR(50) not null,
    PATRONYMIC  VARCHAR(50) not null,
    PROFICIENCY VARCHAR(50) not null,
    primary key (ID)
);

create table if not exists PATIENT
(
    ID            BIGINT identity,
    FIRST_NAME_P  VARCHAR(50) not null,
    SECOND_NAME_P VARCHAR(50) not null,
    PATRONYMIC_P  VARCHAR(50) not null,
    PHONE_NUMBER  VARCHAR (50) not null,
    primary key (ID)
);

create table if not exists RESIPE
(
    ID                 BIGINT identity,
    DESCRIPTION        VARCHAR(50) not null,
    PATIENT_ID         BIGINT      not null,
    DOCTOR_ID          BIGINT      not null,
    CREATION_DATE      VARCHAR(50) not null,
    VALIDITY           INTEGER     not null,
    PRIORITY           VARCHAR(50) not null,
    primary key (ID),
    foreign key (DOCTOR_ID) references DOCTOR ON DELETE RESTRICT ,
    foreign key (PATIENT_ID) references PATIENT ON DELETE RESTRICT
);