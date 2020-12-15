package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Resipe;


import java.util.Collection;
import java.util.Map;

public interface ResipeDAO {

    void insertOrUpdate(Resipe resipe);

    Collection<Resipe> findByDescriptionAndPatientAndDoctor(Map<String,Object> params);

    Collection<Resipe> findByPatient(Patient patient);

    void remove(Resipe resipe) throws Exception;

    Resipe findByID(Long id);

    Collection<Resipe> getAll();
}
