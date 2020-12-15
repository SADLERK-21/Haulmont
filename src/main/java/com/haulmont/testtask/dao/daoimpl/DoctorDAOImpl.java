package com.haulmont.testtask.dao.daoimpl;

import com.haulmont.testtask.dao.DoctorDAO;
import com.haulmont.testtask.entity.Doctor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.util.Collection;

public class DoctorDAOImpl implements DoctorDAO {
    private static final Logger log = LogManager.getLogger(DoctorDAOImpl.class);
    private Transactional<Doctor> transactional;

    public DoctorDAOImpl() {
        this.transactional = new Transactional<>(log);
    }

    @Override
    public void insertOrUpdate(Doctor doctor) {
        String logMessage = "Add/update doctor: " + doctor;
        this.transactional.transaction((x -> {
            this.transactional.getEntityManager().merge(x);
            log.info(logMessage);
        }), doctor, logMessage);
    }

    @Override
    public void remove(Doctor doctor) throws Exception {
        String logMessage = "Remove doctor: " + doctor;
        this.transactional.transaction((x -> {
            Doctor removableDoctor = this.transactional.getEntityManager().find(Doctor.class, x.getId());
            if (removableDoctor != null) {
                this.transactional.getEntityManager().remove(removableDoctor);
            }
            this.transactional.getEntityManager().flush();
            log.info(logMessage);
        }), doctor, logMessage);
    }

    @Override
    public Doctor findByID(Long id) {
        String logMessage = "Find doctor by id: " + id;
        return this.transactional.transaction(()-> {Doctor result = this.transactional.getEntityManager()
                .createNamedQuery("Doctor.findById",Doctor.class)
                .setParameter("id",id).getSingleResult();
            log.info(logMessage);
            return result;
        },logMessage);
    }

    @Override
    public Collection<Doctor> getAll() {
        String logMessage = "Get all doctors";

        return this.transactional.transaction(() -> {
            Collection<Doctor> result =
                    this.transactional.getEntityManager()
                            .createNamedQuery("Doctor.findAll", Doctor.class).getResultList();
            log.info(logMessage.toString());
            StringBuilder stringBuilder = new StringBuilder();
            for (Doctor doctor : result) {
                stringBuilder.append(doctor).append("\n");
            }
            return result;
        }, logMessage.toString());
    }
}
