package com.haulmont.testtask;

import com.haulmont.testtask.dao.DoctorDAO;
import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.dao.ResipeDAO;
import com.haulmont.testtask.dao.daoimpl.DoctorDAOImpl;
import com.haulmont.testtask.dao.daoimpl.PatientDAOImpl;
import com.haulmont.testtask.dao.daoimpl.ResipeDAOImpl;


public class DAOFactory {

    public static DoctorDAO getDoctorDAO() {
        return new DoctorDAOImpl();
    }

    public static ResipeDAO getResipeDAO() {
        return new ResipeDAOImpl();
    }

    public static PatientDAO getPatientDAO() {
        return new PatientDAOImpl();
    }
}
