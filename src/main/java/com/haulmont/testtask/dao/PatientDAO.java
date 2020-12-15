package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Resipe;

import java.util.Collection;

public interface PatientDAO {
    void insertOrUpdate(Patient patient);

    void remove(Patient patient) throws Exception;

    Collection<Patient> findByResipe(Resipe resipe);

    Patient findByID(Long id);

    Collection<Patient> getAll();
}
