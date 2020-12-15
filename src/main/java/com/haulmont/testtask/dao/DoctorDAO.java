package com.haulmont.testtask.dao;


import com.haulmont.testtask.entity.Doctor;

import java.util.Collection;

public interface DoctorDAO {
    void insertOrUpdate(Doctor doctor);

    void remove(Doctor doctor) throws Exception;

    Doctor findByID(Long id);

    Collection<Doctor> getAll();
}
