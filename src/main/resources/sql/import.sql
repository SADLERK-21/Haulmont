insert into DOCTOR
(ID, FIRST_NAME, SECOND_NAME, PATRONYMIC, PROFICIENCY)
values (0, 'Sergey', 'Ivanovich', 'Ivanov', 'Surgeon'),
       (1, 'Andrey', 'Bykov', 'Yevgenyevich', 'Therapist'),
       (2, 'Gleb', 'Romanenko', 'Mikhailovich', 'Dentist'),
       (3, 'Simon', 'Lobanov', 'Andreevich', 'Proctologist'),
       (4, 'Varvara', 'Chernous', 'Valeryevna', 'Dermatologist'),
       (5, 'Boris', 'Levin', 'Leonidovich', 'Surgeon'),
       (6, 'Ivan', 'Kupitman', 'Natanovich' , 'Venereologist'),
       (7, 'Anastasia', 'Kisegach', 'Konstantinovna', 'Head physician'),
       (8, 'Lyubov', 'Scriabina', 'Mikhailovna', 'nurse');

insert into PATIENT
(ID, FIRST_NAME_P, SECOND_NAME_P, PATRONYMIC_P, PHONE_NUMBER)
values (0, 'Ivan', 'Ivanovich', 'Ivanov', '449358'),
       (1, 'Michael', 'Kitaev', 'Sergeevich', '484562'),
       (2, 'Nikita', 'Vasilyev', 'Andreevich', '477725'),
       (3, 'Pavel', 'Manshin', 'Nikolaevich', '495852'),
       (4, 'Anna', 'Demina', 'Andreyevna', '421589'),
       (5, 'Maksim', 'Galanin', 'Sergeevich', '442286');

insert into RESIPE
(ID, DESCRIPTION, PATIENT_ID, DOCTOR_ID, CREATION_DATE, VALIDITY, PRIORITY)
values (0, 'Analgesic', 4, 2, '22/10/20', 120, 'Normal'),
       (1, 'Vitamine', 1, 5, '10/12/20', 60, 'Normal'),
       (2, 'Antibiotic', 0, 0, '15/11/20', 120, 'Cito'),
       (3, 'Laxative', 3, 3, '9/12/20', 240, 'Statim'),
       (4, 'Vitamine', 5, 7, '3/10/20', 180, 'Cito'),
       (5, 'Analgesic', 2, 2, '12/11/20', 120, 'Normal');