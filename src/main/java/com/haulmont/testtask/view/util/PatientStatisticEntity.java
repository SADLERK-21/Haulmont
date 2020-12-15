package com.haulmont.testtask.view.util;

import com.haulmont.testtask.dao.ResipeDAO;
import com.haulmont.testtask.entity.Patient;

public class PatientStatisticEntity {
    private Patient patient;
    private Integer count;

    public PatientStatisticEntity(Patient patient,ResipeDAO resipeDAO) {
        this.patient = patient;
        this.count = resipeDAO.findByPatient(patient).size();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
